package com.example.huyu.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.MeiZhiBean;
import com.example.huyu.bean.XiaBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huyu on 2017/3/10.
 */

public class HttpManager {

    private Handler handler;
    private Retrofit retrofit;
    private IGanHuoService service;
    private static final int ANDROID_CODE=0x001;
    private static final int IOS_CODE=0x002;
    private static final int MEIZHI_CODE=0x003;
    private static final int XIA_CODE=0x004;

    public HttpManager(Handler handler){
        this.handler=handler;
    }

    public void retrofitHttpGet(String type, int count, int page){

        //获取Retrofit对象
        retrofit=new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取service
        service=retrofit.create(IGanHuoService.class);

//        Call<Object> call=service.getList(type,count,page);
//        call.enqueue(new Callback<Object>() {
//        });

        switch(type){
            case "Android":
                Call<AndroidBean> call=service.getAndroidList(count,page);
                call.enqueue(new Callback<AndroidBean>() {

                    //异步请求,这里的Object可以用泛型或是判断type或者运用Java的多态，这里以后需要改进
                    @Override
                    public void onResponse(Call<AndroidBean> call, Response<AndroidBean> response) {

                        Log.i("success-------",response.body().getResults().get(1).getCreatedAt());
                        sendMessage(ANDROID_CODE,response.body());
                    }

                    @Override
                    public void onFailure(Call<AndroidBean> call, Throwable t) {
                        Log.i("failed--------",t.toString());
                    }
                });
                    break;
            case "Ios":
                Call<IOSBean> iosCall=service.getIosList(count,page);
                iosCall.enqueue(new Callback<IOSBean>() {
                    @Override
                    public void onResponse(Call<IOSBean> call, Response<IOSBean> response) {
                        Log.i("success-------ios",response.body().getResults().get(1).getCreatedAt());
                        sendMessage(IOS_CODE,response.body());
                    }

                    @Override
                    public void onFailure(Call<IOSBean> call, Throwable t) {
                        Log.i("failed--------",t.toString());
                    }
                });

                break;
            case "福利":
                Call<MeiZhiBean> meizhiCall=service.getMeizhiList(count,page);
                meizhiCall.enqueue(new Callback<MeiZhiBean>() {
                    @Override
                    public void onResponse(Call<MeiZhiBean> call, Response<MeiZhiBean> response) {
                        Log.i("success-------meizhi",response.body().getResults().get(1).getCreatedAt());
                        sendMessage(MEIZHI_CODE,response.body());

                    }

                    @Override
                    public void onFailure(Call<MeiZhiBean> call, Throwable t) {

                    }
                });
                break;
            case "瞎推荐":
                Call<XiaBean> xiaCall=service.getXiaList(count,page);
                xiaCall.enqueue(new Callback<XiaBean>() {
                    @Override
                    public void onResponse(Call<XiaBean> call, Response<XiaBean> response) {
                        Log.i("success-------xia",response.body().getResults().get(1).getWho());
                        sendMessage(XIA_CODE,response.body());
                    }

                    @Override
                    public void onFailure(Call<XiaBean> call, Throwable t) {

                    }
                });
                break;
            default:break;
        }
    }


    /**
     * 发送消息给请求的Fragment,得到数据
     */
    public void sendMessage(int what,Object obj){

        Message message=Message.obtain();
        message.what=what;
        message.obj=obj;
        handler.sendMessage(message);
    }
}
