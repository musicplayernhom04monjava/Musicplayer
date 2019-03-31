package com.hutech.musicplayer.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.hutech.musicplayer.R;
import com.hutech.musicplayer.models.LrcRow;

import java.util.List;

public class MusicLyricsAdapter extends RecyclerView.Adapter<MusicLyricsAdapter.ViewHolderLyrics> {
    List<LrcRow> lyrics;
    Context context;
    public MusicLyricsAdapter(Context context, List<LrcRow> lyrics)
    {
        this.lyrics = lyrics;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    //Get multiple type view.
/*    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }*/

    @Override
    public ViewHolderLyrics onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View view = null;
        switch (viewType){
            case 0:
                view =  LayoutInflater.from(context).inflate(R.layout.item_lyrics, parent, false);
            case 1:

        }
        return  view;*/
        View view = LayoutInflater.from(context).inflate(R.layout.item_lyrics, parent, false);
        return new ViewHolderLyrics(view);
    }

    //gắn dữ liệu vào itemView
    @Override
    public void onBindViewHolder(ViewHolderLyrics holder, int position) {
        holder.lyric.setText(lyrics.get(position).content);
    }

    class ViewHolderLyrics extends RecyclerView.ViewHolder
    {
        protected TextView lyric;
        public ViewHolderLyrics(View itemView){
            super(itemView);
            lyric = itemView.findViewById(R.id.textview_item_lyrics);
        }
    }
}
