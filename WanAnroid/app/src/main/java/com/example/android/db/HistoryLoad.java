package com.example.android.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.presenter.SeaechTipsFraPresenter;
import com.example.android.presenter.SearchActivityPresenter;
import com.example.android.util.ConnectUtil;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class HistoryLoad {

    private SearchActivityPresenter mPresenter;
    private Activity mActivity;
    private List<String> strings=new ArrayList<>();
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.refreashHistory(strings);
                    break;
                default:
                    break;
            }
        }
    };

    public HistoryLoad(SearchActivityPresenter presenter, Activity activity){
        this.mPresenter=presenter;
        this.mActivity=activity;
        loadhistory();
    }

    private void loadhistory(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                SharedPreferences pref=mActivity.getSharedPreferences("history", Context.MODE_PRIVATE);
                for(int i=0;;i++){
                    String s=pref.getString(""+i,null);
                    if(s==null){break;}
                    else{strings.add(s);}
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
