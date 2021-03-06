package com.example.a0040.music.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0040.music.Activity.DetailActivity;
import com.example.a0040.music.Beans.MusicBeanX;
import com.example.a0040.music.R;


import java.util.ArrayList;

/**
 * Created by a0040 on 2018/3/20.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.mViewHolder> {

    private ArrayList<MusicBeanX> netList;


    public SearchListAdapter(ArrayList<MusicBeanX> list) {
        netList = list;
    }


    class mViewHolder extends RecyclerView.ViewHolder {
        TextView songs_name;
        TextView singer_name;
        View view;
        TextView source_type;

        mViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            singer_name = (TextView) itemView.findViewById(R.id.singer_name);
            songs_name = (TextView) itemView.findViewById(R.id.song_name);
            source_type = itemView.findViewById(R.id.sourceType);

        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview_item, parent, false);
        final mViewHolder viewHolder = new mViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                int position = viewHolder.getAdapterPosition();
                intent.putExtra("type", netList.get(position).getType());
                intent.putExtra("img",netList.get(position).getAlbum().getCover());
                intent.putExtra("song_name",netList.get(position).getName());
                intent.putExtra("album_name",netList.get(position).getAlbum().getName());
                intent.putExtra("artist_name",netList.get(position).getArtists().get(0).getName());
                intent.putExtra("source_type", netList.get(position).getType());
//                https://music-api-jwzcyzizya.now.sh/api/get/album/qq?id=002J7XNt2m9sNc
                intent.putExtra("album_url", "https://music-api-jwzcyzizya.now.sh/api/get/album/" +
                        netList.get(position).getType()
                        + "?id=" + netList.get(position).getAlbum().getId());
                intent.putExtra("url",netList.get(position).getFile());
                if(netList.get(position) !=null){
                    v.getContext().startActivity(intent);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        String singername = "";
        for (MusicBeanX.ArtistsBean artistsBean : netList.get(position).getArtists()) {
                singername += artistsBean.getName() + "  ";
        }

        holder.singer_name.setText(singername);
        holder.songs_name.setText(netList.get(position).getName());
        holder.source_type.setText(netList.get(position).getType());


    }

    @Override
    public int getItemCount() {
        return netList.size();
    }
}
