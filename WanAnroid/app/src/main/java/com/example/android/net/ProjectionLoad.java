package com.example.android.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.ProjectonBean;
import com.example.android.presenter.ProjectionPresenter;
import com.example.android.util.ConnectUtil;
import com.example.android.view.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ProjectionLoad {

    private ProjectionPresenter mPresenter;
    private List<ProjectonBean> mProjectonBeans=new ArrayList<>();
    private static class MyHandler extends Handler{
        WeakReference<ProjectionPresenter> presenterWeakReference;
        List<ProjectonBean> mProjectonBeans;

        public MyHandler(ProjectionPresenter presenter,List<ProjectonBean> list){
            presenterWeakReference=new WeakReference<>(presenter);
            mProjectonBeans=list;
        }

        @Override
        public void handleMessage(Message msg) {
            ProjectionPresenter mPresenter=presenterWeakReference.get();
            if(mPresenter!=null&&mProjectonBeans!=null){
                switch (msg.what) {
                    case 1:
                        mPresenter.result(mProjectonBeans);
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
        }
    }
    private MyHandler handler;

    public ProjectionLoad(ProjectionPresenter presenter){
        handler=new MyHandler(presenter,mProjectonBeans);
        this.mPresenter=presenter;
        load();
    }

    private void load(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String jsonData =ConnectUtil.read( ConnectUtil.connect("https://www.wanandroid.com/project/tree/json","GET"));
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
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                ProjectonBean projectonBean=new ProjectonBean();
                projectonBean.setID(jsonObject.getString("id"));
                projectonBean.setName(jsonObject.getString("name"));
                mProjectonBeans.add(projectonBean);
            }
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
        }
    }
}
