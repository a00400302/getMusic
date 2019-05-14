package com.example.a0040.music.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.example.a0040.music.Adapter.SearchListAdapter;
import com.example.a0040.music.Beans.MusicBeanX;
import com.example.a0040.music.R;
import com.example.a0040.music.Source;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;
    private int count;
    private ArrayList<MusicBeanX> list;
    private RefreshLayout refreshLayout;
    private SearchView searchView;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Fresco.initialize(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int i = checkSelfPermission(permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                showDialog();
            }
        }




        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setDisableContentWhenRefresh(true);
        refreshLayout.setDisableContentWhenLoading(true);
        listView = (RecyclerView) findViewById(R.id.recyclerview);
        count = 1;
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                String load_url = "";
                count += 1;
                String url = "https://music-api-jwzcyzizya.now.sh/api/search/song/all?key=" + keyWord + "&limit=7&page=1";
                load_url = url.replace("page=1", "page=" + count);

                Log.d("asdf", "onLoadmore: " + load_url);
                new ListTask(true, load_url).execute();
                refreshLayout.finishLoadMore(100);
            }
        });

    }


    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("存储权限不可用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setCancelable(false).show();
//            }
//        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 321);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        showDialog();
                    } else
                        finish();
                }
            } else {
                Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_serach, menu);
        MenuItem menuItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("嘀~   学生卡");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                refreshLayout.autoRefresh();
                keyWord = query;
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                String url = "https://music-api-jwzcyzizya.now.sh/api/search/song/all?key=" + query + "&limit=7&page=1";
                new ListTask( false, url).execute();
                listView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                SpacesItemDecoration decoration = new SpacesItemDecoration(18);
                listView.addItemDecoration(decoration);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }

    class ListTask extends AsyncTask<Void, String, String> {

        private boolean isLoadmore;
        private String url;

        public ListTask(boolean isLoadmore, String url) {
            this.isLoadmore = isLoadmore;
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request build;

            build = new Request.Builder().get().url(url)
                    .build();
            Response execute = null;
            try {
                execute = okHttpClient.newCall(build).execute();
                return execute.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            refreshLayout.finishRefresh();
            ArrayList<MusicBeanX> loadlist;
            if (isLoadmore) {
                loadlist = Source.Search(s);
                if (loadlist.size() == 0) {

                    refreshLayout.finishLoadMore();
                    refreshLayout.setEnableLoadMore(false);
//                    refreshLayout.setLoadmoreFinished(true);

                } else {
                    list.addAll(loadlist);
                    listView.getAdapter().notifyDataSetChanged();
//                        refreshLayout.finishLoadmore();

                }
            } else {
                Log.d("ListTask", s);
                list = Source.Search(s);
                listView.setAdapter(new SearchListAdapter(list));
            }
            super.onPostExecute(s);
        }
    }


}
