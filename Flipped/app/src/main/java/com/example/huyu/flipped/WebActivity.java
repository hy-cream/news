package com.example.huyu.flipped;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    private ProgressBar mPbar;
    private WebView mWv;
    private String url;
    private WebSettings mWebsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mPbar= (ProgressBar) findViewById(R.id.web_progressbar);
        mWv= (WebView) findViewById(R.id.web_wv);
        url=getIntent().getStringExtra("url"); //获取传过来的url



    /**
     * 设置webview,初始化一些配置信息
     */

        /**
         * 为了不影响加载速度和受网速的影响，先不加载图片
         */
        if (Build.VERSION.SDK_INT>=19){
            // 对系统在19以上的版本做兼容。
            // 因为4.4以上系统在onPageFinished时再恢复图片加载时
            // 如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
            mWv.getSettings().setLoadsImagesAutomatically(true);
        }else {
            mWv.getSettings().setLoadsImagesAutomatically(false);
        }

        /**
         * websetting的设置
         */
        mWebsetting=mWv.getSettings();
        mWebsetting.setJavaScriptEnabled(true);
        /*设置缓存方式
        LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
        LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式。
        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
        LOAD_CACHE_ELSE_NETWORK：只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        */
        mWebsetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        /**
         * webviewclient设置
         */
        mWv.setWebViewClient(new WebViewClient(){
            //开始加载前
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            //加载完成后
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (!mWv.getSettings().getLoadsImagesAutomatically()){
                    mWv.getSettings().setLoadsImagesAutomatically(true);

                }
            }

            //加载错误时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(WebActivity.this,"加载错误",Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 设置加载网页时的进度条
         */

        mWv.setWebChromeClient(new WebChromeClient(){

            //重写onProgressChanged函数
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbar.setProgress(newProgress);
                if (newProgress==100){
                    mPbar.setVisibility(View.GONE);
                }
            }
        });


        //加载URL
        mWv.loadUrl(url);

    }


    /**
     * 返回上一个浏览页面，通过重写onkeyDown的方法实现返回上一个浏览界面而并非退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //其中在webView.canGoBack()在webVIew含有一个可后退的浏览记录时返回true
        if ((keyCode==KeyEvent.KEYCODE_BACK)&&mWv.canGoBack()){
            mWv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
