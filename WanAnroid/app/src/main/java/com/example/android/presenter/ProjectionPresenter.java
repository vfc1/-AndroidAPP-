package com.example.android.presenter;

import android.widget.Toast;

import com.example.android.bean.ProjectionArticleBean;
import com.example.android.bean.ProjectonBean;
import com.example.android.service.ProjectionArticleLoad;
import com.example.android.service.ProjectionLoad;
import com.example.android.util.ConnectUtil;
import com.example.android.view.ProjectionFragment;

import java.util.List;

public class ProjectionPresenter {

    private ProjectionFragment mProjectionFragment;

    public ProjectionPresenter(ProjectionFragment projectionFragment){
        this.mProjectionFragment=projectionFragment;
    }

    public void projectLoad(){
        if(ConnectUtil.checkConnect(mProjectionFragment.getContext())){
            new ProjectionLoad(this);
        }
        else{connectionfailed("网络连接失败");}

    }

    public void articleLoad(String website,int i){
        if(ConnectUtil.checkConnect(mProjectionFragment.getContext())){
            new ProjectionArticleLoad(this,website,i);
        }
        else{connectionfailed("网络连接失败");}
    }

    public void connectionfailed(String error){
        Toast.makeText(mProjectionFragment.getContext(),error,Toast.LENGTH_LONG).show();
    }

    //将从网上下载的数据返回给活动
    public void result(List<ProjectonBean> projectonBeans){
        mProjectionFragment.refreashProjection(projectonBeans);
    }

    //将列表中的文章返回给活动刷新
    public void articleResult(List<ProjectionArticleBean> projectionArticleBeans,int i){
        mProjectionFragment.refreashArticle(projectionArticleBeans,i);

    }

}
