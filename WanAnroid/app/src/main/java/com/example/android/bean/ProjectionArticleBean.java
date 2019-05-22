package com.example.android.bean;

import android.graphics.drawable.Drawable;

public class ProjectionArticleBean {

    private String mTitle;
    private String mContent;
    private String mAuthor;
    private String mTime;
    private Drawable mDrawable;
    private String mWebsite;
    private String mPictureLink;

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmWebsite(String mWebsite) {
        this.mWebsite = mWebsite;
    }

    public String getmWebsite() {
        return mWebsite;
    }

    public void setmDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

    public void setmPictureLink(String mPictureLink) {
        this.mPictureLink = mPictureLink;
    }

    public String getmPictureLink() {
        return mPictureLink;
    }

}
