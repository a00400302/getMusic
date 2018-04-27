package com.example.a0040.music.Activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a0040.music.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Created by a0040 on 2018/3/20.
 */

public class DetailActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Button downloadbutton = (Button) findViewById(R.id.downloadbutton);

        Log.d("asdf", "onCreate: " + getIntent().getStringExtra("url"));
        final String songname = getIntent().getStringExtra("song_name");
        final String url = getIntent().getStringExtra("url");


        final DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("asdf", "onClick: "+Environment.getExternalStorageDirectory().toString());
                Uri uri =Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                File saveFile = new File(Environment.getExternalStorageDirectory().toString()+"/FindMusic",songname+".mp3");
                request.setTitle(songname);
                request.setDestinationUri(Uri.fromFile(saveFile));
                request.setMimeType("audio/x-mpeg-3");
                downloadManager.enqueue(request);
                Toast.makeText(v.getContext(), "正在下载", Toast.LENGTH_SHORT).show();

            }
        });




        SimpleDraweeView songsAlbumImg = (SimpleDraweeView) findViewById(R.id.songs_album_img);
        songsAlbumImg.setImageURI(getIntent().getStringExtra("img"));

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
        }



//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

        TextView songName = (TextView) findViewById(R.id.song_name);
        RelativeLayout stop =(RelativeLayout) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
            }
        });
        TextView artisName = (TextView) findViewById(R.id.song_artis_name);
        TextView albumName = (TextView) findViewById(R.id.song_album_name);
        songName.setText(songname);
        artisName.setText(getIntent().getStringExtra("artist_name"));
        albumName.setText(getIntent().getStringExtra("album_name"));

        songsAlbumImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AlbumActivity.class);
                intent.putExtra("url", getIntent().getStringExtra("album_url"));
                intent.putExtra("type", getIntent().getStringExtra("type"));
                intent.putExtra("imgurl", getIntent().getStringExtra("img"));
                intent.putExtra("artist_name", getIntent().getStringExtra("artist_name"));
                intent.putExtra("album_name", getIntent().getStringExtra("album_name"));

                mediaPlayer.stop();
                startActivity(intent);
            }
        });

        songsAlbumImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!Objects.equals(getIntent().getStringExtra("type"), "XIA")) {
                    Intent intent = new Intent(DetailActivity.this, ArtistActivity.class);
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    intent.putExtra("artist_url", getIntent().getStringExtra("artist_url"));
                    intent.putExtra("artist_name", getIntent().getStringExtra("artist_name"));
                    intent.putExtra("artist_img_url", getIntent().getStringExtra("artist_img_url"));
                    startActivity(intent);
                    mediaPlayer.stop();
                } else {
                    Toast.makeText(v.getContext(), "虾米音乐暂不支持歌手内容", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }


}
