package com.example.a0040.music;


import com.example.a0040.music.Bean.NetSBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by a0040 on 2018/3/20.
 */

public class Source {

    public static ArrayList<NetSBean> nat_Search(String json) {
        ArrayList<NetSBean> list = new ArrayList<NetSBean>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("songs");
            for (int i = 0; i < jsonArray.length(); i++) {
                NetSBean netSBean = new NetSBean();
                netSBean.setTYPE("NET");
                JSONObject songs = jsonArray.getJSONObject(i);
                JSONObject artsits = songs.getJSONArray("artists").getJSONObject(0);
                JSONObject album = songs.getJSONObject("album");
                netSBean.setSong_name(songs.getString("name"));
                int id = songs.getInt("id");
                netSBean.setArtist_name(artsits.getString("name"));
                netSBean.setArtist_pic_url(artsits.getString("img1v1Url"));
                netSBean.setAlbum_name(album.getString("name"));
                netSBean.setAlbum_pic_url(album.getString("blurPicUrl"));
                netSBean.setMp3Url("https://music.163.com/song/media/outer/url?id=" + String.valueOf(id) + ".mp3");
                netSBean.setAlbumUrl("http://music.163.com/api/album/" + album.getString("id"));
                netSBean.setArtistUrl("http://music.163.com/api/artist/" + artsits.getString("id"));


                list.add(netSBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<NetSBean> qq_Search(String json) {
        ArrayList<NetSBean> list = new ArrayList<>();
        String s = json.substring(7, json.length() - 1);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray songs = jsonObject.getJSONObject("data").getJSONObject("song").getJSONArray("list");
            for (int i = 0; i < songs.length(); i++) {
                NetSBean bean = new NetSBean();
                bean.setTYPE("QQ");
                bean.setAlbum_name(songs.getJSONObject(i).getString("albumname"));
                String albumid = songs.getJSONObject(i).getString("albumid");
                String albummid = songs.getJSONObject(i).getString("albummid");
                bean.setAlbum_pic_url("http://imgcache.qq.com/music/photo/album_300/" +
                        (Integer.valueOf(albumid) % 100) +
                        "/300_albumpic_" +
                        albumid +
                        "_0.jpg");
                bean.setArtist_name(songs.getJSONObject(i).getJSONArray("singer").getJSONObject(0).getString("name"));
                String artistmid = songs.getJSONObject(i).getJSONArray("singer").getJSONObject(0).getString("mid");
                bean.setSong_name(songs.getJSONObject(i).getString("songname"));
                String mid = songs.getJSONObject(i).getString("songmid");
                bean.setMp3Url("http://link.hhtjim.com/qq/" + mid + ".mp3");

                bean.setAlbumUrl("http://i.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?platform=h5page&" +
                        "albummid=" +
                        albummid +
                        "&g_tk=938407465&uin=0&format=jsonp&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&_=1459961045571&jsonpCallback=asonglist1459961045566");
                bean.setArtistUrl("https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg?g_tk=5381&jsonpCallback=callback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&" +
                        "singermid=" +
                        artistmid +
                        "&order=listen&begin=0&num=30&songstatus=1");
                list.add(bean);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<NetSBean> xia_Search(String url) {
        ArrayList<NetSBean> list = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(url.substring(9, url.length() - 1));
            JSONArray songs = jsonObject.getJSONObject("data").getJSONArray("songs");
            for (int i = 0; i < songs.length(); i++) {
                NetSBean bean = new NetSBean();
                bean.setTYPE("XIA");
                bean.setArtist_name(songs.getJSONObject(i).getString("artist_name"));
                bean.setAlbum_name(songs.getJSONObject(i).getString("album_name"));
                bean.setSong_name(songs.getJSONObject(i).getString("song_name"));

                bean.setAlbum_pic_url(songs.getJSONObject(i).getString("album_logo"));
                bean.setArtist_pic_url(songs.getJSONObject(i).getString("artist_logo"));
                bean.setMp3Url(songs.getJSONObject(i).getString("listen_file"));


                bean.setAlbumUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + songs.getJSONObject(i).getString("album_id") + "&page=1&limit=100&callback=jsonp217&r=album/detail");
                bean.setArtistUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + songs.getJSONObject(i).getString("artist_id") + "&page=1&limit=20&_ksTS=1459931285956_216&callback=jsonp217&r=artist/hot-songs");
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static ArrayList<NetSBean> qq_Album(JSONArray jsons) {
        ArrayList<NetSBean> list = new ArrayList<>();
        for (int i = 0; i < jsons.length(); i++) {
            try {
                NetSBean netSBean = new NetSBean();
                netSBean.setTYPE("QQ");
                netSBean.setAlbum_name(jsons.getJSONObject(i).getString("albumname"));
                String albumid = jsons.getJSONObject(i).getString("albumid");
                String albummid = jsons.getJSONObject(i).getString("albummid");

                netSBean.setAlbum_pic_url("http://imgcache.qq.com/music/photo/album_300/" +
                        (Integer.valueOf(albumid) % 100) +
                        "/300_albumpic_" +
                        albumid +
                        "_0.jpg");
                netSBean.setArtist_name(jsons.getJSONObject(i).getJSONArray("singer").getJSONObject(0).getString("name"));
                String artistmid = jsons.getJSONObject(i).getJSONArray("singer").getJSONObject(0).getString("mid");
                netSBean.setSong_name(jsons.getJSONObject(i).getString("songname"));
                String mid = jsons.getJSONObject(i).getString("songmid");
                netSBean.setMp3Url("http://link.hhtjim.com/qq/" + mid + ".mp3");
                netSBean.setAlbumUrl("http://i.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?platform=h5page&" +
                        "albummid=" +
                        albummid +
                        "&g_tk=938407465&uin=0&format=jsonp&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&_=1459961045571&jsonpCallback=asonglist1459961045566");
                netSBean.setArtistUrl("https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg?g_tk=5381&jsonpCallback=callback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&" +
                        "singermid=" +
                        artistmid +
                        "&order=listen&begin=0&num=30&songstatus=1");
                list.add(netSBean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static ArrayList<NetSBean> xia_Album(String s, String album_artis) {
        ArrayList<NetSBean> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s.substring(9, s.length()));
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("songs");

            for (int i = 0; i < jsonArray.length(); i++) {
                NetSBean bean = new NetSBean();
                bean.setSong_name(jsonArray.getJSONObject(i).getString("song_name"));
                bean.setArtist_name(album_artis);
                bean.setMp3Url("http://link.hhtjim.com/xiami/" + jsonArray.getJSONObject(i).getString("song_id") + ".mp3");
                bean.setAlbum_name(jsonArray.getJSONObject(i).getString("album_name"));
//                bean.setArtist_pic_url(jsonArray.getJSONObject(i).getString("artist_logo"));
                bean.setAlbum_pic_url(jsonArray.getJSONObject(i).getString("album_logo"));
                bean.setAlbumUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + jsonArray.getJSONObject(i).getString("album_id") + "&page=1&limit=100&callback=jsonp217&r=album/detail");
                bean.setArtistUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + jsonArray.getJSONObject(i).getString("artist_id") + "&page=1&limit=20&_ksTS=1459931285956_216&callback=jsonp217&r=artist/hot-songs");
                list.add(bean);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<NetSBean> nat_Album(JSONArray jsons) {
        ArrayList<NetSBean> list = new ArrayList<>();
        for (int i = 0; i < jsons.length(); i++) {
            try {
                NetSBean netSBean = new NetSBean();
                netSBean.setTYPE("NET");
                JSONObject songs = null;
                songs = jsons.getJSONObject(i);
                JSONObject artsits = songs.getJSONArray("artists").getJSONObject(0);
                JSONObject album = songs.getJSONObject("album");
                netSBean.setSong_name(songs.getString("name"));
                int id = songs.getInt("id");
                netSBean.setArtist_name(artsits.getString("name"));
                netSBean.setArtist_pic_url(artsits.getString("img1v1Url"));
                netSBean.setAlbum_name(album.getString("name"));
                netSBean.setAlbum_pic_url(album.getString("blurPicUrl"));
                netSBean.setMp3Url("https://music.163.com/song/media/outer/url?id=" + String.valueOf(id) + ".mp3");
                netSBean.setAlbumUrl("http://music.163.com/api/album/" + album.getString("id"));
                netSBean.setArtistUrl("http://music.163.com/api/artist/" + artsits.getString("id"));
                list.add(netSBean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;

    }


    public static ArrayList<NetSBean> qq_Artist(JSONObject jsons) {
        ArrayList<NetSBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = jsons.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {

                NetSBean netSBean = new NetSBean();
                netSBean.setTYPE("QQ");
                netSBean.setAlbum_name(jsons.getJSONArray("list").getJSONObject(i).getJSONObject("musicData").getString("albumname"));
                String albumid = jsons.getJSONArray("list").getJSONObject(i).getJSONObject("musicData").getString("albumid");
                String albummid = jsons.getJSONArray("list").getJSONObject(i).getJSONObject("musicData").getString("albummid");

                netSBean.setAlbum_pic_url("http://imgcache.qq.com/music/photo/album_300/" +
                        (Integer.valueOf(albumid) % 100) +
                        "/300_albumpic_" +
                        albumid +
                        "_0.jpg");


                netSBean.setArtist_name(jsons.getString("singer_name"));
                String artistmid = jsons.getString("singer_mid");
                netSBean.setSong_name(jsons.getJSONArray("list").getJSONObject(i).getJSONObject("musicData").getString("songname"));
                String mid = jsons.getJSONArray("list").getJSONObject(i).getJSONObject("musicData").getString("songmid");
                netSBean.setMp3Url("http://link.hhtjim.com/qq/" + mid + ".mp3");
                netSBean.setAlbumUrl("http://i.y.qq.com/v8/fcg-bin/fcg_v8_album_info_cp.fcg?platform=h5page&" +
                        "albummid=" +
                        albummid +
                        "&g_tk=938407465&uin=0&format=jsonp&inCharset=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&_=1459961045571&jsonpCallback=asonglist1459961045566");
                netSBean.setArtistUrl("https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg?g_tk=5381&jsonpCallback=callback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&" +
                        "singermid=" +
                        artistmid +
                        "&order=listen&begin=0&num=30&songstatus=1");
                list.add(netSBean);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<NetSBean> xia_Artist(String s, String album_artis) {
        ArrayList<NetSBean> list = new ArrayList<NetSBean>();
        try {
            JSONObject jsonObject = new JSONObject(s.substring(9, s.length()));
            JSONArray jsonArray = jsonObject.getJSONArray("data");


            for (int i = 0; i < jsonArray.length(); i++) {
                NetSBean bean = new NetSBean();
                bean.setTYPE("NET");
                bean.setSong_name(jsonArray.getJSONObject(i).getString("song_name"));
                bean.setArtist_name(album_artis);
                bean.setMp3Url("http://link.hhtjim.com/xiami/" + jsonArray.getJSONObject(i).getString("song_id") + ".mp3");
//                bean.setAlbum_name(jsonArray.getJSONObject(i).getString("album_name"));
//                bean.setArtist_pic_url(jsonArray.getJSONObject(i).getString("artist_logo"));
//                bean.setAlbum_pic_url(jsonArray.getJSONObject(i).getString("album_logo"));
//                bean.setAlbumUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + jsonArray.getJSONObject(i).getString("album_id") + "&page=1&limit=100&callback=jsonp217&r=album/detail");
//                bean.setArtistUrl("http://api.xiami.com/web?v=2.0&app_key=1&id=" + jsonArray.getJSONObject(i).getString("artist_id") + "&page=1&limit=20&_ksTS=1459931285956_216&callback=jsonp217&r=artist/hot-songs");
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }


    public static ArrayList<NetSBean> nat_Artist(JSONArray jsonArray) {
        ArrayList<NetSBean> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                NetSBean netSBean = new NetSBean();
                netSBean.setTYPE("NET");
                JSONObject songs = jsonArray.getJSONObject(i);
                JSONObject artsits = songs.getJSONArray("artists").getJSONObject(0);
                JSONObject album = songs.getJSONObject("album");
                netSBean.setSong_name(songs.getString("name"));
                int id = songs.getInt("id");
                netSBean.setArtist_name(artsits.getString("name"));
                netSBean.setArtist_pic_url(artsits.getString("img1v1Url"));
                netSBean.setAlbum_name(album.getString("name"));
                netSBean.setAlbum_pic_url(album.getString("blurPicUrl"));
                netSBean.setMp3Url("https://music.163.com/song/media/outer/url?id=" + String.valueOf(id) + ".mp3");
                netSBean.setAlbumUrl("http://music.163.com/api/album/" + album.getString("id"));
                netSBean.setArtistUrl("http://music.163.com/api/artist/" + artsits.getString("id"));
                list.add(netSBean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }


}
