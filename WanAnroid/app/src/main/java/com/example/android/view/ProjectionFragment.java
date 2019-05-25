package com.example.android.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.R;
import com.example.android.adapter.KnowArticlePager;
import com.example.android.adapter.ProjectionListAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.KnowledgeBean;
import com.example.android.bean.ProjectionArticleBean;
import com.example.android.bean.ProjectonBean;
import com.example.android.presenter.ProjectionPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProjectionFragment extends Fragment {

    private final String web1="https://www.wanandroid.com/project/list/";
    private final String web2="/json?cid=";
    private ProjectionPresenter mPresenter;
    private ViewPager mViewPager;
    private List<View> viewList=new ArrayList<>();
    //各个listview的适配器
    private List<ProjectionListAdapter> mArticleadapters=new ArrayList<>();
    //各个标签下的listview中的内容
    private List<List<ProjectionArticleBean>> lists=new ArrayList<>();
    private KnowArticlePager mAdapter;
    private List<String> mTitles=new ArrayList<>();
    //记录每个标签下的页数
    private List<Integer> integers=new ArrayList<>();
    //记录每个标签网址的ID
    private List<String> Id=new ArrayList<>();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.projection_fragment,container,false);

        mPresenter=new ProjectionPresenter(this);
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tab_layout);
        mViewPager=(ViewPager)view.findViewById(R.id.view_pager);
        mPresenter.projectLoad();
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    public void refreashProjection(List<ProjectonBean> projectonBeans){
        for(int i=0;i<projectonBeans.size();i++){
            ProjectonBean projectonBean=projectonBeans.get(i);
            integers.add(2);
            Id.add(projectonBean.getID());
            mPresenter.articleLoad(web1+1+web2+projectonBean.getID(),i);
            mTitles.add(projectonBean.getName());
            final List<ProjectionArticleBean> articleBeans=new ArrayList<>();
            ListView listView=new ListView(getContext());
            ProjectionListAdapter adapter=new ProjectionListAdapter(getContext(),R.layout.projection_listview,articleBeans,mPresenter,i);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(scrollListener);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProjectionArticleBean article=articleBeans.get(position);
                    Intent intent=new Intent(getContext(),WebActivity.class);
                    intent.putExtra("website",article.getmWebsite());
                    startActivity(intent);
                }
            });
            viewList.add(listView);
            lists.add(articleBeans);
            mArticleadapters.add(adapter);
        }
        mAdapter=new KnowArticlePager(viewList,mTitles);
        mViewPager.setAdapter(mAdapter);
    }

    public void refreashArticle(List<ProjectionArticleBean> articleBeans,int i){
        List<ProjectionArticleBean> articleBeanList=lists.get(i);
        ProjectionListAdapter adapter=mArticleadapters.get(i);
        for(int j=0;j<articleBeans.size();j++){
            articleBeanList.add(articleBeans.get(j));
        }
        //viewpager和listview的数据都刷新
        adapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    public void refreashDrawble(int i){
        mArticleadapters.get(i).notifyDataSetChanged();
    }
}
