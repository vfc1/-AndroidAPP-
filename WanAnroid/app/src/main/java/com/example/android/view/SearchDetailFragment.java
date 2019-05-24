package com.example.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.presenter.SearchDetailPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchDetailFragment extends Fragment {

    private SearchDetailPresenter mPresenter=new SearchDetailPresenter(this);
    private SearchActivity mActivity;
    private List<ArticleBean> mArticleBeans=new ArrayList<>();
    private ListView listView;
    private AticleViewAdapter adapter;
    private String web1="https://www.wanandroid.com/article/query/";
    private String web2="/json";
    private int i=0;//记录页数
    //记录之前搜索的内容
    private String before=new String();
    //判断是否点击了搜索的按钮
    protected boolean mBool=false;

    AbsListView.OnScrollListener scrollListener=new AbsListView.OnScrollListener() {

        boolean isLastRow = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                //将正在加载显示出来
                resultLoad(before);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_detail_fragment, container, false);
        mActivity=(SearchActivity) getActivity();
        listView=(ListView)view.findViewById(R.id.search_result);
        adapter=new AticleViewAdapter(mActivity,R.layout.article_item,mArticleBeans);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(scrollListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleBean homeArticle=mArticleBeans.get(position);
                Intent intent=new Intent(mActivity,WebActivity.class);
                intent.putExtra("website",homeArticle.getmWebsite());
                startActivity(intent);
            }
        });
        return view;
    }

    protected void resultLoad(String s){
        if(mBool){
            before=s;
        }
        mPresenter.loadResult(web1+i+web2+"?k="+s);
        i++;
    }

    public void refreashResult(List<ArticleBean> list){
        if(mBool==true){
            mArticleBeans.clear();
            mBool=false;
            this.i=0;
            adapter.notifyDataSetChanged();
        }
        for(int i=0;i<list.size();i++){
            mArticleBeans.add(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }

}
