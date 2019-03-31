package com.hutech.musicplayer.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.base.BaseActivity;
import com.hutech.musicplayer.base.ViewPagerAdapter;
import com.hutech.musicplayer.managers.ConnectionReceiver;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.managers.GlobalBus;
import com.hutech.musicplayer.models.CustomEventBus;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.presenters.DetailMusicPresenter;
import com.hutech.musicplayer.presenters.MusicPlayerPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DetailMusicActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener,SeekBar.OnSeekBarChangeListener, IDetailMusic,ConnectionReceiver.ListenerNetwork {
    private ViewPagerAdapter viewPagerAdapter;
    private ImageButton btnPlay;
    private ImageButton btnPrevious;
    private ImageButton btnNext;
    private SeekBar seekBarAudio;
    private TextView timeStart;
    private TextView timeTotal;
    private DetailMusicPresenter detailMusicPresenter;
    private MusicPlayerPresenter musicPlayerPresenter;
    private boolean mUserIsSeeking = false;
    private boolean isCheck = false;
    private List<Song> songs = new ArrayList<>();
    private int position = -1;
    int userSelectedPosition = 0;
    ViewPager vpDetailMusic;
    ConnectionReceiver connectionReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_music);
//        Intent intent = this.getIntent();
//        this.initComponent();
        //GlobalBus.getBus().register(this);
        this.initComponent();
        this.setControl();
        detailMusicPresenter = new DetailMusicPresenter(this, this);
        //musicPlayerPresenter = new MusicPlayerPresenter(this);
        connectionReceiver = new ConnectionReceiver(this,this);
    }
    protected void initComponent()
    {

        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        btnPrevious = findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        seekBarAudio = findViewById(R.id.seekbar_audio);
        timeStart = findViewById(R.id.textview_timestart);
        timeTotal = findViewById(R.id.textview_timetotal);
        seekBarAudio.setOnSeekBarChangeListener(this);

    }

    protected void setControl()
    {
        vpDetailMusic = findViewById(R.id.vp_detail_music);
        vpDetailMusic.addOnPageChangeListener(this);
        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());

        viewPagerAdapter.addFragment(new MusicPlayerFragment());
        viewPagerAdapter.addFragment(new MusicLyricsFragment());
        vpDetailMusic.setAdapter(viewPagerAdapter);

    }


    //hiện option menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem mSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearch);

        MenuItemCompat.setOnActionExpandListener(mSearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return false;
            }
        });
        // xử lý sk search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    MusicPlayerAdapter.class.getField(newText);
                }catch (Exception ex){

                }
                //MusicPlayerAdapter.class.getField(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final CustomEventBus customEventBus)
    {
        /*Toast.makeText(DetailMusicActivity.this,"fileMP3: " + song.getPathMp3(),
                Toast.LENGTH_SHORT).show();*/
        try {
            position = customEventBus.getPosition();
            songs = customEventBus.getSongs();
            //Log.d("DetailMusicActiv","vị trí: "+position + "SL bài hát: " + songs.size());
            isCheck = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        detailMusicPresenter.reset();
                        //detailMusicPresenter.loadMedia(song.getPathMp3());
                        detailMusicPresenter.loadMedia(customEventBus.getSong().getPathMp3());
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            });
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }



        //Log.d("DetailMusicActiv","fileMP3: " + song.getPathMp3());
    }

    //sự kiện button previous
    public void previousSong()
    {
        if(position > 0 && songs.size() > 0)
        {
            EventBus myBus = EventBus.getDefault();
            final Integer currentPosition = position -1;
            GlobalBus.getBus().post(currentPosition);
            myBus.post(currentPosition);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        detailMusicPresenter.reset();
                        detailMusicPresenter.loadMedia(songs.get(currentPosition).getPathMp3());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            });
        }
        if(position == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        detailMusicPresenter.reset();
                        detailMusicPresenter.loadMedia(songs.get(position).getPathMp3());
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            });
        }

    }

    public void nextSong()
    {
        if(position < songs.size() - 1)
        {
            Log.d("Detailmusic","vị trí hiện tại"+position+"size bài hát"+songs.size());
            EventBus myBus = EventBus.getDefault();
            final Integer currentPosition = position + 1;
            GlobalBus.getBus().post(currentPosition);
            myBus.post(currentPosition);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        detailMusicPresenter.reset();
                        detailMusicPresenter.loadMedia(songs.get(currentPosition).getPathMp3());
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            });
        }
        if(position == songs.size() - 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        detailMusicPresenter.reset();
                        detailMusicPresenter.loadMedia(songs.get(position).getPathMp3());
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //detailMusicPresenter.loadMedia(R.raw.only_love_mp3);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DetailMusicActi", "onResume");
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectionReceiver.unRegister(); //hủy đăng ký Receiver
       // GlobalBus.getBus().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
        //detailMusicPresenter.stop();
        Log.d("DetailMusicActi", "onStop");
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
      //  Log.d("DetailMusicActiv Scroll",""+i);
    }

    @Override
        public void onPageSelected(int i) {
       // Log.d("DetailMusicAct Selected",""+i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
      //  Log.d("DetailMusicAct ScrollSt",""+i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_previous:
                previousSong();
                break;
            case R.id.btn_play:
                if(isCheck == true)
                {
                    detailMusicPresenter.startAndPause();
                }
                else {
                    Toast.makeText(DetailMusicActivity.this,"Bạn vui lòng chọn bài hát",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_next:
                nextSong();
                break;

        }
    }

    //Khi giá trị Progress thay đổi
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser)
        {
            userSelectedPosition = progress;
           // Log.d("MainActiv", "Số giây: "+ userSelectedPosition);
        }
    }

    //Khi người dùng bắt đầu cử chỉ kéo thanh gạt
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mUserIsSeeking = true;
    }

    //Khi người dùng kết thúc cử chỉ kéo thanh gạt
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mUserIsSeeking = false;
        detailMusicPresenter.seekTo(userSelectedPosition);
    }


    @Override
    public void onDurationChanged(int duration) {
        seekBarAudio.setMax(duration);
        int minute = detailMusicPresenter.calcMinutes(duration);
        int second = detailMusicPresenter.calcSeconds(duration);
        //Log.d("DetailActive","số phút"+minute+"số giây"+second);
        timeTotal.setText(String.format("%02d",minute) +":"+ String.format("%02d",second));

        //Log.d("DetailMusicActiv", String.format("setPlaybackDuration: setMax(%d)", duration));
    }

    @Override
    public void onPositionChanged(final int position) {
        if(!mUserIsSeeking){
            seekBarAudio.setProgress(position);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int minutes = (position/1000) / 60;
                    int seconds = position/1000 - minutes * 60;
                    timeStart.setText(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
                }
            });
           // Log.d("DetailMusicActiv", "Vị trí: "+position);
        }
    }

    @Override
    public void checkStatus(boolean status) {
        if(status == true)
        {
            btnPlay.setImageResource(R.mipmap.ic_pause);
        } else {
            btnPlay.setImageResource(R.mipmap.ic_play);
        }
    }

    @Override
    public void checkPlay(boolean stt) {
        //isCheck = stt;
    }

    //kiểm tra trạng thái kết nối mạng
    @Override
    public void onNetWorkStateChange(boolean isCheck) {
        if(isCheck == false)
            super.dialog("Vui lòng mở wifi hoặc mạng di động!");
    }

    //Lấy danh sách bài hát ra
//    @Override
//    public void getSongs(List<Song> songs) {
//        this.songs = songs;
//        //Toast.makeText(DetailMusicActivity.this,"số lượng bh"+songs.size(),Toast.LENGTH_LONG).show();
//    }
}
