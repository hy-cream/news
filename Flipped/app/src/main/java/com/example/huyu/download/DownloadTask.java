package com.example.huyu.download;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.BoringLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *@fuction 下载文件任务的过程
 * Created by huyu on 2017/3/1.
 */

//异步断点下载文件任务类
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    //下载失败，成功，暂停或是取消返回值
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled = false;
    private boolean isPaused = false;

    private int lastProgress=0;//上一次的进度，用来判断是否在进行下载

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            //创建一个File和目录将下载的文件放进去，获得Url
            long downloadedLength = 0; // 记录已下载的文件长度
            String downloadUrl = params[0];//获取Url
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);

            //如果这个文件存在
            if (file.exists()) {
                //获取文件中的字节数，来判断是否要断点下载
                downloadedLength = file.length();
            }
            //通过网络get请求得到返回的response中提出contentLength
            long contentLength = getContentLength(downloadUrl);
            //如果文件总字节数为0，这说明文件有问题，下载失败
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedLength) {

                // 已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }

            /**
             * 文件开始下载
             */
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");//创建了和文件相同大小的的文件
                savedFile.seek(downloadedLength); // 跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    //得到输入流后先判断用户是否取消，是否暂停,否则通过输入流将文件内容写入保存的文件中
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if(isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        // 计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        //通知进度,随后会执行这个onProgressUpdate方法
                        publishProgress(progress);
                    }
                }
                response.body().close();//关闭网络资源，随时用完随时关，只有流，文件等要在finally中关闭
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //在finally里面关闭流和读写的文件
            try {
                if (is != null) {
                    is.close();
                }else if(savedFile != null){
                    savedFile.close();
                }else if(isCanceled && file != null) {//如果取消了下载，并且下载的文件不为空，则将它删除
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    //可以用来更新进度显示栏
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            //接口的回调1
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    //返回处理结果
    @Override
    protected void onPostExecute(Integer status) {
        //处理结果,接口的回调
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
            default:
                break;
        }
    }

    //暂停下载
    public void pauseDownload() {
        isPaused = true;
    }

    //取消下载
    public void cancelDownload() {
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();//需要下载文件的总字节
            response.close();
            return contentLength;
        }
        return 0;
    }

}
