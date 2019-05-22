package com.example.android.service;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.presenter.ProjectionPresenter;
import com.example.android.util.ConnectUtil;

import java.util.Map;

public class ProjectionPhoto {

    private ProjectionPresenter mPresenter;
    private Map<Integer,Drawable> mMap;
    private int j;
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.listPhotoRefresh(j);
                    break;
                default:
                    break;
            }
        }
    };

    public ProjectionPhoto(ProjectionPresenter presenter ){
        this.mPresenter=presenter;
    }

    public void load(final String website, Map<Integer,Drawable> map, final int i,int j){
        this.j=j;
        this.mMap=map;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                mMap.put(i,ConnectUtil.loadPhoto(website)) ;
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
