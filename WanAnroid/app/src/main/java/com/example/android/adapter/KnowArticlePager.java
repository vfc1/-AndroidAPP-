package com.example.android.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class KnowArticlePager extends PagerAdapter {

    private List<View> viewList;

    public KnowArticlePager(List<View> viewList) {
        this.viewList=viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //获得指定位置的View,并增加到ViewPager中，同时作为当前页面的数据返回
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //当前位置和ViewPager中正显示的页面的位置的间隔是否超出一个页面，是则将当前页面移除
        container.removeView(viewList.get(position));
    }
}

