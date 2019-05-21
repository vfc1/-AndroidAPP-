package com.example.android.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.R;
import com.example.android.bean.KnowledgeBean;

import java.util.ArrayList;
import java.util.List;

public class KnowLedgeDetailActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_ledge_detail);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.mytab);
        intent=getIntent();
        List<String> childName= intent.getStringArrayListExtra("childName");
        int start=intent.getIntExtra("where",0);
        for(int i=0;i<childName.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(childName.get(i)));
        }
        //tabLayout.setSelectedTabIndicator(start);
    }
}
