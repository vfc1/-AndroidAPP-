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
import com.example.android.adapter.KnowLegedListAdapter;
import com.example.android.bean.KnowledgeBean;
import com.example.android.presenter.KnowledgeFragPresenter;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeFragment extends Fragment {

    private KnowledgeFragPresenter mPresenter;
    private List<KnowledgeBean> mKnowledgeBeanList=new ArrayList<>();//存放体系每一个框的信息
    private KnowLegedListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view;
        Activity mActivity;
        ListView mListView;
        view=inflater.inflate(R.layout.knowledge_fragment,container,false);
        mActivity=(Activity) getContext();
        mPresenter=new KnowledgeFragPresenter(mActivity,this);
        mListView=(ListView)view.findViewById(R.id.list_view);
        mAdapter=new KnowLegedListAdapter(getContext(),R.layout.knowledge_classification,mKnowledgeBeanList);
        load();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KnowledgeBean knowledgeBean=mKnowledgeBeanList.get(position);
                Intent intent=new Intent(getContext(), KnowLedgeDetailActivity.class);
                intent.putStringArrayListExtra("childName",(ArrayList<String>)knowledgeBean.getmChildName());
                intent.putStringArrayListExtra("ID",(ArrayList<String>) knowledgeBean.getmID());
                intent.putExtra("title",knowledgeBean.getMfatherName());
                intent.putExtra("where",0);
                startActivity(intent);
            }
        });
        return view;
    }

    private void load(){
        mPresenter.load("https://www.wanandroid.com/tree/json");
    }

    public void refresh(List<KnowledgeBean> knowledgeBeanList){
        mKnowledgeBeanList.addAll(knowledgeBeanList);
        mAdapter.notifyDataSetChanged();
    }
}
