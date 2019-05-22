package com.example.android.service;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.bean.BannerBean;
import com.example.android.util.ConnectUtil;

public class BannerPhotoLoad {

    private BannerBean mbanner;

    public BannerPhotoLoad(BannerBean banner, final String website){
        this.mbanner=banner;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Drawable drawable = ConnectUtil.loadPhoto(website);
                mbanner.setDrawable(drawable);
            }
        }).start();
    }
}
