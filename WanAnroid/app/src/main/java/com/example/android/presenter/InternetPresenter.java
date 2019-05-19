package com.example.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.bean.BannerBean;
import com.example.android.service.ArticleLoad;
import com.example.android.service.BannerLoad;
import com.example.android.view.HomeFragment;

import java.io.Serializable;
import java.util.List;

public class InternetPresenter implements Serializable {

    private Activity mActivity;
    private HomeFragment mHomeFragment;

    public InternetPresenter(Activity activity,HomeFragment homeFragment){
        this.mActivity=activity;
        this.mHomeFragment=homeFragment;
    }

    public void articleLoad(String website,int i){
        //判断是否有网络连接

        if (checkConnect()) {
            new ArticleLoad(this,website,i);
        } else{
            connectionfailed("网络连接失败");
        }

    }

    public void connectionfailed(String error){
        Toast.makeText(mActivity,error,Toast.LENGTH_LONG).show();
    }

    public void articleResult(List<ArticleBean> homeArticleList){
        mHomeFragment.artitleRefresh(homeArticleList);
    }

    public void bannerLoad(String website){
        if (checkConnect()) {
            new BannerLoad(this).load(website);
        } else{
            connectionfailed("网络连接失败");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bannerRefresh(List<BannerBean> bannerBeanList){
        mHomeFragment.bannerRefresh(bannerBeanList);
    }

    private boolean checkConnect(){

        ConnectivityManager connectivityManager= (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null;
    }
}
