package com.example.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.android.bean.ArticleBean;
import com.example.android.service.ArticleLoad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConnectUtil {
    public static HttpURLConnection connect(String website,String method){
        HttpURLConnection connection=null;

        try {
            URL url=new URL(website);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //获取输入流
            //InputStream in=connection.getInputStream();
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
            connection1=connect(website,"GET");
            InputStream in = connection1.getInputStream();
            if (in != null) {
                //获取到字节数组
                byte[] arr=streamToArr(in);
                //将字节数组转换成位图
                Bitmap bitmap= BitmapFactory.decodeByteArray(arr,0,arr.length);

                //Bitmap bitmap = BitmapFactory.decodeStream(in);
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

    //将输入流转换成字节数组
    public static byte[] streamToArr(InputStream inputStream){

        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len;

            while ((len=inputStream.read(buffer))!=-1){
                baos.write(buffer,0,len);
            }

            //关闭输出流
            baos.close();
            //关闭输入流
            inputStream.close();
            //返回字节数组
            return baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            //若失败，则返回空
            return null;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String read(HttpURLConnection connection) {

        StringBuilder response = new StringBuilder();
        BufferedReader reader=null;
        String line;
        try  {
            if(connection!=null){
                InputStream in=connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        return response.toString();
    }

    public static boolean checkConnect(Context context){

        ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null;
    }

    public static List<ArticleBean> parseJSON(String jsonData) {
        List<ArticleBean> list = new ArrayList<>();
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
                list.add(homeArticle);
            }
        } catch (JSONException e) {
            e.getStackTrace();
        }
        return list;
    }


}

