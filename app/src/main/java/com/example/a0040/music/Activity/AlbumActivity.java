package com.example.a0040.music.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a0040.music.Adapter.SearchListAdapter;
import com.example.a0040.music.Bean.NetSBean;
import com.example.a0040.music.R;
import com.example.a0040.music.Source;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class AlbumActivity extends AppCompatActivity {

    private TextView album_time;
    private TextView album_artis;
    private TextView album_count;
    private TextView album_name;
    private RecyclerView album_list;


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
        setContentView(R.layout.activity_album);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.artist_toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("album_name")+"-"+getIntent().getStringExtra("artist_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.imageView);
        simpleDraweeView.setImageURI(getIntent().getStringExtra("imgurl"));
        String url = getIntent().getStringExtra("url");
        Log.d("asdf", "onCreate: " + url);

        album_name = (TextView) findViewById(R.id.album_name);
        album_count = (TextView) findViewById(R.id.album_count);
        album_artis = (TextView) findViewById(R.id.album_artis);
        album_time = (TextView) findViewById(R.id.album_time);
        album_artis.setText(getIntent().getStringExtra("artist_name"));
        album_name.setText(getIntent().getStringExtra("album_name"));
        album_list = (RecyclerView) findViewById(R.id.album_list);

        AlbumAsyncTask albumAsyncTask = new AlbumAsyncTask(url, getIntent().getStringExtra("type"));
        albumAsyncTask.execute();

        album_list.setLayoutManager(new LinearLayoutManager(this));


    }


    public class AlbumAsyncTask extends AsyncTask<Void, String, String> {


        String url;
        String type;

        public AlbumAsyncTask(String url, String type) {
            this.url = url;
            this.type = type;
        }

        public AlbumAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestBody body = RequestBody.create(null, "");
            Request.Builder url = new Request.Builder().url(this.url);
            if (Objects.equals(type, "NET")) {
                url.post(body)
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
//            if(Objects.equals(type, "QQ")){
//
//            }
            if (Objects.equals(type, "XIA")) {
                url.get()
                        .addHeader("HOST", "api.xiami.com")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "application/json, text/plain, */*")
                        .addHeader("Accept-Language", "zh-CN")
                        .addHeader("Origin", "http://m.xiami.com/")
                        .addHeader("Referer", "http://m.xiami.com/")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Listen1/1.5.2 Chrome/59.0.3071.115 Electron/1.8.1 Safari/537.3");

            }

            OkHttpClient okHttpClient = new OkHttpClient();
            Response execute = null;
            try {
                execute = okHttpClient.newCall(url.build()).execute();
                return execute.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            JSONObject json = null;
            try {
                s = s.replace("asonglist1459961045566(", "");
                s = s.replace(")", "");

                json = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (Objects.equals(this.type, "NET")) {
                try {
                    JSONArray songs = json.getJSONObject("album").getJSONArray("songs");
                    long publishTime = json.getJSONObject("album").getLong("publishTime") / 1000;
                    int year = (int) (1970 + publishTime / 31536000);
                    int mouth = (int) (publishTime % 31536000 / 2419200);
                    album_time.setText(year + "年" + mouth + "月");
                    album_count.setText(json.getJSONObject("album").getString("company"));

                    ArrayList<NetSBean> list = Source.nat_Album(songs);
                    SearchListAdapter searchListAdapter = new SearchListAdapter(list);
                    album_list.setAdapter(searchListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.equals(this.type, "QQ")) {
                try {
                    JSONArray songs = json.getJSONObject("data").getJSONArray("list");
                    String time = json.getJSONObject("data").getString("aDate");
                    album_time.setText(time);
                    album_count.setText(json.getJSONObject("data").getString("company"));
                    ArrayList<NetSBean> list = Source.qq_Album(songs);
                    SearchListAdapter searchListAdapter = new SearchListAdapter(list);
                    album_list.setAdapter(searchListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.equals(this.type, "XIA")) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s.substring(9, s.length()));
                    long time = jsonObject.getJSONObject("data").getLong("gmt_publish");
                    int year = (int) (1970+time / 31536000);
                    int mouth = (int) (time % 31536000 / 2419200);
                    album_time.setText(year + "年" + mouth + "月");
                    album_count.setText("");
                    ArrayList<NetSBean> list = Source.xia_Album(s,album_artis.getText().toString());
                    SearchListAdapter searchListAdapter = new SearchListAdapter(list);
                    album_list.setAdapter(searchListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            super.onPostExecute(s);
        }
    }
}
