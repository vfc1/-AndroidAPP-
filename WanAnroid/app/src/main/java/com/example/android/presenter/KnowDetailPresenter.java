package com.example.android.presenter;

import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.net.ArticleLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.KnowLedgeDetailActivity;

import java.util.List;

public class KnowDetailPresenter {

    KnowLedgeDetailActivity mKnowLedgeDetailActivity;

    //构造器
    public KnowDetailPresenter(KnowLedgeDetailActivity knowLedgeDetailActivity){
        this.mKnowLedgeDetailActivity=knowLedgeDetailActivity;
    }

    //返回线程下载的东西
    public void articleResult(List<ArticleBean> articleBeanList,int i){
        mKnowLedgeDetailActivity.refreash(articleBeanList,i);
    }

    //向用户提示错误信息
    public void connectionfailed(String error){
        Toast.makeText(mKnowLedgeDetailActivity,error,Toast.LENGTH_LONG).show();
    }

    //开启线程下载文章
    public void articleLoad(String website,int i){
        if(ConnectUtil.checkConnect(mKnowLedgeDetailActivity)){new ArticleLoad(this,website,i);}
        else{connectionfailed("网络连接失败");}

    }
}
