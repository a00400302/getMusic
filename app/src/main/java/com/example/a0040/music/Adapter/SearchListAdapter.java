package com.example.a0040.music.Adapter;


import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0040.music.MusicBean;
import com.example.a0040.music.R;


import java.util.ArrayList;

/**
 * Created by a0040 on 2018/3/20.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.mViewHolder> {

    private ArrayList<MusicBean.SongListBean> netList;


    public SearchListAdapter(ArrayList<MusicBean.SongListBean> list) {
        netList = list;
    }


    class mViewHolder extends RecyclerView.ViewHolder {
        TextView songs_name;
        TextView singer_name;
        View view;

        mViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            singer_name = (TextView) itemView.findViewById(R.id.singer_name);
            songs_name = (TextView) itemView.findViewById(R.id.song_name);

        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview_item, parent, false);
        final mViewHolder viewHolder = new mViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                int position = viewHolder.getAdapterPosition();
//                intent.putExtra("type", netList.get(position).getTYPE());
//                intent.putExtra("img",netList.get(position).getAlbum_pic_url());
//                intent.putExtra("song_name",netList.get(position).getSong_name()  );
//                intent.putExtra("album_name",netList.get(position).getAlbum_name());
//                intent.putExtra("artist_name",netList.get(position).getArtist_name());
//                intent.putExtra("artist_url",netList.get(position).getArtistUrl());
//                intent.putExtra("album_url",netList.get(position).getAlbumUrl());
//                intent.putExtra("artist_img_url",netList.get(position).getArtist_pic_url());
//
//                intent.putExtra("url",netList.get(position).getMp3Url());
////                intent.putExtra("",netList.get(position).get)
//
//                if(netList.get(position).getAlbum_name()!=null){
//                    v.getContext().startActivity(intent);
//                }else {
////  TODO   直接播放
//                }


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        String singername = "";
        for (MusicBean.SongListBean.ArtistsBean artistsBean : netList.get(position).getArtists() ) {
                singername += artistsBean.getName() + "  ";
        }

        holder.singer_name.setText(singername);
        holder.songs_name.setText(netList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return netList.size();
    }
}
