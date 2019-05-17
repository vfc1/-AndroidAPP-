package com.example.android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.AticleViewAdapter;
import com.example.android.bean.Article;
import com.example.android.presenter.InternetPresenter;

import java.util.ArrayList;

import java.util.List;

public class  HomeFragment extends Fragment {

    AticleViewAdapter madapter;
    InternetPresenter mpresenter;
    Activity mActivity;
    List<Article> mHomeArticleList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view=inflater.inflate(R.layout.home_fragment,container,false);
        mActivity=(Activity)getContext();
        mpresenter=new InternetPresenter(mActivity,this);
        load();
        madapter= new AticleViewAdapter(mActivity, R.layout.article_item, mHomeArticleList);
        ListView listView=(ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(madapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article homeArticle=mHomeArticleList.get(position);
                Intent intent=new Intent(mActivity,WebActivity.class);
                intent.putExtra("website",homeArticle.getmWebsite());
                startActivity(intent);
            }
        });
        return view;
    }

    public void refresh(List<Article> homeArticleList){
        for(int i=0;i<homeArticleList.size();i++){
            mHomeArticleList.add(homeArticleList.get(i));

        }
        madapter.notifyDataSetChanged();
    }

    public void load(){
        mpresenter.load();
    }

}
