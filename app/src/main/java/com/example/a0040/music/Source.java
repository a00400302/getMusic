package com.example.a0040.music;


import com.alibaba.fastjson.JSON;
import com.example.a0040.music.Beans.AlbumBeanX;
import com.example.a0040.music.Beans.MusicBean;
import com.example.a0040.music.Beans.MusicBeanX;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by a0040 on 2018/3/20.
 */

public class Source {

    public static ArrayList<MusicBeanX> Search(String s) {
        ArrayList<MusicBeanX> list = new ArrayList<>();
        MusicBean musicBean = JSON.parseObject(s, MusicBean.class);
        MusicBean.NeteaseBean netease = musicBean.getNetease();
        MusicBean.QqBean qq = musicBean.getQq();
        MusicBean.XiamiBean xiami= musicBean.getXiami();
        list.addAll(forNetease(netease));
        list.addAll(forQQ(qq));
        list.addAll(forXiami(xiami));
        return list;
    }


    public static ArrayList<MusicBeanX>  forNetease(MusicBean.NeteaseBean netease){
        ArrayList<MusicBeanX> list = new ArrayList<>();
        for (MusicBean.NeteaseBean.SongListBeanXX listBeanXX:netease.getSongList()){
            MusicBeanX musicBeanX = new MusicBeanX();
            musicBeanX.setType("netease");
            musicBeanX.setSuccess(netease.isSuccess());
            musicBeanX.setTotal(netease.getTotal());
            musicBeanX.setName(listBeanXX.getName());
            musicBeanX.setId(String.valueOf(listBeanXX.getId()));
            musicBeanX.setNeedPay(listBeanXX.isNeedPay());
//          TODO  解析url
            musicBeanX.setFile("https://link.hhtjim.com/163/"+musicBeanX.getId()+".mp3");
//          TODO  解析url


            MusicBeanX.AlbumBean albumBean = new MusicBeanX.AlbumBean();
            albumBean.setCover(listBeanXX.getAlbum().getCover());
            albumBean.setCoverBig(listBeanXX.getAlbum().getCoverBig());
            albumBean.setCoverSmall(listBeanXX.getAlbum().getCoverSmall());
            albumBean.setId(String.valueOf(listBeanXX.getAlbum().getId()));
            albumBean.setName(listBeanXX.getAlbum().getName());
            musicBeanX.setAlbum(albumBean);

            ArrayList<MusicBeanX.ArtistsBean> artistsBeans = new ArrayList<>();
            for(MusicBean.NeteaseBean.SongListBeanXX.ArtistsBeanXX x:listBeanXX.getArtists()){
                MusicBeanX.ArtistsBean artistsBean = new MusicBeanX.ArtistsBean();
                artistsBean.setId(String.valueOf(x.getId()));
                artistsBean.setName(x.getName());
//                artistsBean.setAvatar();
                artistsBean.setTns(x.getTns());
                artistsBean.setAlias(x.getAlias());
                artistsBeans.add(artistsBean);
            }
            musicBeanX.setArtists(artistsBeans);

            list.add(musicBeanX);
        }
        return list;
    }




    public static ArrayList<MusicBeanX>  forQQ(MusicBean.QqBean qqBean){
        ArrayList<MusicBeanX> list = new ArrayList<>();
        for (MusicBean.QqBean.SongListBeanX listBeanXX:qqBean.getSongList()){
            MusicBeanX musicBeanX = new MusicBeanX();
            musicBeanX.setType("qq");
            musicBeanX.setSuccess(qqBean.isSuccess());
            musicBeanX.setTotal(qqBean.getTotal());
            musicBeanX.setName(listBeanXX.getName());
            musicBeanX.setId(String.valueOf(listBeanXX.getId()));
            musicBeanX.setNeedPay(listBeanXX.isNeedPay());
//          TODO  解析url
            musicBeanX.setFile("https://link.hhtjim.com/qq/"+musicBeanX.getId()+".mp3");
//          TODO  解析url


            MusicBeanX.AlbumBean albumBean = new MusicBeanX.AlbumBean();
            albumBean.setCover(listBeanXX.getAlbum().getCover());
            albumBean.setCoverBig(listBeanXX.getAlbum().getCoverBig());
            albumBean.setCoverSmall(listBeanXX.getAlbum().getCoverSmall());
            albumBean.setId(listBeanXX.getAlbum().getId());
            albumBean.setName(listBeanXX.getAlbum().getName());
            musicBeanX.setAlbum(albumBean);



            ArrayList<MusicBeanX.ArtistsBean> artistsBeans = new ArrayList<>();
            for(MusicBean.QqBean.SongListBeanX.ArtistsBeanX x:listBeanXX.getArtists()){
                MusicBeanX.ArtistsBean artistsBean = new MusicBeanX.ArtistsBean();
                artistsBean.setId(String.valueOf(x.getId()));
                artistsBean.setName(x.getName());
//                artistsBean.setAvatar();
//                artistsBean.setTns(x.getTns());
//                artistsBean.setAlias(x.getAlias());
                artistsBeans.add(artistsBean);
            }
            musicBeanX.setArtists(artistsBeans);

            list.add(musicBeanX);
        }
        return list;
    }



    public static ArrayList<MusicBeanX>  forXiami(MusicBean.XiamiBean xiamiBean){
        ArrayList<MusicBeanX> list = new ArrayList<>();
        for (MusicBean.XiamiBean.SongListBean listBeanXX:xiamiBean.getSongList()){
            MusicBeanX musicBeanX = new MusicBeanX();
            musicBeanX.setType("xiami");
            musicBeanX.setSuccess(xiamiBean.isSuccess());
            musicBeanX.setTotal(xiamiBean.getTotal());
            musicBeanX.setName(listBeanXX.getName());
            musicBeanX.setId(String.valueOf(listBeanXX.getId()));
            musicBeanX.setNeedPay(listBeanXX.isNeedPay());
//          TODO  解析url
            musicBeanX.setFile("https://link.hhtjim.com/xiami/"+ musicBeanX.getId()    + ".mp3");
//          TODO  解析url


            MusicBeanX.AlbumBean albumBean = new MusicBeanX.AlbumBean();
            albumBean.setCover(listBeanXX.getAlbum().getCover());
            albumBean.setCoverBig(listBeanXX.getAlbum().getCoverBig());
            albumBean.setCoverSmall(listBeanXX.getAlbum().getCoverSmall());
            albumBean.setId(String.valueOf(listBeanXX.getAlbum().getId()));
            albumBean.setName(listBeanXX.getAlbum().getName());
            musicBeanX.setAlbum(albumBean);

            ArrayList<MusicBeanX.ArtistsBean> artistsBeans = new ArrayList<>();
            for(MusicBean.XiamiBean.SongListBean.ArtistsBean x:listBeanXX.getArtists()){
                MusicBeanX.ArtistsBean artistsBean = new MusicBeanX.ArtistsBean();
                artistsBean.setId(String.valueOf(x.getId()));
                artistsBean.setName(x.getName());
                artistsBean.setAvatar(x.getAvatar());
//                artistsBean.setTns(x.getTns());
//                artistsBean.setAlias(x.getAlias());
                artistsBeans.add(artistsBean);
            }
            musicBeanX.setArtists(artistsBeans);

            list.add(musicBeanX);
        }
        return list;
    }


    public static ArrayList<AlbumBeanX.SongListBean> Album(String s) {
        ArrayList<AlbumBeanX.SongListBean> list = new ArrayList<>();
        AlbumBeanX albumBeanX = JSON.parseObject(s, AlbumBeanX.class);
        for (AlbumBeanX.SongListBean songListBean:albumBeanX.getSongList()){
            list.add(songListBean);
        }
        return list;
    }
}
