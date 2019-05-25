package com.example.android.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.R;
import com.example.android.presenter.SearchActivityPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private ImageButton ib_search;
    private SearchTipsFragment searchTipsFragment;
    private SearchDetailFragment searchDetailFragment;
    private FragmentManager manager;
    protected EditText editText;
    //存放历史记录
    private List<String> history=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SearchActivityPresenter(this).loadHistory();
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        ib_back=findViewById(R.id.ib_back);
        ib_back.setOnClickListener(this);
        ib_search=findViewById(R.id.title_search);
        ib_search.setOnClickListener(this);
        searchTipsFragment=new SearchTipsFragment();
        editText=(EditText)findViewById(R.id.edit_query);
        editText.setOnClickListener(this);
        manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.sheach_tips,searchTipsFragment);
        transaction.commit();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.title_search:
                String s=editText.getText().toString();
                if(!s.equals("")){
                    if(history.contains(s)){history.remove(s);}
                    history.add(s);
                    FragmentTransaction transaction1=manager.beginTransaction();
                    transaction1.hide(searchTipsFragment);
                    if(searchDetailFragment==null){
                        searchDetailFragment=new SearchDetailFragment();
                        transaction1.add(R.id.sheach_tips,searchDetailFragment);
                    }else{
                        transaction1.show(searchDetailFragment);
                    }
                    transaction1.commit();
                    searchDetailFragment.mBool=true;
                    searchDetailFragment.resultLoad(s);

                }
                else{
                    Toast.makeText(this,"请输入内容",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.edit_query :
                FragmentTransaction transaction2=manager.beginTransaction();
                if (searchDetailFragment != null) {
                    transaction2.hide(searchDetailFragment);
                }
                if(searchTipsFragment==null){
                    searchTipsFragment=new SearchTipsFragment();
                    transaction2.add(R.id.sheach_tips,searchTipsFragment);
                }else{
                    transaction2.show(searchTipsFragment);
                }
                transaction2.commit();
                break;
             default:
                 break;
        }
    }

    public void setText(String s){
        editText.setText(s);

    }

    //清空历史
    public void historyClear(){
        history.clear();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=getSharedPreferences("history",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor=getSharedPreferences("history",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Iterator<String> it=history.iterator();
        for(int i=history.size()-1;i>-1;i--){
            editor.putString(""+i,it.next());
        }
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void refreashHistory(List<String> strings){
        for(int i=strings.size()-1;i>-1;i--){
            history.add(strings.get(i));
        }
        searchTipsFragment.refreashHistory(strings);

    }
}
