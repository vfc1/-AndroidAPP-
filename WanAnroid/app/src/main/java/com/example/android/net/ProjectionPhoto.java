package com.example.android.net;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.presenter.ProjectionPresenter;
import com.example.android.util.ConnectUtil;
import com.example.android.view.LoginActivity;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;

public class ProjectionPhoto {

    private Map<Integer,Drawable> mMap;
    private static class MyHandler extends Handler{
        WeakReference<ProjectionPresenter> presenterWeakReference;

        public MyHandler(ProjectionPresenter presenter){
            presenterWeakReference=new WeakReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            ProjectionPresenter mPresenter=presenterWeakReference.get();
            int j=msg.arg1;
            if(mPresenter!=null){
                switch (msg.what) {
                    case 1:
                        mPresenter.listPhotoRefresh(j);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private MyHandler handler;

    public ProjectionPhoto(ProjectionPresenter presenter ){
        handler=new MyHandler(presenter);
    }

    public void load(final String website, Map<Integer,Drawable> map, final int i, final int j){
        this.mMap=map;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                mMap.put(i, ConnectUtil.loadPhoto(website)) ;
                Message message=new Message();
                message.what=1;
                message.arg1=j;
                handler.sendMessage(message);
            }
        }).start();
    }
}
