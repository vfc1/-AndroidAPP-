package com.example.android.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.adapter.KnowArticlePager;
import com.example.android.adapter.KnowLegedListAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.KnowledgeBean;
import com.example.android.presenter.KnowDetailPresenter;
import com.example.android.presenter.KnowledgeFragPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowLedgeDetailActivity extends AppCompatActivity {

    private Intent intent;
    private final String web1="https://www.wanandroid.com/article/list/";
    private final String web2="/json?cid=";
    private int i=0;
    private List<View> mViewList=new ArrayList<>();
    private List<AticleViewAdapter> mLIistadapter=new ArrayList<>();
    private List<List<ArticleBean>> lists=new ArrayList<>();
    private KnowDetailPresenter mPresenter;
    private KnowArticlePager mAdapter;
    private ViewPager mViewPager;
    private List<Integer> integers=new ArrayList<>();
    private List<String> Id;

    AbsListView.OnScrollListener scrollListener=new AbsListView.OnScrollListener() {

        boolean isLastRow = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                int i=mViewPager.getCurrentItem();
                int j=integers.get(i);
                mPresenter.articleLoad(web1+j+web2+Id.get(i),i);
                j++;
                integers.set(i,j);
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
        setContentView(R.layout.activity_know_ledge_detail);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.mytab);
        intent=getIntent();
        List<String> childName= intent.getStringArrayListExtra("childName");
        Id=intent.getStringArrayListExtra("ID");
        int start=intent.getIntExtra("where",0);
        mViewPager=(ViewPager) findViewById(R.id.view_pager);
        mPresenter=new KnowDetailPresenter(this);

        for(int i=0;i< Id.size();i++){
            int j=0;
            mPresenter.articleLoad(web1+j+web2+ Id.get(i),i);
            j++;
            integers.add(j);
            ListView listView=new ListView(this);
            listView.setOnScrollListener(scrollListener);
            final List<ArticleBean> articleBeanList=new ArrayList<>();
            lists.add(articleBeanList);
            AticleViewAdapter aticleViewAdapter=new AticleViewAdapter(this,R.layout.article_item,articleBeanList);
            listView.setAdapter(aticleViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ArticleBean article=articleBeanList.get(position);
                    Intent intent=new Intent(KnowLedgeDetailActivity.this,WebActivity.class);
                    intent.putExtra("website",article.getmWebsite());
                    startActivity(intent);
                }
            });
            mLIistadapter.add(aticleViewAdapter);
            mViewList.add(listView);
        }
        //注意必须在给adapter添加list之后再添加添加给viewpager否则就应该增加刷新的代码，因为每次改变数据之后都要刷新，不然报错
        mAdapter=new KnowArticlePager(mViewList,childName);
        mViewPager.setAdapter(mAdapter);
        //for(int i=0;i<childName.size();i++) {
            //tabLayout.addTab(tabLayout.newTab().setText(childName.get(i)));
       // }
        //关联
        tabLayout.setupWithViewPager(mViewPager);
        //进入时选定的状态
        tabLayout.getTabAt(start).select();
    }

    public void refreash(List<ArticleBean> articleBeanList,int i){
        List<ArticleBean> articleBeans =lists.get(i);
        for(int j=0;j<articleBeanList.size();j++){
            articleBeans.add(articleBeanList.get(j));
        }
        mLIistadapter.get(i).notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }


}
