package com.example.android.presenter;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.example.android.bean.ProjectionArticleBean;
import com.example.android.bean.ProjectonBean;
import com.example.android.net.ProjectionArticleLoad;
import com.example.android.net.ProjectionLoad;
import com.example.android.net.ProjectionPhoto;
import com.example.android.util.ConnectUtil;
import com.example.android.view.ProjectionFragment;

import java.util.List;
import java.util.Map;

public class ProjectionPresenter {

    private ProjectionFragment mProjectionFragment;
    private ProjectionPhoto projectionPhoto;

    public ProjectionPresenter(ProjectionFragment projectionFragment){
        this.mProjectionFragment=projectionFragment;
        projectionPhoto=new ProjectionPhoto(this);
    }

    //下载项目的信息
    public void projectLoad(){
        if(ConnectUtil.checkConnect(mProjectionFragment.getContext())){
            new ProjectionLoad(this);
        }
        else{connectionfailed("网络连接失败");}

    }

    //下载项目目录下的文章
    public void articleLoad(String website,int i){
        if(ConnectUtil.checkConnect(mProjectionFragment.getContext())){
            new ProjectionArticleLoad(this,website,i);
        }
        else{connectionfailed("网络连接失败");}
    }

    //发生错误
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
    //i为第几个图片,j为第几个listview
    public void loadPhoto(String website, Map<Integer,Drawable> map, int i,int j){
        projectionPhoto.load(website,map,i,j);
    }

    //刷新项目模块下的listview
    public void listPhotoRefresh(int i){
        mProjectionFragment.refreashDrawble(i);
    }

}
