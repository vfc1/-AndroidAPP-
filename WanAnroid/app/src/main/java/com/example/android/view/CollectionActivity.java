package com.example.android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.presenter.CollectionPresenter;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private int i=0;
    private String web1="https://www.wanandroid.com/lg/collect/list/";
    private String web2="/json";
    private List<ArticleBean> articleBeans=new ArrayList<>();
    private ListView listView;
    private AticleViewAdapter adapter;
    private CollectionPresenter mPresenter;

    AbsListView.OnScrollListener scrollListener=new AbsListView.OnScrollListener() {

        boolean isLastRow = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                mPresenter.load(web1+i+web2);
                i++;
                isLastRow = false;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastRow = true;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        mPresenter=new CollectionPresenter(this);
        mPresenter.load(web1+i+web2);
        i++;
        listView=(ListView)findViewById(R.id.collect_listview);
        adapter=new AticleViewAdapter(this,R.layout.article_item,articleBeans);
        listView.setAdapter(adapter);
    }

    public void refreash(List<ArticleBean> list){
        for(int i=0;i<list.size();i++){
            articleBeans.add(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
