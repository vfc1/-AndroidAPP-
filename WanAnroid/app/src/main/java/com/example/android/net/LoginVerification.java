package com.example.android.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.android.presenter.ProjectionPresenter;
import com.example.android.util.ConnectUtil;
import com.example.android.view.LoginActivity;
import com.example.android.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LoginVerification {

    private LoginActivity mLoginActivity=null;
    private MainActivity mMainActivity=null;
    private String mUserName;
    private String mPassWord;
    private String web1="https://www.wanandroid.com/user/login?username=";
    private String web2="&password=";
    private List<String> setCookies;
    private static class MyHandler extends Handler{
        WeakReference<LoginActivity> loginActivityWeakReference;
        WeakReference<MainActivity> mainActivityWeakReference;

        public MyHandler(LoginActivity loginActivity,MainActivity mainActivity){
            loginActivityWeakReference=new WeakReference<>(loginActivity);
            mainActivityWeakReference=new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity mLoginActivity=loginActivityWeakReference.get();
            MainActivity mMainActivity=mainActivityWeakReference.get();
            switch (msg.what) {
                case 1:
                    if(mLoginActivity==null){
                        mMainActivity.loginSuccess((String) msg.obj);
                    }
                    else{
                        mLoginActivity.toastShow("登录成功");
                        mLoginActivity.setText((String)msg.obj);
                    }
                    break;
                case 2:
                    if(mLoginActivity!=null){
                        mLoginActivity.toastShow("密码或账号错误，请重新输入");
                    }
                    break;
                case 3:
                    if(mLoginActivity!=null){
                        mLoginActivity.toastShow("网络连接断开");
                    }
                default:
                    break;
            }

        }
    }
    private MyHandler handler;



    public LoginVerification(LoginActivity loginActivity){

        mLoginActivity=loginActivity;
        handler=new MyHandler(loginActivity,null);
    }

    public LoginVerification(MainActivity mainActivity){
        mMainActivity=mainActivity;
        handler=new MyHandler(null,mainActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void login(final String userNmae, final String passWord){
        mUserName=userNmae;
        mPassWord=passWord;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String jsonData =null;
                mUserName=URLEncoder.encode(mUserName);
                mPassWord=URLEncoder.encode(mPassWord);
                HttpURLConnection connection=ConnectUtil.connect(web1+mUserName+web2+mPassWord, "POST");
                mUserName=userNmae;
                mPassWord=passWord;
                Message message=new Message();
                if(connection!=null){
                    Map<String,List<String>> cookies = connection.getHeaderFields();
                    setCookies = cookies.get("Set-Cookie");
                    jsonData=ConnectUtil.read(connection);
                    if(jsonData!=null) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonData);
                            if (jsonObject.getString("errorCode").equals("-1")) {
                                message.what=2;
                                handler.sendMessage(message);
                            } else {
                                message.what=1;
                                message.obj=userNmae;
                                handler.sendMessage(message);
                                layIn();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    message.what=3;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    //存储
    private void layIn(){
        SharedPreferences.Editor editor;
        Context context;
        if(mLoginActivity==null){context=mMainActivity;}else {context=mLoginActivity;}
        editor=context.getSharedPreferences("password",context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        editor.putString("username",mUserName);
        editor.putString("password",mPassWord);
        editor.apply();
        editor=context.getSharedPreferences("cookies",context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        String s=setCookies.get(0);
        for(int i=1;i<setCookies.size();i++){
            s=";"+setCookies.get(i);
        }
        editor.putString("cookies",s);
    }

}
