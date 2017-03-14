package com.example.huyu.download;

/**
 * 处理下载结果，对下载任务的监听
 * Created by huyu on 2017/3/1.
 */

//相当于这个接口是可以放在DownloadTask里面的，因为这是对Download下载任务的监听，并且在download中回调了接口的方法
public interface DownloadListener {

    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();

}
