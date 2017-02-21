package com.shenstudio.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MusicManageService extends Service {
    PlayerManage manage;
    IBinder binder;

    public MusicManageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取到音乐播放的管理类
        manage = new MusicPlayer(this.getApplicationContext());
        Log.d("Service", "manage = " + manage);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    void play() {
        manage.play();
    }

    void pause() {
        manage.pause();
    }

    void next() {
        manage.next();
    }

    void pre() {
        manage.pre();
    }

    void seek(int time) {
        manage.seekTo(time);
    }

}
