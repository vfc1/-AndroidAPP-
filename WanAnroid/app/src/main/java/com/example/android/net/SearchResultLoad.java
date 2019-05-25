package com.example.android.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.bean.ArticleBean;
import com.example.android.presenter.SearchDetailPresenter;
import com.example.android.util.ConnectUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchResultLoad {

    private SearchDetailPresenter mPresenter;
    private List<ArticleBean> mArticleList = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mPresenter.refreashResult(mArticleList);
                    mArticleList=new ArrayList<>();
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

    public SearchResultLoad(SearchDetailPresenter presenter){
        this.mPresenter=presenter;
    }

    public void load(final String website){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Message message = new Message();
                    String jsonData = ConnectUtil.read(ConnectUtil.connect(website, "POST"));
                    if (jsonData == null) {
                        message.what = 3;
                        handler.sendMessage(message);
                    } else {
                        message.what = 1;
                        listAdd(ConnectUtil.parseJSON(jsonData));
                        handler.sendMessage(message);
                    }
                }
        }).start();
    }

    private void listAdd(List<ArticleBean> list){
        for(int i=0;i<list.size();i++){
            mArticleList.add(list.get(i));
        }
    }

}
