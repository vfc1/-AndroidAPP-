package com.example.android.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.example.android.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mShare;
    private ImageButton mCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mShare=(ImageButton)findViewById(R.id.share);
        mCollect=(ImageButton)findViewById(R.id.collection);
        mShare.setOnClickListener(this);
        mCollect.setOnClickListener(this);
        //内置一个浏览器
        WebView webView=(WebView)findViewById(R.id.web_view);
        //让webview支持javaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //当跳转到另外一个网页任然在APP里显示而不是打开浏览器
        webView.setWebViewClient(new WebViewClient());
        //设置网页自适应屏幕，还有可以缩放，隐藏自带的缩放按钮
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webView.loadUrl(getIntent().getStringExtra("website"));
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                intent.putExtra(Intent.EXTRA_TEXT,getIntent().getStringExtra("website"));
                startActivity(Intent.createChooser(intent,getTitle()));
                 break;
            case R.id.collection:

                 break;
             default:
                 break;
        }
    }
}
