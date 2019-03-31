package com.hutech.musicplayer.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.managers.GlobalBus;
import com.hutech.musicplayer.models.CustomEventBus;
import com.hutech.musicplayer.models.LrcRow;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.presenters.IMusicLyrics;
import com.hutech.musicplayer.presenters.MusicLyricsPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MusicLyricsFragment extends Fragment implements IMusicLyrics {
    private View view;
    private RecyclerView recyclerView;
    //private List<String> lyrics = new ArrayList<>();
    private MusicLyricsAdapter musicLyricsAdapter;
    private LinearLayoutManager layoutManager;
    private MusicLyricsPresenter musicLyricsPresenter;
    private final int fileNameMusicLrc = R.raw.only_love_lrc;
    private String urlLrc;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_music_lyrics,container,false);
            GlobalBus.getBus().register(this);
            recyclerView = view.findViewById(R.id.recyclerview_music_lyrics);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            //setData();
            musicLyricsPresenter = new MusicLyricsPresenter(view,this);
            //musicLyricsPresenter.getLrc(fileNameMusicLrc);
        }else {
            ViewGroup viewGroup = (ViewGroup) container.getParent();
            viewGroup.removeView(view);
        }
        return view;

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CustomEventBus customEventBus)
    {

        //urlLrc = song.getPathlrc();
        urlLrc = customEventBus.getSong().getPathlrc();
        musicLyricsPresenter.getFromAssets(urlLrc);
        /*lyrics.add("[Verse 1: Adam Levine] ");
        lyrics.add("Spent 24 hours, I need more hours with you ");
        lyrics.add("You spent the weekend getting even, ooh ");
        lyrics.add("We spent the late nights making things right between us ");
        musicLyricsAdapter = new MusicLyricsAdapter(getActivity(),lyrics);
        recyclerView.setAdapter(musicLyricsAdapter);*/
    }


    @Override
    public void getLyrics(List<LrcRow> lyrics) {
        if(lyrics != null)
        {
            musicLyricsAdapter = new MusicLyricsAdapter(getActivity(),lyrics);
            recyclerView.setAdapter(musicLyricsAdapter);
        }

    }

    @Override
    public void getLyrics(String lyrics) {
        musicLyricsPresenter.getLrc(lyrics);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }
}
