package com.shenstudio.musicplayer;

import java.io.Serializable;

/**
 * Created by 沈文斐 on 2016-08-31.
 */

public class MusicInfo implements Serializable {
    private String title;
    private String artist;
    private String album_id;
    private String path;
    private String duration;

    public MusicInfo(String title, String artist, String album_id, String path, String duration) {
        this.title = title;
        this.artist = artist;
        this.album_id = album_id;
        this.path = path;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public String getPath() {
        return path;
    }

    public String getDuration() {
        return duration;
    }
}
