package com.example.android.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.adapter.KnowArticlePager;
import com.example.android.adapter.KnowLegedListAdapter;
import com.example.android.bean.ArticleBean;
import com.example.android.bean.KnowledgeBean;
import com.example.android.presenter.KnowDetailPresenter;
import com.example.android.presenter.KnowledgeFragPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowLedgeDetailActivity extends AppCompatActivity {

    private Intent intent;
    private final String web1="https://www.wanandroid.com/article/list/";
    private final String web2="/json?cid=";
    private int i=0;
    private List<View> mViewList=new ArrayList<>();
    private List<AticleViewAdapter> mLIistadapter=new ArrayList<>();
    private List<List<ArticleBean>> lists=new ArrayList<>();
    private KnowDetailPresenter mPresenter;
    private KnowArticlePager mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_ledge_detail);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.mytab);
        intent=getIntent();
        List<String> childName= intent.getStringArrayListExtra("childName");
        List<String> Id=intent.getStringArrayListExtra("ID");
        int start=intent.getIntExtra("where",0);
        ViewPager viewPager=(ViewPager) findViewById(R.id.view_pager);
        mPresenter=new KnowDetailPresenter(this);

        for(int i=0;i< Id.size();i++){
            mPresenter.articleLoad(web1+0+web2+ Id.get(i),i);
            ListView listView=new ListView(this);
            final List<ArticleBean> articleBeanList=new ArrayList<>();
            lists.add(articleBeanList);
            AticleViewAdapter aticleViewAdapter=new AticleViewAdapter(this,R.layout.article_item,articleBeanList);
            listView.setAdapter(aticleViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ArticleBean article=articleBeanList.get(position);
                    Intent intent=new Intent(KnowLedgeDetailActivity.this,WebActivity.class);
                    intent.putExtra("website",article.getmWebsite());
                    startActivity(intent);
                }
            });
            mLIistadapter.add(aticleViewAdapter);
            mViewList.add(listView);
        }
        //注意必须在给adapter添加list之后再添加添加给viewpager否则就应该增加刷新的代码，因为每次改变数据之后都要刷新，不然报错
        mAdapter=new KnowArticlePager(mViewList,childName);
        viewPager.setAdapter(mAdapter);
        //for(int i=0;i<childName.size();i++) {
            //tabLayout.addTab(tabLayout.newTab().setText(childName.get(i)));
       // }
        //关联
        tabLayout.setupWithViewPager(viewPager);
        //进入时选定的状态
        tabLayout.getTabAt(start).select();
    }

    public void refreash(List<ArticleBean> articleBeanList,int i){
        List<ArticleBean> articleBeans =lists.get(i);
        for(int j=0;j<articleBeanList.size();j++){
            articleBeans.add(articleBeanList.get(j));
        }
        mLIistadapter.get(i).notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }
}
