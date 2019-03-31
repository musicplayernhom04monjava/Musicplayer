package com.hutech.musicplayer.presenters;

import com.hutech.musicplayer.models.Song;

import java.util.List;

public interface ContractDetailMusic  {
    void onDuration(int duration);
    void onPosition(int position);
    void checkStatus(boolean status);
    void checkPlay(boolean stt);
    void getSongs(List<Song> songs);
}
