package com.shenstudio.musicplayer;

/**
 * Created by 沈文斐 on 2016-08-31.
 */

public interface PlayerManage {
    void play();

    void pause();

    void next();

    void pre();

    void setState(MusicPlayer.State state);

    void close();

    void seekTo(int time);
}
