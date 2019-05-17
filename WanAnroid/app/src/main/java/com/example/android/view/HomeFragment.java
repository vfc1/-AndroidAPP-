package com.example.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.bean.Article;
import com.example.android.presenter.InternetPresenter;

import java.util.ArrayList;

import java.util.List;

public class  HomeFragment extends Fragment {

    private AticleViewAdapter madapter;
    private InternetPresenter mpresenter;
    private Activity mActivity;
    private SwipeRefreshLayout swipeRefresh;
    private final String web1="https://www.wanandroid.com/article/list/";
    private final String web2="/json";
    private int i=0;

    List<Article> mHomeArticleList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        mActivity=(Activity)getContext();
        mpresenter=new InternetPresenter(mActivity,this);
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorBulue);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAticle(web1+0+web2,0);
                swipeRefresh.setRefreshing(false);
            }
        });

        loadAticle(web1+i+web2,i);
        madapter= new AticleViewAdapter(mActivity, R.layout.article_item, mHomeArticleList);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(madapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article homeArticle=mHomeArticleList.get(position);
                Intent intent=new Intent(mActivity,WebActivity.class);
                intent.putExtra("website",homeArticle.getmWebsite());
                startActivity(intent);
            }
        });
        return view;
    }

    public void refresh(List<Article> homeArticleList){
        for(int i=0;i<homeArticleList.size();i++){
            mHomeArticleList.add(homeArticleList.get(i));

        }
        madapter.notifyDataSetChanged();
    }

    public void loadAticle(String website,int i){
        mpresenter.load(website,i);
    }

}
