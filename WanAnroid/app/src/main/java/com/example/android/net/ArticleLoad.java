package com.example.android.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.ArticleBean;
import com.example.android.presenter.CollectionPresenter;
import com.example.android.presenter.KnowDetailPresenter;
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
    private CollectionPresenter mCollectionPresenter=null;
    private List<ArticleBean> mArticleList = new ArrayList<>();
    private Message message = new Message();
    //记录第几个标签
    private int tab;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mKnowPresenter != null) {
                        mKnowPresenter.articleResult(mArticleList, tab);
                    }
                    else if (mHomePresenter != null) {
                        mHomePresenter.articleResult(mArticleList);
                    }
                    else if(mCollectionPresenter!=null){
                        mCollectionPresenter.refreash(mArticleList);
                    }
                    break;
                case 2:

                    if (mKnowPresenter != null) {
                        mKnowPresenter.connectionfailed("数据解析失败");
                    }
                    if (mHomePresenter != null) {
                        mHomePresenter.connectionfailed("数据解析失败");
                    } else {
                        mCollectionPresenter.connectionfailed("数据解析失败");
                    }
                    break;
                case 3:
                    if (mHomePresenter != null) {
                        mHomePresenter.connectionfailed("网络连接失败");
                    }
                    if (mKnowPresenter != null) {
                        mKnowPresenter.connectionfailed("网络连接失败");
                    } else {
                        mCollectionPresenter.connectionfailed("网络连接失败");
                    }
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

    public ArticleLoad(CollectionPresenter presenter){
       mCollectionPresenter=presenter;
    }

    public ArticleLoad(homeFlagmentPresenter presenter,  String website,int i) {
        this.mHomePresenter = presenter;
        load(website, i);
    }

    public void load(final String website, final int i){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                message.what = 1;
                String topJSONdata = null;
                String jsonData =ConnectUtil.read( ConnectUtil.connect(website,"GET"));
                if (i == -1) {
                    topJSONdata =ConnectUtil.read( ConnectUtil.connect("https://www.wanandroid.com/article/top/json","GET"));
                }
                if (jsonData == null) {
                    message.what = 3;
                    handler.sendMessage(message);
                } else {
                    if (i == -1) {
                        parseTopJSON(topJSONdata);
                        listAdd(ConnectUtil.parseJSON(jsonData));
                        handler.sendMessage(message);
                    } else {
                        listAdd(ConnectUtil.parseJSON(jsonData));
                        handler.sendMessage(message);
                    }
                }

            }
        }).start();
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

    private void listAdd(List<ArticleBean> list){
        mArticleList.addAll(list);
    }
}