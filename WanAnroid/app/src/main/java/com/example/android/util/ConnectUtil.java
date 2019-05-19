package com.example.android.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.presenter.InternetPresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.InflaterInputStream;

public class ConnectUtil {
    public static HttpURLConnection connect(String website){
        HttpURLConnection connection=null;

        try {
            URL url=new URL(website);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //获取输入流
            InputStream in=connection.getInputStream();
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    static public Drawable loadPhoto(String website) {

        BufferedReader reader = null;
        HttpURLConnection connection1=null;

        try{
            connection1=connect(website);
            InputStream in = connection1.getInputStream();
            if (in != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return new BitmapDrawable(bitmap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection1!=null){
                connection1.disconnect();
            }
        }
       return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String read(HttpURLConnection connection) {

        StringBuilder response = new StringBuilder();

        String line;
        try (   InputStream in=connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return response.toString();
    }
}

