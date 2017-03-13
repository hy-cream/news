package com.example.huyu.http;

import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.MeiZhiBean;
import com.example.huyu.bean.XiaBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by huyu on 2017/3/10.
 */

public interface IGanHuoService {

    @GET("data/Android/{count}/{page}")
    Call<AndroidBean> getAndroidList(@Path("count") int count,
                                    @Path("page") int page);
    @GET("data/iOS/{count}/{page}")
    Call<IOSBean> getIosList(@Path("count") int count,
                             @Path("page") int page);
    @GET("data/福利/{count}/{page}")
    Call<MeiZhiBean> getMeizhiList(@Path("count") int count,
                                   @Path("page") int page);

    @GET("data/App/{count}/{page}")
    Call<XiaBean> getXiaList(@Path("count") int count,
                             @Path("page") int page);
}
