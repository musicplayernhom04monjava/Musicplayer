package com.hutech.musicplayer.presenters;

import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.models.User;

import java.util.List;

public interface IFirebase {
    void fireBaseCallBack(User user);
    void fireBaseErrorCallBack(ErrorCode code);
    void fireBaseCallBack(List<Song> songs);
    void fireBaseCallBackSearchSongs(List<Song> songs);
}
