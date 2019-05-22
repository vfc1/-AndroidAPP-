package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.ProjectionArticleBean;
import com.example.android.presenter.ProjectionPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class ProjectionArticleLoad {

    private ProjectionPresenter mPresenter;
    private List<ProjectionArticleBean> mProjectionArticleBeans=new ArrayList<>();
    //记录是第几个listview;
    int i;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.articleResult(mProjectionArticleBeans,i);
                    break;
                case 2:
                    mPresenter.connectionfailed("数据解析失败");
                    break;
                case 3:
                    mPresenter.connectionfailed("网络连接失败");
                default:
                    break;
            }
        }
    };

    public ProjectionArticleLoad(ProjectionPresenter projectionPresenter,String website,int i){
        this.i=i;
        this.mPresenter=projectionPresenter;
        load(website);
    }

    private void load(final String website){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                HttpURLConnection connection=ConnectUtil.connect(website);
                String jsonData=null;
                if(connection!=null){jsonData = ConnectUtil.read(connection );}
                else{
                    Message message=new Message();
                    message.what=3;
                    handler.sendMessage(message);
                    return;
                }

                if(jsonData==null){
                    Message message=new Message();
                    message.what=3;
                    handler.sendMessage(message);
                }
                else{parseJSON(jsonData);}
            }
        }).start();
    }

    private void parseJSON(String jsonData){
        try {
            JSONObject jsonObject1=new JSONObject(jsonData);
            JSONObject jsonObject=new JSONObject(jsonObject1.getString("data"));
            JSONArray jsonArray=new JSONArray(jsonObject.getString("datas"));
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                ProjectionArticleBean articleBean=new ProjectionArticleBean();
                articleBean.setmTitle(jsonObject.getString("title"));
                articleBean.setmContent(jsonObject.getString("desc"));
                articleBean.setmAuthor(jsonObject.getString("author"));
                articleBean.setmTime(jsonObject.getString("niceDate"));
                articleBean.setmWebsite(jsonObject.getString("link"));
                mProjectionArticleBeans.add(articleBean);
            }
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
            e.printStackTrace();
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
        }


    }
}
