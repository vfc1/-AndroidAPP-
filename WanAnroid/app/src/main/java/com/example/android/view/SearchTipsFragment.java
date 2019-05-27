package com.example.android.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.android.R;
import com.example.android.bean.HotWebsiteBean;
import com.example.android.presenter.SeaechTipsFraPresenter;
import com.example.android.util.ConnectUtil;
import com.example.android.widget.MyViewGroup;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchTipsFragment extends Fragment {

    private SearchActivity mActivity;
    private MyViewGroup mHistory;
    private MyViewGroup mHotWord;
    private MyViewGroup mWebsite;
    private List mStrings;
    private List<String> strings=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_tips_fragment, container, false);
        SeaechTipsFraPresenter mPresenter;
        mPresenter=new SeaechTipsFraPresenter(this);
        mHistory=(MyViewGroup)view.findViewById(R.id.historical_records);
        mHistory.setSpacing(20,20,20,20);
        mHotWord=(MyViewGroup)view.findViewById(R.id.hot_words);
        mHotWord.setSpacing(20,20,20,20);
        mWebsite=(MyViewGroup)view.findViewById(R.id.website);
        mWebsite.setSpacing(20,20,20,20);
        mWebsite.increaseY(200);
        ImageButton delete=(ImageButton)view.findViewById(R.id.history_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.historyClear();
                mHistory.removeAllViews();
            }
        });
        mActivity=(SearchActivity)getContext();
        mPresenter.loadHotWords();
        mPresenter.loadHotWebsite();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHistory(List<String> strings){
        for(int i=0;i<strings.size();i++){
            Log.d("test",""+i);
            final String text=strings.get(i);
            final Button button=buttonSetUp(text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.setText(text);
                }
            });
            mHistory.addView(button);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWord(List<String> strings){
        this.mStrings=strings;
        for(int i=0;i<strings.size();i++){
            final String text=strings.get(i);
            final Button button=buttonSetUp(text);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.setText(text);
                }
            });
            mHotWord.addView(button);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHotWebsite(List<HotWebsiteBean> websiteBeans){
        for(int i=0;i<websiteBeans.size();i++){
           final HotWebsiteBean bean=websiteBeans.get(i);
           Button button=buttonSetUp(bean.getmName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mActivity,WebActivity.class);
                    intent.putExtra("website",bean.getmWebsite());
                    startActivity(intent);
                }
            });
            mWebsite.addView(button);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Button buttonSetUp( String text){
        Button button=new Button(mActivity);
        button.setText(text);
        //为按钮设置背景图片
        Resources resources = getContext().getResources();
        Drawable drawable = resources.getDrawable(R.drawable.rectangle);
        button.setBackground(drawable);
        button.setTextSize(12);
        button.setTextColor(mActivity.getResources().getColor(R.color.colorOrange));
        button.setMinWidth(0);//Button中的方法    改变Button(TextView)中的mMinWidth
        button.setMinHeight(0);//Button中的方法   改变Button(TextView)中的mMinHeight
        button.setMinimumHeight(0);//View中的方法 改变View中的mMinHeight
        button.setMinimumWidth(0);//View中的方法  改变View中的mMinWidth
        button.setMaxLines(1);//设置最大行数只能为一
        return button;
    }



}
