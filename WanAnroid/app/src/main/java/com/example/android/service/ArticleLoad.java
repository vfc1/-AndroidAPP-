package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.ArticleBean;
import com.example.android.presenter.KnowDetailPresenter;
import com.example.android.presenter.KnowledgeFragPresenter;
import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleLoad {

    private homeFlagmentPresenter mHomePresenter=null;
    private KnowDetailPresenter mKnowPresenter=null;
    private List<ArticleBean> mArticleList = new ArrayList<>();
    private Message message = new Message();
    //记录第几个标签
    private int tab;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(mKnowPresenter==null){mHomePresenter.articleResult(mArticleList);}
                    else{mKnowPresenter.articleResult(mArticleList,tab);}

                    break;
                case 2:
                    if(mKnowPresenter==null){mHomePresenter.connectionfailed("数据解析失败");}
                    else{mKnowPresenter.connectionfailed("数据解析失败");}
                    break;
                case 3:
                    if(mKnowPresenter==null){mHomePresenter.connectionfailed("网络连接失败");}
                    else{mKnowPresenter.connectionfailed("网络连接失败");}
                default:
                    break;
            }
        }
    };

    public ArticleLoad(KnowDetailPresenter knowDetailPresenter,String website,int i){
        this.mKnowPresenter=knowDetailPresenter;
        this.tab=i;
        load(website,i);

    }

    public ArticleLoad(homeFlagmentPresenter presenter,  String website,int i) {
        this.mHomePresenter = presenter;
        load(website, i);
    }

    private void load(final String website, final int i){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                message.what = 1;
                String topJSONdata = null;
                String jsonData =ConnectUtil.read( ConnectUtil.connect(website));
                if (i == -1) {
                    topJSONdata =ConnectUtil.read( ConnectUtil.connect("https://www.wanandroid.com/article/top/json"));
                }
                if (jsonData == null) {
                    message.what = 3;
                    handler.sendMessage(message);
                } else {
                    if (i == -1) {
                        parseTopJSON(topJSONdata);
                        parseJSON(jsonData);
                        handler.sendMessage(message);
                    } else {
                        parseJSON(jsonData);
                        handler.sendMessage(message);
                    }
                }

            }
        }).start();
    }

    private void parseJSON(String jsonData) {
        try {
            JSONObject ajsonObject = new JSONObject(jsonData);
            ajsonObject = new JSONObject(ajsonObject.getString("data"));
            JSONArray jsonArray = new JSONArray(ajsonObject.getString("datas"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ArticleBean homeArticle = new ArticleBean();
                homeArticle.setMtitle(jsonObject.getString("title"));
                homeArticle.setMauthor(jsonObject.getString("author"));
                homeArticle.setMreleaseTime(jsonObject.getString("niceDate"));
                homeArticle.setType(jsonObject.getString("superChapterName") + "/" + jsonObject.getString("chapterName"));
                homeArticle.setmWebsite(jsonObject.getString("link"));
                mArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
            Message message1 = new Message();
            message1.what = 2;
            handler.sendMessage(message1);
        }

    }

    private void parseTopJSON(String jsonData) {
        try {
            JSONObject ajsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = new JSONArray(ajsonObject.getString("data"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ArticleBean homeArticle = new ArticleBean();
                homeArticle.setMtitle(jsonObject.getString("title"));
                homeArticle.setMauthor(jsonObject.getString("author"));
                homeArticle.setMreleaseTime(jsonObject.getString("niceDate"));
                homeArticle.setType("置顶    " + jsonObject.getString("superChapterName") + "/" + jsonObject.getString("chapterName"));
                homeArticle.setmWebsite(jsonObject.getString("link"));
                mArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
            Message message1 = new Message();
            message1.what = 2;
            handler.sendMessage(message1);
        }
    }

}
