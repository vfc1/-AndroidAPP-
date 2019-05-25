package com.example.android.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.BannerBean;
import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class BannerLoad {

    private homeFlagmentPresenter mPresenter;
    private List<BannerBean> bannerBeanList=new ArrayList<>();
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    bannerBeanList.get(msg.arg1).setDrawable((String)msg.obj,mPresenter);
                    break;
                default:
                    break;
            }
        }
    };

    public BannerLoad(homeFlagmentPresenter presenter){
        this.mPresenter=presenter;
    }

    public void load(final String website){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                HttpURLConnection connection=ConnectUtil.connect(website,"GET");
                String jsonData=ConnectUtil.read(connection);
                parseJSON(jsonData);
                //try {
               //     Thread.sleep(6000);
               // } catch (InterruptedException e) {
                //    e.printStackTrace();
               //     Log.i("bannerload","进程进入睡眠失败");
              //  }
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
                //bannerBean.setDrawable(jsonObject.getString("imagePath"),mPresenter);
                bannerBean.setWebsite(jsonObject.getString("url"));
                bannerBeanList.add(bannerBean);
                Message message=new Message();
                message.what=1;
                message.arg1=i;
                message.obj=jsonObject.getString("imagePath");
                handler.sendMessage(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("BannerLoad.parseJSON","数据解析异常");
        }

    }
}
