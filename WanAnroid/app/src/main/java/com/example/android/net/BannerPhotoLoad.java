package com.example.android.net;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.BannerBean;
import com.example.android.presenter.ProjectionPresenter;
import com.example.android.presenter.HomeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

import java.lang.ref.WeakReference;

public class BannerPhotoLoad {

    private BannerBean mbanner;

    private static class MyHandler extends Handler{
        WeakReference<HomeFlagmentPresenter> presenterWeakReference;
        BannerBean mbanner;

        public MyHandler(HomeFlagmentPresenter presenter,BannerBean bannerBean){
            presenterWeakReference=new WeakReference<>(presenter);
            mbanner=bannerBean;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            HomeFlagmentPresenter mPresenter=presenterWeakReference.get();
            if(mPresenter!=null){
                switch (msg.what) {
                    case 1:
                        mPresenter.bannerRefresh(mbanner);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private MyHandler handler;

    public BannerPhotoLoad(BannerBean banner, final String website, HomeFlagmentPresenter presenter){
        this.mbanner=banner;
        handler=new MyHandler(presenter,banner);
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Drawable drawable = ConnectUtil.loadPhoto(website);
                mbanner.setDrawable(drawable);
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
