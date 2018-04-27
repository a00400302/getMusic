package com.example.a0040.music.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.a0040.music.Adapter.PlaceholderAdapter;

import com.example.a0040.music.Adapter.SearchListAdapter;
import com.example.a0040.music.Bean.NetSBean;
import com.example.a0040.music.R;

import com.example.a0040.music.Source;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a0040 on 2018/3/20.
 */

public class ArtistActivity extends AppCompatActivity {

    private SimpleDraweeView view;
    private int count;
    private String url;
    private String type;
    private RecyclerView listview;
    private ArrayList<NetSBean> list;
    private RefreshLayout refreshLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        listview = (RecyclerView) findViewById(R.id.artist_list);
        ;
        listview.setLayoutManager(new LinearLayoutManager(this));
        view = (SimpleDraweeView) findViewById(R.id.artist_img);

        Log.d("asdf", "onCreate: " + getIntent().getStringExtra("artist_url"));
        Log.d("asdf", "onCreate: " + getIntent().getStringExtra("type"));
        url = getIntent().getStringExtra("artist_url");
        type = getIntent().getStringExtra("type");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.artist_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("artist_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArtistTask artistTask = new ArtistTask(url, type, false);
        //"http://music.163.com/api/artist/songs?id=2116&offset=50&limit=50"
        artistTask.execute();

        if (Objects.equals(type, "XIA")) {
            count = 1;
        } else {
            count = 0;
        }

//刷新加载


        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                http://music.163.com/api/artist/songs?id=2116&offset=51&limit=1
//                http://api.xiami.com/web?v=2.0&app_key=1&id=135&page=1&limit=20&_ksTS=1459931285956_216&callback=jsonp217&r=artist/hot-songs
//                https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg?g_tk=5381&jsonpCallback=callback&loginUin=0&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0&singermid=003Nz2So3XXYek&order=listen&begin=0&num=30&songstatus=1

                String load_url = "";
                if (Objects.equals(type, "NET")) {
                    count += 50;
                    String id = url.substring(32);
                    load_url = "http://music.163.com/api/artist/songs?id=" + id + "&offset=" + count + "&limit=50";
                }
                if (Objects.equals(type, "QQ")) {
                    count += 1;
                    load_url = url.replace("begin=0", "begin=" + count);
                }
                if (Objects.equals(type, "XIA")) {
                    count += 1;
                    load_url = url.replace("page=1", "page=" + count);
                }
                new ArtistTask(load_url, type, true).execute();
//
//                refreshLayout.setEnableLoadmore(false); 停止加载
//                refreshLayout.setLoadmoreFinished(true);  加载完成

                refreshlayout.finishLoadmore(100);
            }
        });


    }


    class ArtistTask extends AsyncTask<Void, String, String> {

        private boolean isLoadmore;
        private String url;
        private String type;


        ArtistTask(String url, String type, boolean isLoadmore) {
            this.url = url;
            this.type = type;
            this.isLoadmore = isLoadmore;
        }

        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(null, "");
            Request.Builder build;
            build = new Request.Builder().url(url).post(body);
            if (Objects.equals(type, "NET")) {
                build.post(body)
                        .addHeader("HOST", "music.163.com")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "application/json, text/plain, */*")
                        .addHeader("Accept-Language", "zh-CN")
                        .addHeader("Origin", "http://music.163.com/")
                        .addHeader("Referer", "http://music.163.com/")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Listen1/1.5.2 Chrome/59.0.3071.115 Electron/1.8.1 Safari/537.36")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Postman-Token", "a57d01de-5cb9-430b-9a3b-7a96e624f4e5");

            }
            if (Objects.equals(type, "QQ")) {
                build.get();
            }
            if (Objects.equals(type, "XIA")) {
                build.get()
                        .addHeader("HOST", "api.xiami.com")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "application/json, text/plain, */*")
                        .addHeader("Accept-Language", "zh-CN")
                        .addHeader("Origin", "http://m.xiami.com/")
                        .addHeader("Referer", "http://m.xiami.com/")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Listen1/1.5.2 Chrome/59.0.3071.115 Electron/1.8.1 Safari/537.3");

            }
            Response execute = null;
            try {
                execute = okHttpClient.newCall(build.build()).execute();
                return execute.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            JSONObject json = null;
            if (Objects.equals(type, "NET")) {
                try {
                    json = new JSONObject(s);
                    if (isLoadmore) {
                        JSONArray jsonArray = json.getJSONArray("songs");
                        if (jsonArray.length() == 0) {
                            refreshLayout.finishLoadmore();
                            refreshLayout.setEnableLoadmore(false);
                            refreshLayout.setLoadmoreFinished(true);
                        } else {
                            ArrayList<NetSBean> beans = Source.nat_Artist(jsonArray);
                            list.addAll(beans);
                            listview.getAdapter().notifyDataSetChanged();
                        }
                    } else {
                        JSONArray jsonArray = json.getJSONArray("hotSongs");
                        view.setImageURI(json.getJSONObject("artist").getString("picUrl"));
                        list = Source.nat_Album(jsonArray);
                        listview.setAdapter(new SearchListAdapter(list));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            if (Objects.equals(type, "QQ")) {
                s = s.replace(")", "");
                s = s.replace("callback(", "");
                try {
                    json = new JSONObject(s);
                    view.setImageURI("http://y.gtimg.cn/music/photo_new/T001R500x500M000" + json.getJSONObject("data").getString("singer_mid") + ".jpg");
                    JSONObject jsonArray = json.getJSONObject("data");
                    if (isLoadmore) {
                        if (jsonArray.length() == 0) {
                            refreshLayout.finishLoadmore();
                            refreshLayout.setEnableLoadmore(false);
                            refreshLayout.setLoadmoreFinished(true);
                        } else {
                            list.addAll(Source.qq_Artist(jsonArray));
                            listview.getAdapter().notifyDataSetChanged();
                        }

                    } else {
                        list = Source.qq_Artist(jsonArray);
                        listview.setAdapter(new SearchListAdapter(list));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            if (Objects.equals(type, "XIA")) {
                s = s.replace(")", "");
                view.setImageURI(getIntent().getStringExtra("artist_img_url").replace("_1", ""));
                if (isLoadmore) {
                    ArrayList<NetSBean> loadlist = Source.xia_Artist(s, getIntent().getStringExtra("artist_name"));
                    if (loadlist.size() == 0) {
                        refreshLayout.finishLoadmore();
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setLoadmoreFinished(true);
                    } else {
                        list.addAll(loadlist);
                        listview.getAdapter().notifyDataSetChanged();
                    }

                } else {
                    list = Source.xia_Artist(s, getIntent().getStringExtra("artist_name"));
                    listview.setAdapter(new SearchListAdapter(list));
                }


            }


            super.onPostExecute(s);
        }
    }


}
