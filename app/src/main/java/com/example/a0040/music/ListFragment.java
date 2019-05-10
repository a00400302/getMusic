package com.example.a0040.music;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a0040.music.Adapter.SearchListAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ListFragment extends Fragment {
    View rootView;
    private RecyclerView listView;
    private int count;
    private ArrayList<MusicBean.SongListBean> list;
    private RefreshLayout refreshLayout;


    public ListFragment() {
    }

    private static String KEYWORD = "keyword";


    public static ListFragment newInstance(String key) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(KEYWORD, key);
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
        String url = "https://music-api-jwzcyzizya.now.sh/api/search/song/netease?key=" + getArguments().getString(KEYWORD) + "&limit=20&page=1";
        new ListTask( false, url).execute();
        listView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(18);
        listView.addItemDecoration(decoration);


        count = 1;




        //刷新加载
        refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });

//        https://music-api-jwzcyzizya.now.sh/api/search/song/netease?key=刘瑞琦&limit=5&page=1

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                String load_url = "";
                count += 1;
                String url = "https://music-api-jwzcyzizya.now.sh/api/search/song/netease?key=" + getArguments().getString(KEYWORD) + "&limit=5&page=1";
                load_url = url.replace("page=1", "page=" + count);

                Log.d("asdf", "onLoadmore: " + load_url);
                new ListTask(true, load_url).execute();
                refreshLayout.finishLoadMore(100);
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
            ArrayList<MusicBean.SongListBean> loadlist;
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