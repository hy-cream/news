package com.example.tab_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 胡钰 on 2016/7/29.
 */

//用了volley框架，解析加载网络图片
public class Jsonimages {

    private RequestQueue mqueue;
    private String url;
    private Handler mHandler;
    private List<Bitmap> mbitmaplist=new ArrayList<Bitmap>();
    private int width,height;

    public Jsonimages(RequestQueue mqueue, String url, Handler mHandler, int width,int height){
        this.mqueue=mqueue;
        this.url=url;
        this.width=width;
        this.height=height;
        this.mHandler=mHandler;
    }

  public void jsonimage(){

      ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
          @Override
          public void onResponse(Bitmap bitmap) {

              for (int i=0;i<10;i++){
                  mbitmaplist.add(i,bitmap);
                  Log.i("images",bitmap.toString());
              }

              Message msg = new Message();
              msg.what = 0x12345;
              msg.obj=mbitmaplist;
              mHandler.sendMessage(msg);
          }
      }, width,height, Bitmap.Config.ARGB_8888,new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
              Log.i("error",volleyError.toString());
          }
      });

      mqueue.add(imageRequest);
  }

}
