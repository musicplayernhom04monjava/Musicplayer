package com.hutech.musicplayer.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.hutech.musicplayer.managers.ConnectionReceiver;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.managers.IPlayerControl;
import com.hutech.musicplayer.managers.MediaPlayerManager;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.views.IDetailMusic;

import java.util.ArrayList;
import java.util.List;

public class DetailMusicPresenter implements IPlayerControl, ContractDetailMusic{

    private MediaPlayerManager mediaPlayerManager;
    private IDetailMusic iDetailMusic;
    private List<Song> songs = new ArrayList<>();
    //private Context mContext;
    public DetailMusicPresenter(Context context, IDetailMusic v){
        mediaPlayerManager = new MediaPlayerManager(context, this);
        this.iDetailMusic = v;


        //FirebaseManager.getInstance(this).loadSongs();
    }
    @Override
    public void loadMedia(String resourceId) {
        mediaPlayerManager.loadMedia(resourceId);
    }

    @Override
    public void play() {
        mediaPlayerManager.play();
    }

    @Override
    public void pause() {
        mediaPlayerManager.pause();
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {
       // Log.d("DetailMusicPre","danh sách bài hát: "+songs.size());
    }

    @Override
    public void seekTo(int position) {
        mediaPlayerManager.seekTo(position);
    }

    @Override
    public void reset() {
        mediaPlayerManager.reset();
    }

    @Override
    public void stop() {
        mediaPlayerManager.stop();
    }

    @Override
    public void onDuration(int duration) {
        iDetailMusic.onDurationChanged(duration);
        //Log.d("DetailMusicPre","Duration: "+duration);
    }

    @Override
    public void onPosition(int position) {
        iDetailMusic.onPositionChanged(position);
        //Log.d("DetailMusicPre","Position: "+position);
    }

    @Override
    public void checkStatus(boolean status) {
        iDetailMusic.checkStatus(status);
    }

    @Override
    public void checkPlay(boolean stt) {
        //iDetailMusic.checkStatus(stt);
    }

    @Override
    public void getSongs(List<Song> songs) {
        this.songs = songs;
        //Log.d("DetailMusicPre","danh sách bài hát: "+songs.size());
    }

    public int calcMinutes(int totalMSeconds){
        int minutes = (totalMSeconds / 1000)/60;
        return minutes;
    }
    public int calcSeconds(int totalMSeconds){
        int seconds = (totalMSeconds / 1000)%60;
        return seconds;
    }
    public void startAndPause()
    {
        mediaPlayerManager.startAndPause();
    }

}
