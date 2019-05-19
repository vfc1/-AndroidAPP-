package com.example.android.service;

import android.graphics.drawable.Drawable;

import com.example.android.bean.BannerBean;
import com.example.android.util.ConnectUtil;

public class BannerPhotoLoad {

    private BannerBean mbanner;

    public BannerPhotoLoad(BannerBean banner, final String website){
        this.mbanner=banner;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = ConnectUtil.loadPhoto(website);
                mbanner.setDrawable(drawable);
            }
        }).start();
    }
}
