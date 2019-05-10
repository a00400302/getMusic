package com.example.a0040.music;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a0040 on 2018/3/20.
 */

public class Source {

    static ArrayList<MusicBean.SongListBean> Search(String s) {
        ArrayList<MusicBean.SongListBean> list = new ArrayList<>();
        MusicBean musicBean = JSON.parseObject(s, MusicBean.class);
        List<MusicBean.SongListBean> songList = musicBean.getSongList();
        list.addAll(songList);
        return list;
    }
}
