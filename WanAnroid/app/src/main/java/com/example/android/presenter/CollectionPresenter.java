package com.example.android.presenter;

import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.net.ArticleLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.CollectionActivity;

import java.util.Collection;
import java.util.List;

public class CollectionPresenter {

    private CollectionActivity mActivity;
    private ArticleLoad articleLoad;

    public CollectionPresenter(CollectionActivity activity){
        mActivity=activity;
        articleLoad=new ArticleLoad(this);
    }

    public void load(String website){
        if(ConnectUtil.checkConnect(mActivity)){
            articleLoad.load(website,0);
        }else{connectionfailed("网络连接失败");}
    }

    public void refreash(List<ArticleBean> list) {
        mActivity.refreash(list);
    }

    public void connectionfailed(String error){
        Toast.makeText(mActivity,error,Toast.LENGTH_LONG).show();
    }

}
