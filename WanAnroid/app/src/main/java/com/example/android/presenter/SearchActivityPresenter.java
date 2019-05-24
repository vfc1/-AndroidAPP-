package com.example.android.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.db.HistoryLoad;
import com.example.android.view.SearchActivity;

import java.util.List;

public class SearchActivityPresenter {

    private SearchActivity mActivity;

    public SearchActivityPresenter(SearchActivity activity){
        this.mActivity=activity;
    }

    public void loadHistory(){
        new HistoryLoad(this,mActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHistory(List<String> strings){
        mActivity.refreashHistory(strings);
    }
}
