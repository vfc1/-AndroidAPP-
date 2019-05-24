package com.example.android.service;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.BannerBean;
import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

public class BannerPhotoLoad {

    private BannerBean mbanner;
    private homeFlagmentPresenter mPresenter;
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.bannerRefresh(mbanner);
                    break;
                default:
                    break;
            }
        }
    };

    public BannerPhotoLoad(BannerBean banner, final String website,homeFlagmentPresenter presenter){
        this.mbanner=banner;
        this.mPresenter=presenter;
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
