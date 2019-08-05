package com.example.android.net;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.ArticleBean;
import com.example.android.presenter.SearchDetailPresenter;
import com.example.android.util.ConnectUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SearchResultLoad {

    private List<ArticleBean> mArticleList ;
    private MyHandler handler;
    private SearchDetailPresenter mPresenter;

    private static class MyHandler extends Handler{

        WeakReference<SearchDetailPresenter> presenterWeakReference;
       WeakReference<List<ArticleBean>> listWeakReference;

        public MyHandler(SearchDetailPresenter presenter,List<ArticleBean> list){
            this.presenterWeakReference=new WeakReference<>(presenter);
            listWeakReference=new WeakReference<>(list);
        }

        @Override
        public void handleMessage(Message msg) {
            SearchDetailPresenter mPresenter=presenterWeakReference.get();
            List<ArticleBean> mArticleList=listWeakReference.get();
            if(mPresenter!=null){
                switch (msg.what) {
                    case 1:
                        mPresenter.refreashResult(mArticleList);
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

        }

    }

    public SearchResultLoad(SearchDetailPresenter presenter){
        mPresenter=presenter;
    }

    public void load(final String website){

        mArticleList = new ArrayList<>();
        handler=new MyHandler(mPresenter,mArticleList);

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
        mArticleList.addAll(list);
    }

}
