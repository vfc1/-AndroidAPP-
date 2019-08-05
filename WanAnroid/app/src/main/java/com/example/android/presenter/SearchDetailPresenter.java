package com.example.android.presenter;


import android.content.Context;

import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.net.SearchResultLoad;

import com.example.android.view.SearchDetailFragment;


import java.util.List;

public class SearchDetailPresenter {

    private SearchDetailFragment mFragment;
    private SearchResultLoad searchResultLoad;

    public SearchDetailPresenter(SearchDetailFragment fragment){
        searchResultLoad=new SearchResultLoad(this);
        this.mFragment=fragment;
    }

    //加载搜索结果
    public void loadResult(String website){
        searchResultLoad.load(website);
    }

    //刷新加载结果
    public void refreashResult(List<ArticleBean> articleBeans){
        mFragment.refreashResult(articleBeans);
    }

    //发生错误
    public void connectionfailed(String error){
        Toast.makeText(mFragment.getContext(),error,Toast.LENGTH_LONG).show();
    }

}
