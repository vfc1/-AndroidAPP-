package com.example.android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.R;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
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
    }
}
