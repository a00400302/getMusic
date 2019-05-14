package com.example.a0040.music.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.a0040.music.Adapter.AlbumListAdapter;
import com.example.a0040.music.Adapter.SearchListAdapter;
import com.example.a0040.music.Beans.AlbumBeanX;
import com.example.a0040.music.Beans.MusicBeanX;
import com.example.a0040.music.R;
import com.example.a0040.music.Source;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

        getSupportActionBar().setTitle(getIntent().getStringExtra("album_name") + "-" + getIntent().getStringExtra("artist_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.imageView);
        simpleDraweeView.setImageURI(getIntent().getStringExtra("imgurl"));
        String url = getIntent().getStringExtra("url");
        Log.d("asdf", "onCreate: " + url);

        album_name = (TextView) findViewById(R.id.album_name);
        album_artis = (TextView) findViewById(R.id.album_artis);
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
                json = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("AlbumAsyncTask", s);
            ArrayList<AlbumBeanX.SongListBean> list = Source.Album(s);

            AlbumListAdapter searchListAdapter = new AlbumListAdapter(list,type);
            album_list.setAdapter(searchListAdapter);
            super.onPostExecute(s);
        }
    }
}
