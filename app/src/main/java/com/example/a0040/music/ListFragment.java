package com.example.a0040.music;


import android.graphics.Rect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.a0040.music.Adapter.SearchListAdapter;
import com.example.a0040.music.Bean.NetSBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ListFragment extends Fragment {
    View rootView;
    private RecyclerView listView;
    private int count;
    private ArrayList<NetSBean> list;
    private RefreshLayout refreshLayout;


    public ListFragment() {
    }

    private static String KEYWORD = "keyword";
    private static String TYPE = "type";


    public static ListFragment newInstance(String key, int ty) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(KEYWORD, key);
        args.putInt(TYPE, ty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);


        listView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        String url = null;
        if (getArguments().getInt(TYPE) == 0) {
            url = "http://music.163.com/api/search/pc?s=" + getArguments().getString(KEYWORD) + "&offset=0&limit=20&type=1";
        }
        if (getArguments().getInt(TYPE) == 1) {
            url = "http://i.y.qq.com/s.music/fcgi-bin/search_for_qq_cp?g_tk=938407465&uin=0&format=jsonp&inCharset" +
                    "=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&w=" + getArguments().getString(KEYWORD) +
                    "&zhidaqu=1&catZhida=1&t=0&flag=1&ie=utf-8&sem=1&aggr=0&perpage=20&" +
                    "n=20&p=1&remoteplace=txt.mqq.all&_=1459991037831&jsonpCallback=jsonp4";
        }
        if (getArguments().getInt(TYPE) == 2) {
            url = "http://api.xiami.com/web?v=2.0&app_key=1&key=" + getArguments().getString(KEYWORD) + "&page=0&limit=20&callback=jsonp154&r=search/songs";
        }
        new ListTask(getArguments().getInt(TYPE), false, url).execute();
        listView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(18);

        listView.addItemDecoration(decoration);


        if (getArguments().getInt(TYPE) == 0) {
            count = 0;
        } else {
            count = 1;
        }

        //刷新加载
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {


                String load_url = "";
                if (getArguments().getInt(TYPE) == 0) {
                    count += 20;
                    String url = "http://music.163.com/api/search/pc?s=" + getArguments().getString(KEYWORD) + "&offset=0&limit=20&type=1";
                    load_url = url.replace("offset=0", "offset=" + count);

                }
                if (getArguments().getInt(TYPE) == 1) {
                    count += 1;
                    String url = "http://i.y.qq.com/s.music/fcgi-bin/search_for_qq_cp?g_tk=938407465&uin=0&format=jsonp&inCharset" +
                            "=utf-8&outCharset=utf-8&notice=0&platform=h5&needNewCode=1&w=" + getArguments().getString(KEYWORD) +
                            "&zhidaqu=1&catZhida=1&t=0&flag=1&ie=utf-8&sem=1&aggr=0&perpage=20&" +
                            "n=20&p=1&remoteplace=txt.mqq.all&_=1459991037831&jsonpCallback=jsonp4";
                    load_url = url.replace("p=1", "p=" + count);
                }
                if (getArguments().getInt(TYPE) == 2) {
                    count += 1;
                    String url = "http://api.xiami.com/web?v=2.0&app_key=1&key=" + getArguments().getString(KEYWORD) + "&page=0&limit=20&callback=jsonp154&r=search/songs";
                    load_url = url.replace("page=0", "page=" + count);
                }
                Log.d("asdf", "onLoadmore: " + load_url);
                new ListTask(getArguments().getInt(TYPE), true, load_url).execute();
              refreshLayout.finishLoadmore(100);


            }
        });


        return rootView;


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
        private int ty;

        public ListTask(int ty, boolean isLoadmore, String url) {
            this.isLoadmore = isLoadmore;
            this.url = url;
            this.ty = ty;
        }

        @Override
        protected String doInBackground(Void... voids) {


            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(null, "");
            Request build;


            if (ty == 0) {
                build = new Request.Builder().post(body).url(url)
                        .build();
                Response execute = null;
                try {
                    execute = okHttpClient.newCall(build).execute();
                    return execute.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            if (ty == 1) {

                build = new Request.Builder().get()
                        .url(url)
                        .build();

                Response execute = null;
                try {
                    execute = okHttpClient.newCall(build).execute();
                    return execute.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (ty == 2) {
                build = new Request.Builder().post(body).addHeader("HOST", "api.xiami.com")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "application/json, text/plain, */*")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Accept-Language", "zh-CN")
                        .addHeader("Origin", "http://m.xiami.com/")
                        .addHeader("Referer", "http://m.xiami.com/")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Listen1/1.5.2 Chrome/59.0.3071.115 Electron/1.8.1 Safari/537.3")
                        .addHeader("Cache-Control", "no-cache")
                        .url(url).build();

                Response execute = null;
                try {
                    execute = okHttpClient.newCall(build).execute();
                    return execute.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            ArrayList<NetSBean> loadlist;
            if (ty == 0) {
                if (isLoadmore) {
                    loadlist = Source.nat_Search(s);
                    if (loadlist.size() == 0) {
                        refreshLayout.finishLoadmore();
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setLoadmoreFinished(true);
                    } else {
                        list.addAll(loadlist);
                        listView.getAdapter().notifyDataSetChanged();
//                        refreshLayout.finishLoadmore();

                    }
                } else {
                    list = Source.nat_Search(s);
                    listView.setAdapter(new SearchListAdapter(list));
                }

            }


            if (ty == 1) {
                if (isLoadmore) {
                    loadlist = Source.qq_Search(s);
                    if (loadlist.size() == 0) {
                        refreshLayout.finishLoadmore();
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setLoadmoreFinished(true);
                    } else {
                        list.addAll(loadlist);
                        listView.getAdapter().notifyDataSetChanged();
//                        refreshLayout.finishLoadmore();
                    }

                } else {
                    list = Source.qq_Search(s);
                    listView.setAdapter(new SearchListAdapter(list));
                }
            }
            if (ty == 2) {

                if (isLoadmore) {
                    loadlist = Source.xia_Search(s);
                    if (loadlist.size() == 0) {
                        refreshLayout.finishLoadmore();
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setLoadmoreFinished(true);
                    } else {
                        list.addAll(loadlist);
                        listView.getAdapter().notifyDataSetChanged();
//                        refreshLayout.finishLoadmore();
                    }
                } else {
                    list = Source.xia_Search(s);
                    listView.setAdapter(new SearchListAdapter(list));
                }
            }
            super.onPostExecute(s);
        }
    }


}