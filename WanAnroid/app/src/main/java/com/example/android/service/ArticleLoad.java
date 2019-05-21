package com.example.android.service;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.ArticleBean;
import com.example.android.presenter.homeFlagmentPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleLoad {

    private homeFlagmentPresenter mPresenter;
    private List<ArticleBean> mHomeArticleList = new ArrayList<>();
    private Message message = new Message();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.articleResult(mHomeArticleList);
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

    public ArticleLoad(homeFlagmentPresenter presenter, final String website, final int i) {
        this.mPresenter = presenter;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                message.what = 1;
                String topJSONdata = null;
                String jsonData =ConnectUtil.read( ConnectUtil.connect(website));
                if (i == 0) {
                    topJSONdata =ConnectUtil.read( ConnectUtil.connect("https://www.wanandroid.com/article/top/json"));
                }
                if (jsonData == null) {
                    message.what = 3;
                    handler.sendMessage(message);
                } else {
                    if (i == 0) {
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
                mHomeArticleList.add(homeArticle);
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
                mHomeArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
            Message message1 = new Message();
            message1.what = 2;
            handler.sendMessage(message1);
        }
    }

}
