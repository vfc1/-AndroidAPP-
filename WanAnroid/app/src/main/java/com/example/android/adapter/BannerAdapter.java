package com.example.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<View> viewList;
    private ViewPager viewPager;

    public BannerAdapter(List<View> viewList,ViewPager viewPager) {

        this.viewList = viewList;
        this.viewPager=viewPager;
    }

    @Override
    public int getCount() {
        if (viewList != null&&(viewList.size()>1)){
            //设置轮播最大值，等于无限循环
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {return view==object;}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //第二处修改，当前要显示的数据索引为集合长度
        if(viewList.size()!=0) {
            position = position % viewList.size();
        }
        //
        container.removeView(viewList.get(position));
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        position = position % viewList.size();
        container.removeView(viewList.get(position));
    }


}
