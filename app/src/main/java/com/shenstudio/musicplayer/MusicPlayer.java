package com.shenstudio.musicplayer;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放的控制器，使用双向链表来表示音乐列表，初次创建的时会根据列表进行预载（将列表头放入播放列表）
 * 通过application类拿到播放列表
 * 对外只暴露播放，暂停，下一曲等方法
 */
public class MusicPlayer implements PlayerManage {
    private final static String TAG = "MusicPlayer";

    //播放器的四种状态模式
    private static final int MOD_NORMAL = 0;
    private static final int MOD_LOOP = 1;
    private static final int MOD_SINGLE = 2;
    private static final int MOD_RAMDOM = 3;

    private static State mstate = State.NORMAL;

    /**
     * 播放器的播放顺序控制器
     */
    enum State {
        NORMAL(MOD_NORMAL), LOOP(MOD_LOOP), SINGLE(MOD_SINGLE), RAMDOM(MOD_RAMDOM);

        private int state;

        State(int state) {
            this.state = state;
            if (player != null && state == MOD_SINGLE) {
                player.setLooping(true);
            } else if (player != null && player.isLooping()) {
                player.setLooping(false);
            }
        }

        int getState() {
            return state;
        }
    }

    private static MediaPlayer player;
    private APP context;
    private LinkedList<MusicInfo> musicList;
    private boolean isPrepare = false;

    MusicPlayer(Context application) {
        if (player == null)
            player = new MediaPlayer();
        this.context = (APP) application;
        player.setOnCompletionListener(new CompletionListener());
        player.setOnPreparedListener(new PreparedListener());
        proloadList(context);
    }

    void proloadList(APP application) {
        if (application == null){
            Log.d(TAG, "application发生空指针异常");
            return;
        }
        musicList = application.getMusicList();
        if (musicList == null)
            return;
        MusicInfo info = musicList.getFirst();
        application.setCurr_position(1);
        prepare(info);
    }

    void prepare(MusicInfo info) {
        player.reset();
        try {
            player.setDataSource(context, Uri.parse(info.getPath()));
        } catch (IOException e) {
            Utils.showToast(context, context.getString(R.string.ERROR_FailGetMusicInfo));
            e.printStackTrace();
        }
        isPrepare = false;
        player.prepareAsync();
    }

    @Override
    public void play() {
        if (player.isPlaying() && isPrepare) {
            player.start();
        } else {
            //Utils.showToast(context, "音乐准备中，请稍后...");
        }
    }

    @Override
    public void pause() {
        if (player.isPlaying())
            player.pause();
    }

    /**
     * 控制上一曲或是下一曲，offset为-1或是1，指示偏移量
     */
    void nextOrPre(int offset) {
        int curr = context.getCurr_position();
        //判断是是否超出范围
        if (curr + offset > musicList.size() || curr + offset <= 0) {
            Utils.showToast(context, context.getString(R.string.ERROR_failToContinue));
            return;
        }

        //将当前的播放列表中的指针指向下一位
        int curr_nextOne = curr + offset;
        context.setCurr_position(curr_nextOne);
        MusicInfo info = musicList.get(context.getCurr_position());

        prepare(info);

        //等待准备结束，播放音乐
        while (!isPrepare) {
            play();
            break;
        }
    }

    @Override
    public void next() {
        nextOrPre(1);
    }

    @Override
    public void pre() {
        nextOrPre(-1);
    }

    //设置循环状态
    @Override
    public void setState(State state) {

    }

    //退出程序时调用，避释放资源
    @Override
    public void close() {
        player.release();
    }

    //音乐播放时间跳转
    @Override
    public void seekTo(int time) {
        if (isPrepare) {
            player.seekTo(time);
        }
    }

    //用来给progressbar更新进度提供信息
    void setOnProgressListener(final ProgressListener pl, long per) {
        final ProgressListener listener = pl;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int curr_position = player.getCurrentPosition();
                listener.onChanged(curr_position);
            }
        }, 0, per);
    }

    interface ProgressListener {
        void onChanged(int posititon);
    }

    //播放完毕监听，用来自动推动播放列表前进
    private class CompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }

    //异步准备监听的回调，用来指示是否追被完毕
    private class PreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            isPrepare = true;
        }
    }
}
