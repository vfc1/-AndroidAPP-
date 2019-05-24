package com.example.android.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.db.HistoryLoad;
import com.example.android.service.SearchResultLoad;
import com.example.android.view.SearchActivity;
import com.example.android.view.SearchDetailFragment;

import java.util.List;

public class SearchDetailPresenter {

    private SearchDetailFragment mFragment;
    private SearchResultLoad searchResultLoad;

    public SearchDetailPresenter(SearchDetailFragment fragment){
        searchResultLoad=new SearchResultLoad(this);
        this.mFragment=fragment;
    }

    public void loadResult(String website){
        searchResultLoad.load(website);
    }

    public void refreashResult(List<ArticleBean> articleBeans){
        mFragment.refreashResult(articleBeans);
    }

    public void connectionfailed(String error){
        Toast.makeText(mFragment.getContext(),error,Toast.LENGTH_LONG).show();
    }

}
