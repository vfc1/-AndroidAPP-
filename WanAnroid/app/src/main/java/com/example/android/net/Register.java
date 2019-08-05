package com.example.android.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.android.util.ConnectUtil;
import com.example.android.view.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class Register {

    private String web1="https://www.wanandroid.com/user/register?username=";
    private String web2="&password=";
    private String web3="&repassword=";
    private String mUserName;
    private String mPassWord;
    private String mRePassWord;
    private static class MyHandler extends Handler{
        WeakReference<LoginActivity> loginActivityWeakReference;

        public MyHandler(LoginActivity activity){
            loginActivityWeakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity mActivity=loginActivityWeakReference.get();
            if(mActivity!=null){
                switch (msg.what) {
                    case 1:
                        Toast.makeText(mActivity,"注册成功，请登录",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(mActivity,"网络连接失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(mActivity,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        }
    }
    private MyHandler handler;

    public Register(LoginActivity loginActivity,String userName,String passWord,String rePassWord){
        handler=new MyHandler(loginActivity);
        mUserName=URLEncoder.encode(userName);
        mPassWord=URLEncoder.encode(passWord);
        mRePassWord=URLEncoder.encode(rePassWord);
        register();
    }

    public void register(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Message message=new Message();
                String jsonData=ConnectUtil.read(ConnectUtil.connect(web1+mUserName+web2+mPassWord+web3+mRePassWord,"POST"));
                if(jsonData==null){
                    message.what=2;
                    handler.sendMessage(message);
                }else{
                    try {
                        JSONObject jsonObject=new JSONObject(jsonData);
                        String s=jsonObject.getString("errorCode");
                        if(s.equals("0")){
                            message.what=1;
                            handler.sendMessage(message);
                        }else{
                            message.what=3;
                            message.obj=jsonObject.get("errorMsg");
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
