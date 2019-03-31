package com.hutech.musicplayer.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.managers.GlobalBus;
import com.hutech.musicplayer.models.CustomEventBus;
import com.hutech.musicplayer.models.Song;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MusicPlayerAdapter extends RecyclerView.Adapter<MusicPlayerAdapter.ViewHolderSongs> {

    private List<Song> songs;
    private Context context;
    private int previousIndex = 0, currenIndex = -1;
    private CustomEventBus customEventBus = new CustomEventBus();

    public void updateItem(int position)
    {
        currenIndex = position;
        notifyItemChanged(previousIndex);
        notifyItemChanged(currenIndex);
        previousIndex = position;
        customEventBus.setSong(songs.get(position));
        customEventBus.setPosition(position);
        customEventBus.setSongs(songs);
        GlobalBus.getBus().post(customEventBus);
    }
    public MusicPlayerAdapter(Context context, List<Song>songs)
    {
        this.context = context;
        this.songs = songs;

    }
    @Override
    public ViewHolderSongs onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_songs,parent,false);
        return new ViewHolderSongs(view);
    }

    // gắn dữ liệu vào itemView
    @Override
    public void onBindViewHolder(final ViewHolderSongs holder, final int position) {
        holder.updateStatusView(songs.get(position).getTitle(),songs.get(position).getSinger(),currenIndex == position);
//        if(currenIndex == position){
//            customEventBus.setSong(songs.get(position));
//            customEventBus.setPosition(position);
//            customEventBus.setSongs(songs);
//            //GlobalBus.getBus().post(songs.get(position));
//            GlobalBus.getBus().post(customEventBus);
//
//        }
//        else {
//            holder.singer.setTextColor(context.getResources().getColor(R.color.colorWhite));
//            holder.title.setTextColor(context.getResources().getColor(R.color.colorWhite));
//            //holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currenIndex != position)
                {
                    currenIndex = position;

                    notifyItemChanged(previousIndex);
                    notifyItemChanged(currenIndex);
                    previousIndex = position;
                    customEventBus.setSong(songs.get(position));
                    customEventBus.setPosition(position);
                    customEventBus.setSongs(songs);
                    GlobalBus.getBus().post(customEventBus);
                }

//                if(currenIndex != position)
//                {
//                    currenIndex = position;
//                    notifyDataSetChanged();
//                }

            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(int positionPrev)
//    {
//        this.currenIndex = positionPrev;
//        Log.d("MusicPlayerAdapter","CurrenIndex"+currenIndex);
//    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class ViewHolderSongs extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView singer;
        void updateStatusView(String title, String single,boolean isSelected) {
            this.singer.setText(single);
            this.title.setText(title);
            if (isSelected) {
                this.singer.setTextColor(context.getResources().getColor(R.color.colorOrange));
                this.title.setTextColor(context.getResources().getColor(R.color.colorOrange));
            }else {
                this.singer.setTextColor(context.getResources().getColor(R.color.colorWhite));
                this.title.setTextColor(context.getResources().getColor(R.color.colorWhite));
            }
        }
        public ViewHolderSongs(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.textview_title);
            singer = itemView.findViewById(R.id.textview_singer);
        }
    }
}
