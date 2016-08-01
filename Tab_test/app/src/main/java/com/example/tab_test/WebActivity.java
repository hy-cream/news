package com.example.tab_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;


/**
 * Created by 胡钰 on 2016/7/28.
 */

//每个item的web
public class WebActivity extends AppCompatActivity {

    private WebView mwebView;
    private String url_web;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclick);
        mwebView= (WebView) findViewById(R.id.webView);
        intent=this.getIntent();
        url_web=intent.getStringExtra("url_web");

        Log.i("url","\""+url_web+"\"");

        mwebView.loadUrl(url_web);

    }
}
