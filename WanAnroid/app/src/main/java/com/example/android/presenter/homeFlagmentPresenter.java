package com.example.android.presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.bean.BannerBean;
import com.example.android.net.ArticleLoad;
import com.example.android.net.BannerLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.HomeFragment;

import java.io.Serializable;
import java.util.List;

public class homeFlagmentPresenter implements Serializable {

    private Context mActivity;
    private HomeFragment mHomeFragment;

    public homeFlagmentPresenter(Context activity, HomeFragment homeFragment){
        this.mActivity=activity;
        this.mHomeFragment=homeFragment;
    }

    //下载文章
    public void articleLoad(String website,int i){
        //判断是否有网络连接

        if (ConnectUtil.checkConnect(mActivity)) {
            new ArticleLoad(this,website,i);
        } else{
            connectionfailed("网络连接失败");
        }

    }
//发生错误
    public void connectionfailed(String error){
        Toast.makeText(mActivity,error,Toast.LENGTH_LONG).show();
    }

    //刷新文章
    public void articleResult(List<ArticleBean> homeArticleList){
        mHomeFragment.artitleRefresh(homeArticleList);
    }

    //下载轮播图
    public void bannerLoad(String website){
        if (ConnectUtil.checkConnect(mActivity)) {
            new BannerLoad(this).load(website);
        } else{
            connectionfailed("网络连接失败");
        }
    }

    //刷新轮播图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bannerRefresh(BannerBean bannerBean){
        mHomeFragment.bannerRefresh(bannerBean);
    }

}
