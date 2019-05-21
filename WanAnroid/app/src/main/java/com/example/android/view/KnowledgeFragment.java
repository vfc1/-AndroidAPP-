package com.example.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.R;
import com.example.android.adapter.KnowLegedListAdapter;
import com.example.android.bean.KnowledgeBean;
import com.example.android.presenter.KnowledgeFragPresenter;
import com.example.android.presenter.homeFlagmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeFragment extends Fragment {

    private View view;
    private KnowledgeFragPresenter mPresenter;
    private Activity mActivity;
    private List<KnowledgeBean> mKnowledgeBeanList=new ArrayList<>();
    private ListView mListView;
    private KnowLegedListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(R.layout.knowledge_fragment,container,false);
        mActivity=(Activity) getContext();
        mPresenter=new KnowledgeFragPresenter(mActivity,this);
        mListView=(ListView)view.findViewById(R.id.list_view);
        mAdapter=new KnowLegedListAdapter(getContext(),R.layout.knowledge_classification,mKnowledgeBeanList);
        load();
        mListView.setAdapter(mAdapter);
        return view;
    }

    private void load(){
        mPresenter.load("https://www.wanandroid.com/tree/json");
    }

    public void refresh(List<KnowledgeBean> knowledgeBeanList){
        for(int i=0;i<knowledgeBeanList.size();i++){
            mKnowledgeBeanList.add(knowledgeBeanList.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }
}
