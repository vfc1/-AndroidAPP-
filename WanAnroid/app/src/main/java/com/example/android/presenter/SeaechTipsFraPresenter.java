package com.example.android.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.bean.HotWebsiteBean;
import com.example.android.net.HotWebsiteLoad;
import com.example.android.net.HotWordLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.SearchTipsFragment;

import java.util.List;

public class SeaechTipsFraPresenter {

    private SearchTipsFragment mFragment;

    public SeaechTipsFraPresenter(SearchTipsFragment fragment){
        this.mFragment=fragment;
    }

    //下载热词
    public void loadHotWords(){
        if(ConnectUtil.checkConnect(mFragment.getContext())){new HotWordLoad(this);}
        else{connectionfailed("网络连接失败");}

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWord(List<String> strings){
          mFragment.refreashHotWord(strings);

    }

    //下载常用网站
    public void loadHotWebsite(){
        if(ConnectUtil.checkConnect(mFragment.getContext())){new HotWebsiteLoad(this);}
        else{connectionfailed("网络连接失败");}

    }

    //刷新常用网站
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWebsite(List<HotWebsiteBean> hotWebsiteBeans){
        mFragment.refreashHotWebsite(hotWebsiteBeans);

    }

    //发生错误
    public void connectionfailed(String error){
        Toast.makeText(mFragment.getContext(),error,Toast.LENGTH_LONG).show();
    }
}
