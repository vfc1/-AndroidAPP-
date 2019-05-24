package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.HotWebsiteBean;
import com.example.android.presenter.SeaechTipsFraPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

public class HotWebsiteLoad {

    private SeaechTipsFraPresenter mPresenter;
    private List<HotWebsiteBean> mHotWebsiteBeans=new LinkedList<>();
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.refreashHotWebsite(mHotWebsiteBeans);
                    break;
                case 2:
                    mPresenter.connectionfailed("数据解析失败");
                    break;
                case 3:
                    mPresenter.connectionfailed("网络连接失败");
                default:
                    break;
            }
        }
    };

    public HotWebsiteLoad(SeaechTipsFraPresenter presenter){
        this.mPresenter=presenter;
        load();
    }

    private void load() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                HttpURLConnection connection = ConnectUtil.connect("https://www.wanandroid.com/friend/json","GET");
                String jsonData = ConnectUtil.read(connection);
                if(jsonData!=null){parseJSON(jsonData);}
                else{
                    Message message=new Message();
                    message.what=3;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    private void parseJSON(String jsonData){
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
            for(int i=0;i<jsonArray.length();i++){
                HotWebsiteBean hotWebsiteBean=new HotWebsiteBean();
                jsonObject=jsonArray.getJSONObject(i);
                hotWebsiteBean.setmName(jsonObject.getString("name"));
                hotWebsiteBean.setmWebsite(jsonObject.getString("link"));
                mHotWebsiteBeans.add(hotWebsiteBean);
            }
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            Message message=new Message();
            message.what=2;
            handler.sendMessage(message);
            e.printStackTrace();
        }

    }
}

