package com.shenstudio.musicplayer;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by 沈文斐 on 2016-09-12.
 */

//用于进行全局的初始化及数据通信操作
public class APP extends Application {
    private final static String TAG = "APP";
    //音乐播放列表队列，用于管理音乐播放顺序
    private LinkedList<MusicInfo> musicList;

    //用于记录当前的列表的播放进度
    private int curr_position;

    public int getCurr_position() {
        return curr_position;
    }

    public void setCurr_position(int curr_position) {
        this.curr_position = curr_position;
    }

    public LinkedList<MusicInfo> getMusicList() {
        return musicList;
    }

    public void setMusicList(LinkedList<MusicInfo> musicQueue) {
        this.musicList = musicQueue;
    }

    public static IBinder manager;

    /**
     * 获取正在播放的音乐信息
     */
    public MusicInfo getCurrInfo() {
        if (musicList == null) {
            return null;
        }
        MusicInfo info = musicList.get(curr_position);
        if (info != null) {
            return info;
        } else {
            return null;
        }
    }

    /**
     * 用来初始化后台运行的服务，并生成一个可供全局使用的播放管理器
     */
    void initService() {
        Intent intent = new Intent(this, MusicManageService.class);
        ServiceConnection Conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                manager = service;
                Log.d(TAG, "播放服务已成功连接");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "播放服务已断开连接");
            }
        };
        startService(intent);
        bindService(intent, Conn, BIND_AUTO_CREATE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initService();
    }
}
