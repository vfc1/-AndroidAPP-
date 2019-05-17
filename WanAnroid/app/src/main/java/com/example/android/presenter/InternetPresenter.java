package com.example.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.android.bean.HomeArticle;
import com.example.android.service.ArticleLoad;
import com.example.android.service.MyIntentService;
import com.example.android.view.HomeFragment;
import com.example.android.view.MainActivity;

import java.io.Serializable;
import java.util.List;

public class InternetPresenter implements Serializable {

    private Activity mActivity;
    private HomeFragment mHomeFragment;

    public InternetPresenter(Activity activity,HomeFragment homeFragment){
        this.mActivity=activity;
        this.mHomeFragment=homeFragment;
    }

    public void load(){
        //判断是否有网络连接
        ConnectivityManager connectivityManager= (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            new ArticleLoad(this);
        } else{
            connectionfailed("网络连接失败");
        }

    }

    public void connectionfailed(String error){
        Toast.makeText(mActivity,error,Toast.LENGTH_LONG).show();
    }

    public void result(List<HomeArticle> homeArticleList){
        mHomeFragment.refresh(homeArticleList);
    }
}
