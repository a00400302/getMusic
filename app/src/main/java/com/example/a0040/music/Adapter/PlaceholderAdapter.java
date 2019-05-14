package com.example.a0040.music.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a0040.music.R;

import java.util.ArrayList;

/**
 * Created by a0040 on 2018/3/27.
 */

public class PlaceholderAdapter extends RecyclerView.Adapter<PlaceholderAdapter.Placeviewholder> {
    ArrayList<String> list= new ArrayList<String>();


    public PlaceholderAdapter(){
        for (int i=0;i<30;i++){
            list.add(String.valueOf(i));
        }
    }


    @Override
    public Placeviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview_placeholder, parent, false);

        return new Placeviewholder(view);
    }

    @Override
    public void onBindViewHolder(Placeviewholder holder, int position) {
            holder.a.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Placeviewholder extends RecyclerView.ViewHolder{
        private TextView a;

        public Placeviewholder(View itemView) {
            super(itemView);
            a = itemView.findViewById(R.id.holder_text);
        }
    }
}
