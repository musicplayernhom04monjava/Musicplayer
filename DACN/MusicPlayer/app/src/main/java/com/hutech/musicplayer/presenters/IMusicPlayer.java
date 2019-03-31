package com.hutech.musicplayer.presenters;

import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.models.Song;

import java.util.List;

public interface IMusicPlayer {
    void listSong(List<Song> songs);
    void onFailure(ErrorCode errorCode);
}
