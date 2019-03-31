package com.hutech.musicplayer.presenters;

import android.util.Log;
import android.view.View;

import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.models.User;
import com.hutech.musicplayer.views.IDetailMusic;

import java.util.List;

public class MusicPlayerPresenter implements IFirebase {
    //View view;
    IMusicPlayer iMusicPlayer;
    FirebaseManager firebaseManager;
    //IDetailMusic iDetailMusic;
    public MusicPlayerPresenter(){}
    public MusicPlayerPresenter(IMusicPlayer iMusicPlayer)
    {
        this.iMusicPlayer = iMusicPlayer;
        firebaseManager = new FirebaseManager(this);
    }
    //public MusicPlayerPresenter(IDetailMusic iDetailMusic) { this.iDetailMusic = iDetailMusic; }
    public void loadSongs()
    {
        firebaseManager.loadSongs();
    }

    @Override
    public void fireBaseCallBack(User user) {

    }

    @Override
    public void fireBaseErrorCallBack(ErrorCode code) {
        iMusicPlayer.onFailure(code);
    }

    @Override
    public void fireBaseCallBack(List<Song> songs) {
        if(iMusicPlayer != null)
        {
            Log.d("MusicplayerPre","fireBaseCallBack");
            iMusicPlayer.listSong(songs);
        }

//        if(iDetailMusic != null)
//            iDetailMusic.getSongs(songs);
    }

    @Override
    public void fireBaseCallBackSearchSongs(List<Song> songs) {
        iMusicPlayer.listSong(songs);
    }

    public void searchSongs(String query){
        firebaseManager.searchSong(query);
    }

}
