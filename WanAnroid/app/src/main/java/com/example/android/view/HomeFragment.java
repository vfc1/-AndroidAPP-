package com.example.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.adapter.BannerAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.BannerBean;
import com.example.android.presenter.homeFlagmentPresenter;

import java.util.ArrayList;

import java.util.List;

public class  HomeFragment extends Fragment {

    private AticleViewAdapter madapter;
    private homeFlagmentPresenter mpresenter;
    private Activity mActivity;
    private SwipeRefreshLayout swipeRefresh;
    private final String web1="https://www.wanandroid.com/article/list/";
    private final String web2="/json";
    //判断页数
    private int i=0;
    private ViewPager viewPager;
    private  ViewpagerTime viewpagerTime;
    private RelativeLayout relativeLayout;
    //判断是否为下拉
    private Boolean down=true;
    private boolean down1=false;
    //用来给onstart方法判断是不是第一次开始活动
    private boolean start=true;

    private List<View> mViewList=new ArrayList<View>();

    List<ArticleBean> mHomeArticleList=new ArrayList<>();
    BannerAdapter bannerAdapter;

    AbsListView.OnScrollListener scrollListener=new AbsListView.OnScrollListener() {

        boolean isLastRow = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当滚到最后一行且停止滚动时，执行加载
            if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                i++;
                //将正在加载显示出来
                relativeLayout.setPadding(0,0,0,0);
                loadAticle(web1+i+web2,i);
                isLastRow = false;
                down=false;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //判断是否滚到最后一行
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                isLastRow = true;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        mActivity=(Activity)getContext();
        mpresenter=new homeFlagmentPresenter(mActivity,this);
/////////////////////////////////////////////////
        mpresenter.bannerLoad("https://www.wanandroid.com/banner/json");
 /////////////////////////////////////////////////////
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorBulue);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAticle(web1+0+web2,-1);
                //mpresenter.bannerLoad("https://www.wanandroid.com/banner/json");
                swipeRefresh.setRefreshing(false);
                down=true;
                down1=true;
            }
        });

        loadAticle(web1+i+web2,-1);
        madapter= new AticleViewAdapter(mActivity, R.layout.article_item, mHomeArticleList);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(madapter);
        ////////////////////////////
        viewPager=(ViewPager)getLayoutInflater().inflate(
        R.layout.view_pager_item,  null );
        bannerAdapter=new BannerAdapter(mViewList,viewPager);
        //设置ViewPager的宽和高
        viewPager.setLayoutParams(new  ListView.LayoutParams(
        ListView.LayoutParams.MATCH_PARENT,620));
        viewPager.setAdapter(bannerAdapter);
        listView.addHeaderView(viewPager);
        //要求父容易不要响应touch事件
        viewPager.requestDisallowInterceptTouchEvent(true);
        //开始计时，自动切换图片
        viewpagerTime= new ViewpagerTime(viewPager);
        /////////////////////
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //因为添加了headview，占一个位置
                ArticleBean homeArticle=mHomeArticleList.get(position-1);
                Intent intent=new Intent(mActivity,WebActivity.class);
                intent.putExtra("website",homeArticle.getmWebsite());
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////
        relativeLayout =(RelativeLayout) getLayoutInflater().inflate(
                R.layout.loading_item,  null );
        relativeLayout.setLayoutParams(new  ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,150));
        listView.addFooterView(relativeLayout);
        relativeLayout.setPadding(0,-relativeLayout.getHeight(),0,0);
        listView.setOnScrollListener(scrollListener);
        /////////////////////////////////////////////////////
        return view;
    }

    public void artitleRefresh(List<ArticleBean> homeArticleList){
        if(mHomeArticleList!=null&&down){
            this.i=0;
            mHomeArticleList.clear();
        }
        for(int i=0;i<homeArticleList.size();i++){
            mHomeArticleList.add(homeArticleList.get(i));
        }
        relativeLayout.setPadding(0,-relativeLayout.getHeight(),0,0);
        madapter.notifyDataSetChanged();
        down=false;
    }

    public void loadAticle(String website,int i){
        mpresenter.articleLoad(website,i);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void bannerRefresh(final BannerBean bannerBean){

        if(mViewList!=null&&down1){mViewList.clear();down1=false;}
       // for(int i=0;i<bannerBeanList.size();i++) {
        //    mBannerBean=bannerBeanList.get(i);
          //  ImageButton imageButton = new ImageButton(mActivity);
            //imageButton.setBackground(mBannerBean.getDrawable());
        //    imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
         //   imageButton.setOnClickListener(new Button.OnClickListener() {
          //      @Override
             //   public void onClick(View v) {
         //           Intent intent=new Intent(mActivity,WebActivity.class);
         //           intent.putExtra("website",mBannerBean.getWebsite());
         //           startActivity(intent);
          //      }
        //    });
        //    mViewList.add(imageButton);
        //}
        ImageButton imageButton = new ImageButton(mActivity);
        imageButton.setBackground(bannerBean.getDrawable());
        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
        final Intent intent=new Intent(mActivity,WebActivity.class);
        intent.putExtra("website",bannerBean.getWebsite());
        imageButton.setOnClickListener(new Button.OnClickListener() {
                  @Override
               public void onClick(View v) {
                       startActivity(intent);
                  }
                });
        mViewList.add(imageButton);
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(start==false){
            viewpagerTime.clockStart();
        }
        start=false;
    }

    @Override
    public void onStop() {
        super.onStop();
        viewpagerTime.stop();
    }
}
