package com.example.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bean.HomeArticle;
import com.example.android.presenter.InternetPresenter;
import com.example.android.presenter.InternetPresenter;
import com.example.android.util.ConnectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private InternetPresenter internetPresenter;
    private List<HomeArticle> mHomeArticleList=new LinkedList<>();

    public MyIntentService() {

        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        internetPresenter=(InternetPresenter)intent.getSerializableExtra("presenter");
        String jsonData= ConnectUtil.connect("https://www.wanandroid.com/article/top/json",internetPresenter);
        Log.d("test",jsonData);
        parseJSONWithJSONObject(jsonData,"置顶    ");
        jsonData= ConnectUtil.connect("https://www.wanandroid.com/article/list/0/json",internetPresenter);
        parseJSONWithJSONObject(jsonData,"");
        internetPresenter.result(mHomeArticleList);
    }

    private void parseJSONWithJSONObject(String jsonData,String top){
        try {
            Log.d("test","问题在这里");
            JSONObject ajsonObject=new JSONObject(jsonData);
            ajsonObject=new JSONObject(ajsonObject.getString("data"));
            JSONArray jsonArray=new JSONArray(ajsonObject.getString("datas"));
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                HomeArticle homeArticle=new HomeArticle();
                homeArticle.setMtitle(jsonObject.getString("title"));
                homeArticle.setMauthor(top+jsonObject.getString("author"));
                homeArticle.setMreleaseTime(jsonObject.getString("niceDate"));
                homeArticle.setType(jsonObject.getString("superChapterName")+"/"+jsonObject.getString("chapterName"));
                homeArticle.setmWebsite(jsonObject.getString("link"));
                mHomeArticleList.add(homeArticle);
            }
        } catch (JSONException e) {
            internetPresenter.connectionfailed("数据解析失败");
            e.printStackTrace();
        }
    }


}
