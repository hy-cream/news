package com.example.tab_test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡钰 on 2016/7/27.
 */

//用了volley框架，我把它专门放在一个类里来请求并解析数据
public class JsonHttp {
        private String url;
        private RequestQueue queue;
        private Handler mHandler;
        private  Map<String, String> map;


    public JsonHttp(String url,RequestQueue queue,Handler mHandler){
            this.url=url;
            this.queue=queue;
            this.mHandler=mHandler;
        }

        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

//我觉得不用留太多，50条应该够了
        public JsonHttp jsonjiexi(int num) {

            if(list.size()>50){
                list.clear();
            }


          JsonObjectRequest jsonor= new JsonObjectRequest(url+num, null, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject jsonObject) {

                  //获得json数据,开始解析
                  try {
//                        if(jsonObject.getString("error")==false){
//                        count=jsonObject.getInt("count");
//                    Log.i("count============", String.valueOf(count));

                      JSONArray array = jsonObject.getJSONArray("results");

                      for (int i = 0; i < array.length(); i++) {

                          JSONObject object = (JSONObject) array.opt(i);

                          map = new HashMap<String, String>();

                          map.put("_id", object.optString("_id"));
                          map.put("createdAt", object.optString("createdAt"));
                          map.put("desc", object.optString("desc"));
                          map.put("publishedAt", object.optString("publishedAt"));
                          map.put("source", object.optString("source"));
                          map.put("type", object.optString("type"));
                          map.put("url", object.optString("url"));
                          map.put("used", object.optString("used"));
                          map.put("who", object.optString("who"));

                          list.add(i, (HashMap<String, String>) map);
                      }
                      //测试是否存入了数据
//                        for (int i=0;i<list.size();i++) {
//
//                            Log.i("key",list.get(i).get("url").toString());
//
//                        }
                      //子线程执行更慢，用handler发送消息提醒
                      Message msg = new Message();
                      msg.what = 0x1234;
                      msg.obj = list;
                      mHandler.sendMessage(msg);

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
//                Log.i("error----------------------",volleyError.getMessage().toString());
                }
            });

            queue.add(jsonor);

            return null;
        }
}
