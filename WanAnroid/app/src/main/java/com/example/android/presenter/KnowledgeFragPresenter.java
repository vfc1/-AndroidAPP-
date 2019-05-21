package com.example.android.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.android.bean.ArticleBean;
import com.example.android.bean.KnowledgeBean;
import com.example.android.service.KnowledgeLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.KnowLedgeDetailActivity;
import com.example.android.view.KnowledgeFragment;

import java.util.List;

public class KnowledgeFragPresenter {

    private Context mContext;
    private KnowledgeFragment mKnowledgeFragment;
    private KnowLedgeDetailActivity mKnowLedgeDetailActivity;

    public KnowledgeFragPresenter(Context activity, KnowledgeFragment knowledgeFragment){
        this.mContext=activity;
        this.mKnowledgeFragment=knowledgeFragment;
    }

    public void load(String website){
        if(ConnectUtil.checkConnect(mContext)){new KnowledgeLoad(this,website);}
        else {connectionfailed("网络连接失败");}

    }

    public void connectionfailed(String error){
        Toast.makeText(mContext,error,Toast.LENGTH_LONG).show();
    }

    public void result(List<KnowledgeBean> knowledgeBeanList){
        mKnowledgeFragment.refresh(knowledgeBeanList);
    }

}
