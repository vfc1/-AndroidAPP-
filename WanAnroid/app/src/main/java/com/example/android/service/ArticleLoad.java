package com.example.android.service;

import android.os.Handler;
import android.os.Message;

import com.example.android.bean.Article;
import com.example.android.presenter.InternetPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleLoad {

    private InternetPresenter mPresenter;
    private List<Article> mHomeArticleList=new ArrayList<>();
    private Message message=new Message();
    private Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.result(mHomeArticleList);
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

    public ArticleLoad(InternetPresenter presenter){
        this.mPresenter=presenter;
        new Thread(new Runnable() {
            @Override
            public void run() {
                message.what=1;
                String jsonDataTop = ConnectUtil.connect("https://www.wanandroid.com/article/top/json", mPresenter);
                String jsonData= ConnectUtil.connect("https://www.wanandroid.com/article/list/0/json",mPresenter);
                parseTopJSON(jsonDataTop);
                parseJSON(jsonData);
                if(jsonDataTop==null||jsonData==null){
                    message.what=3;
                    handler.sendMessage(message);
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    private void parseJSON(String jsonData) {
        try {
            JSONObject ajsonObject=new JSONObject(jsonData);
            ajsonObject=new JSONObject(ajsonObject.getString("data"));
            JSONArray jsonArray = new JSONArray(ajsonObject.getString("datas"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Article homeArticle = new Article();
                homeArticle.setMtitle(jsonObject.getString("title"));
                homeArticle.setMauthor(jsonObject.getString("author"));
                homeArticle.setMreleaseTime(jsonObject.getString("niceDate"));
                homeArticle.setType(jsonObject.getString("superChapterName")+"/"+jsonObject.getString("chapterName"));
                homeArticle.setmWebsite(jsonObject.getString("link"));
                mHomeArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
            Message message1=new Message();
            message1.what=2;
            handler.sendMessage(message1);
        }

    }

    private void parseTopJSON(String jsonData) {
        try {
            JSONObject ajsonObject=new JSONObject(jsonData);
            JSONArray jsonArray = new JSONArray(ajsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Article homeArticle = new Article();
                homeArticle.setMtitle(jsonObject.getString("title"));
                homeArticle.setMauthor(jsonObject.getString("author"));
                homeArticle.setMreleaseTime(jsonObject.getString("niceDate"));
                homeArticle.setType("置顶    "+jsonObject.getString("superChapterName")+"/"+jsonObject.getString("chapterName"));
                homeArticle.setmWebsite(jsonObject.getString("link"));
                mHomeArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
            Message message1=new Message();
            message1.what=2;
            handler.sendMessage(message1);
        }
    }

}
