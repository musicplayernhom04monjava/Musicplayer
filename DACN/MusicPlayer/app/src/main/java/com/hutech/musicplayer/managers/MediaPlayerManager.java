package com.hutech.musicplayer.managers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import com.hutech.musicplayer.presenters.ContractDetailMusic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MediaPlayerManager implements IPlayerControl{
    private MediaPlayer mediaPlayer;
    private Context mContext;
    private String mResource;
    private Runnable mSeekbarPositionUpdateTask;
    private ContractDetailMusic contractDetailMusic;
    private ScheduledExecutorService mExecutor;
    private boolean isSet;
    private boolean isCheck = false;
    public MediaPlayerManager(Context context, ContractDetailMusic contractDetailMusic)
    {
        mContext = context.getApplicationContext();
        this.contractDetailMusic = contractDetailMusic;
    }

    private void initMedia()
    {
        if(mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void loadMedia(String resourceId) {
        initMedia();
        /*mResource = resourceId;
        AssetFileDescriptor assetFileDescriptor =
                mContext.getResources().openRawResourceFd(mResource);*/
        try {
            mediaPlayer.setDataSource(resourceId);
        }
        catch (Exception ex){
            Log.d("MPManager_LoadMedia","Lỗi: "+ex);
        }
        try
        {
            mediaPlayer.prepare();
            initProgressCallBack();
            /*this.isCheck = true;
            contractDetailMusic.checkPlay(isCheck);*/
            startAndPause();
        }catch (Exception ex){
            Log.d("MPManager_LoadMedia","Lỗi: "+ex);
        }

    }
    public void playBackComplete()
    {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("MediaPlayer","PlayBackComplete");
                isSet = false;
                mp.stop();
                contractDetailMusic.checkStatus(isCheck);
            }
        });
    }
    public void stop()
    {
        mediaPlayer.stop();
    }
    @Override
    public void play() {
        if(mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
            startUpdatePositionCallBack();
            playBackComplete();
        }

    }

    @Override
    public void pause() {
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void seekTo(int position) {
        if(mediaPlayer != null)
        {
            mediaPlayer.seekTo(position);
        }
    }

    @Override
    public void reset() {
        isSet = false;
        if(mediaPlayer != null){
            /*try{
                mediaPlayer.prepare();
            }
            catch (Exception ex){
                Log.d("MPManager_LoadMedia","Lỗi: "+ex);
            }*/

            mediaPlayer.stop();
            mediaPlayer.reset();
        }


        contractDetailMusic.checkStatus(isSet);
    }

    public void startAndPause()
    {
        isSet = false;
            if(mediaPlayer.isPlaying())
            {
                pause();
            }
            else {
                isSet = true;
                play();
            }
        contractDetailMusic.checkStatus(isSet);
    }
    //Đồng bộ vị trí mMediaPlayer với mPlaybackProgressCallback thông qua tác vụ định kỳ

    private void startUpdatePositionCallBack()
    {
        if(mExecutor == null)
        {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if(mSeekbarPositionUpdateTask == null)
        {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallBack();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(mSeekbarPositionUpdateTask, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void updateProgressCallBack(){
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {
            int currenPosition = mediaPlayer.getCurrentPosition();
            if(contractDetailMusic != null)
            {
                contractDetailMusic.onPosition(currenPosition);
            }
        }
    }

    private void initProgressCallBack(){
        int duration = mediaPlayer.getDuration();
        if(contractDetailMusic != null)
        {
            contractDetailMusic.onDuration(duration);
            contractDetailMusic.onPosition(0);
        }
    }
}
