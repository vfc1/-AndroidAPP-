package com.example.android.util;

import android.content.Intent;
import android.util.Log;

import com.example.android.presenter.InternetPresenter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectUtil {
    public static String connect(String website,InternetPresenter internetPresenter){
        HttpURLConnection connection=null;
        BufferedReader reader=null;

        try {
            URL url=new URL(website);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //获取输入流
            InputStream in=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder response=new StringBuilder();
            String line;
            while ((line=reader.readLine())!=null){
                response.append(line);
            }
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            connection.disconnect();
        }

    }
}
