package com.example.a0040.music.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0040.music.Activity.DetailActivity;
import com.example.a0040.music.Beans.AlbumBeanX;
import com.example.a0040.music.R;

import java.util.ArrayList;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.mViewHolder> {

    /**
     * Created by a0040 on 2018/3/20.
     */

    private ArrayList<AlbumBeanX.SongListBean> netList;
    private String type;
    private String Stype;


    public AlbumListAdapter(ArrayList<AlbumBeanX.SongListBean> list, String type) {
        this.netList = list;
        if (type.equals("netease")){
            this.Stype = "163";
        }else{
            this.Stype = type;
        }
        this.type = type;


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
                intent.putExtra("type", type);
                intent.putExtra("img", netList.get(position).getAlbum().getCover());
                intent.putExtra("song_name", netList.get(position).getName());
                intent.putExtra("album_name", netList.get(position).getAlbum().getName());
                intent.putExtra("artist_name", netList.get(position).getArtists().get(0).getName());


                intent.putExtra("album_url", "https://music-api-jwzcyzizya.now.sh/api/get/album/" +
                        type
                        + "?id=" + netList.get(position).getAlbum().getId());


                intent.putExtra("url","https://link.hhtjim.com/"+Stype+"/"+netList.get(position).getId()+".mp3");
                if (netList.get(position) != null) {
                    v.getContext().startActivity(intent);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder mViewHolder, int i) {
        String singername = "";
        for (AlbumBeanX.SongListBean.ArtistsBean  artistsBean: netList.get(i).getArtists()) {
            singername += artistsBean.getName() + "  ";
        }

        mViewHolder.singer_name.setText(singername);
        mViewHolder.songs_name.setText(netList.get(i).getName());
        mViewHolder.source_type.setText(type);
    }


    @Override
    public int getItemCount() {
        return netList.size();
    }
}


