package com.hutech.musicplayer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song {


    @SerializedName("pathlrc")
    @Expose
    private String pathlrc;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("singer")
    @Expose
    private String singer;
    @SerializedName("pathmp3")
    @Expose
    private String pathmp3;


    public Song(){}
    public Song(String title, String singer, String pathlrc, String pathmp3)
    {
        this.singer = singer;
        this.title = title;
        this.pathlrc = pathlrc;
        this.pathmp3 = pathmp3;
    }
    public String getPathlrc() {
        return pathlrc;
    }

    public void setPathlrc(String pathlrc) {
        this.pathlrc = pathlrc;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }


    public String getPathMp3() {
        return pathmp3;
    }

    public void setPathMp3(String pathMp3) {
        this.pathmp3 = pathMp3;
    }


}
