package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.BannerBean;
import com.example.android.presenter.InternetPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class BannerLoad {

    private InternetPresenter mPresenter;
    private List<BannerBean> mBannerBeanList=new ArrayList<>();

    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.bannerRefresh(mBannerBeanList);
                    break;
                default:
                    break;
            }
        }
    };

    public BannerLoad(InternetPresenter presenter){
        this.mPresenter=presenter;
    }

    public void load(final String website){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                HttpURLConnection connection=ConnectUtil.connect(website);
                String jsonData=ConnectUtil.read(connection);
                parseJSON(jsonData);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("bannerload","进程进入睡眠失败");
                }
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void parseJSON(String jsonData){

        try {
            JSONObject jsonObject1=new JSONObject(jsonData);
            JSONArray jsonArray=new JSONArray(jsonObject1.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BannerBean bannerBean=new BannerBean();
                bannerBean.setDrawable(jsonObject.getString("imagePath"));
                bannerBean.setWebsite(jsonObject.getString("url"));
                mBannerBeanList.add(bannerBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("BannerLoad.parseJSON","数据解析异常");
        }

    }
}
