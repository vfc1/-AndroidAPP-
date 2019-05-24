package com.example.android.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.bean.HotWebsiteBean;
import com.example.android.db.HistoryLoad;
import com.example.android.service.BannerLoad;
import com.example.android.service.HotWebsiteLoad;
import com.example.android.service.HotWordLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.SearchActivity;
import com.example.android.view.SearchTipsFragment;

import java.util.List;

public class SeaechTipsFraPresenter {

    private SearchTipsFragment mFragment;

    public SeaechTipsFraPresenter(SearchTipsFragment fragment){
        this.mFragment=fragment;
    }

    public void loadHotWords(){
        if(ConnectUtil.checkConnect(mFragment.getContext())){new HotWordLoad(this);}
        else{connectionfailed("网络连接失败");}

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWord(List<String> strings){
          mFragment.refreashHotWord(strings);

    }

    public void loadHotWebsite(){
        if(ConnectUtil.checkConnect(mFragment.getContext())){new HotWebsiteLoad(this);}
        else{connectionfailed("网络连接失败");}

    }





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWebsite(List<HotWebsiteBean> hotWebsiteBeans){
        mFragment.refreashHotWebsite(hotWebsiteBeans);

    }

    public void connectionfailed(String error){
        Toast.makeText(mFragment.getContext(),error,Toast.LENGTH_LONG).show();
    }
}
