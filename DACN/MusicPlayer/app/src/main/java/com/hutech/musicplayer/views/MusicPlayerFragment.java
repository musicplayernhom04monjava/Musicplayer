package com.hutech.musicplayer.views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.managers.ConnectionReceiver;
import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.managers.GlobalBus;
import com.hutech.musicplayer.models.CustomEventBus;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.presenters.IMusicPlayer;
import com.hutech.musicplayer.presenters.MusicPlayerPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayerFragment extends Fragment implements IMusicPlayer{
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MusicPlayerAdapter musicPlayerAdapter;
    private MusicPlayerPresenter musicPlayerPresenter;
    private List<Song> songs;
    private TextView edtSearch;
    private Button btnSearch;
    //private EventBus myBus = EventBus.getDefault();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_music_player,container,false);
            recyclerView = view.findViewById(R.id.recyclerview_music_player);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            edtSearch = view.findViewById(R.id.edt_search);
            btnSearch = view.findViewById(R.id.btn_search);
            //addSong();

            GlobalBus.getBus().register(this);
            //myBus.register(this);
        }else {
            ViewGroup viewGroup = (ViewGroup) container.getParent();
            viewGroup.removeView(view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        musicPlayerPresenter = new MusicPlayerPresenter(this);
        musicPlayerPresenter.loadSongs();
        searchSongs();
    }
    /*
    

    

}
