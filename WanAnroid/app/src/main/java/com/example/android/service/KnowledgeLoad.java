package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.KnowledgeBean;
import com.example.android.presenter.KnowledgeFragPresenter;
import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeLoad {
    private KnowledgeFragPresenter mPresenter;
    private List<KnowledgeBean> knowledgeBeanList = new ArrayList<>();
    private Message message = new Message();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.result(knowledgeBeanList);
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

    public KnowledgeLoad(KnowledgeFragPresenter presenter, final String website) {
        this.mPresenter = presenter;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                String jsonData = ConnectUtil.read(ConnectUtil.connect(website,"GET"));
                if(jsonData==null){
                    Message message=new Message();
                    message.what=3;
                    handler.sendMessage(message);
                }
                else{parseJSON(jsonData); }
            }
        }).start();
    }

    private void parseJSON(String jsonDate){
        try {
            JSONArray jsonArrayAll=new JSONArray(new JSONObject(jsonDate).getString("data"));
            for(int i=0;i<jsonArrayAll.length();i++){
                KnowledgeBean knowledgeBean=new KnowledgeBean();
                JSONObject jsonObject1=jsonArrayAll.getJSONObject(i);
                knowledgeBean.setMfatherName(jsonObject1.getString("name"));
                JSONArray jsonArray=new JSONArray(jsonObject1.getString("children"));
                for(int j=0;j<jsonArray.length();j++){
                    JSONObject jsonObject=jsonArray.getJSONObject(j);
                    knowledgeBean.setmChildName(jsonObject.getString("name"));
                    knowledgeBean.setmID(jsonObject.getString("id"));
                }
                knowledgeBeanList.add(knowledgeBean);
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
