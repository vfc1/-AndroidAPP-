package com.example.android.bean;

import android.graphics.drawable.Drawable;

import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.service.BannerPhotoLoad;

public class BannerBean {

    private Drawable drawable;
    private String website;

    //拿到网址后开始获取图片，多线程下载，加快加载速度
    public void setDrawable(String website, homeFlagmentPresenter presenter) {
        new BannerPhotoLoad(this,website,presenter);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }
}
