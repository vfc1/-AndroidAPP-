package com.example.android.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.adapter.BannerAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.BannerBean;
import com.example.android.presenter.InternetPresenter;

import java.util.ArrayList;

import java.util.List;

public class  HomeFragment extends Fragment {

    private AticleViewAdapter madapter;
    private InternetPresenter mpresenter;
    private Activity mActivity;
    private SwipeRefreshLayout swipeRefresh;
    private final String web1="https://www.wanandroid.com/article/list/";
    private final String web2="/json";
    private int i=0;
    private ViewPager viewPager;
    private BannerBean mBannerBean;

    private List<View> mViewList=new ArrayList<View>();

    List<ArticleBean> mHomeArticleList=new ArrayList<>();
    BannerAdapter bannerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        mActivity=(Activity)getContext();
        mpresenter=new InternetPresenter(mActivity,this);
/////////////////////////////////////////////////
        mpresenter.bannerLoad("https://www.wanandroid.com/banner/json");
 /////////////////////////////////////////////////////
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorBulue);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAticle(web1+0+web2,0);
                mpresenter.bannerLoad("https://www.wanandroid.com/banner/json");
                swipeRefresh.setRefreshing(false);
            }
        });

        loadAticle(web1+i+web2,i);
        madapter= new AticleViewAdapter(mActivity, R.layout.article_item, mHomeArticleList);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(madapter);
        ////////////////////////////
        bannerAdapter=new BannerAdapter(mViewList);
        viewPager=(ViewPager)getLayoutInflater().inflate(
        R.layout.view_pager_item,  null );
        //设置ViewPager的宽和高
        viewPager.setLayoutParams(new  ListView.LayoutParams(
        ListView.LayoutParams.MATCH_PARENT,620));
        viewPager.setAdapter(bannerAdapter);
        listView.addHeaderView(viewPager);
        /////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleBean homeArticle=mHomeArticleList.get(position);
                Intent intent=new Intent(mActivity,WebActivity.class);
                intent.putExtra("website",homeArticle.getmWebsite());
                startActivity(intent);
            }
        });
        return view;
    }

    public void artitleRefresh(List<ArticleBean> homeArticleList){
        if(mHomeArticleList!=null){
            mHomeArticleList.clear();
        }
        for(int i=0;i<homeArticleList.size();i++){
            mHomeArticleList.add(homeArticleList.get(i));
        }
        madapter.notifyDataSetChanged();
    }

    public void loadAticle(String website,int i){
        mpresenter.articleLoad(website,i);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bannerRefresh(List<BannerBean> bannerBeanList){

        if(mViewList!=null){mViewList.clear();}
        for(int i=0;i<bannerBeanList.size();i++) {
            mBannerBean=bannerBeanList.get(i);
            ImageButton imageButton = new ImageButton(mActivity);
            imageButton.setBackground(mBannerBean.getDrawable());
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
            imageButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mActivity,WebActivity.class);
                    intent.putExtra("website",mBannerBean.getWebsite());
                    startActivity(intent);
                }
            });
            mViewList.add(imageButton);
        }
        bannerAdapter.notifyDataSetChanged();
    }

}
