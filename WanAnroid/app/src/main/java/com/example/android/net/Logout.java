package com.example.android.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.example.android.util.ConnectUtil;
import com.example.android.view.MainActivity;

import java.net.HttpURLConnection;

public class Logout {

    private MainActivity mActivity;

    public Logout(MainActivity activity){
        mActivity=activity;
        layout();
    }

    public void layout(){
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    HttpURLConnection connection = ConnectUtil.connect("https://www.wanandroid.com/user/logout/json","GET");
                    if(connection!=null){ connection.disconnect();}
                        SharedPreferences.Editor editor=mActivity.getSharedPreferences("password",Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        editor=mActivity.getSharedPreferences("cookies",Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                }
            }).start();
    }

}
