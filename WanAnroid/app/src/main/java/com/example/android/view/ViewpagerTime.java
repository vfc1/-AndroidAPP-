package com.example.android.view;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.time.Clock;

import static java.lang.Thread.sleep;

public class ViewpagerTime {

    private ViewPager mViewPager;
    //用来判断结束线程
    private boolean run=true;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    break;
                default:
                    break;
             }
        }
    };

    public ViewpagerTime(ViewPager viewPager){
        this.mViewPager=viewPager;
        clockStart();
    }


    public void clockStart(){
        run=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run){
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.i("ViewpagerTime","进程进入睡眠失败");
                    }
                }

            }
        }).start();
    }

    public void stop(){
        run=false;
    }
}
