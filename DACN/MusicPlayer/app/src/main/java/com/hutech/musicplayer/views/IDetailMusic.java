package com.hutech.musicplayer.views;

import com.hutech.musicplayer.models.Song;

import java.util.List;

public interface IDetailMusic {
    void onDurationChanged(int duration);
    void onPositionChanged(int position);
    void checkStatus(boolean status);
    void checkPlay(boolean stt);
    //void getSongs(List<Song> songs);
}
