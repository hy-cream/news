package com.example.huyu.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.huyu.adapter.SVVpAdapter;
import com.example.huyu.flipped.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by huyu on 2017/3/10.
 */

/**
 * 循环轮播+手势滑动
 */
public class SlideView extends FrameLayout {

    private ViewPager mViewPager;
    private List<ImageView> mImgList;
    private int[] imgId={R.mipmap.iv1,R.mipmap.iv2,R.mipmap.iv3, R.mipmap.iv4};
    private List<View> mdotList;

    //当前轮播页
    private int currentItem=1;

    //自动轮播,区分手势滑动和自动轮播的状态
    private boolean isAutoPlay=true;

    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    //接受子线程传过来的当前位置,Handler是一个抽象类
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //api:设置当前选中的页面，如果viewpager已经设立了第一个布局，
            // 当前适配器会有一个平滑的动画在当前项和指定项之间
            if(currentItem==1){
                mViewPager.setCurrentItem(currentItem,false);
            }else{
                //false从尾页换到首页不展示动画效果
                mViewPager.setCurrentItem(currentItem);
            }


        }
    };


    public SlideView(Context context) {
        this(context,null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData();
        initUI(context);
        if (isAutoPlay){
            startPlay();

        }
        Log.i("isautoPlay", String.valueOf(isAutoPlay));

    }

    private void initUI(Context context) {
        //加载一个布局,搞清楚三个参数的含义
        LayoutInflater.from(context).inflate(R.layout.slideview_layout,this,true);

        /**
         *
         */
        for (int i = 0;i <= imgId.length+1; i ++){

            ImageView img=new ImageView(context);
            if(i==0){
                img.setImageResource(imgId[imgId.length-1]);
            }else if(i==imgId.length+1){
                img.setImageResource(imgId[0]);
            }else{
                img.setImageResource(imgId[i-1]);
            }
            img.setScaleType(ImageView.ScaleType.FIT_XY);

            mImgList.add(img);
        }

        View dot1=findViewById(R.id.dot1);
        View dot2=findViewById(R.id.dot2);
        View dot3=findViewById(R.id.dot3);
        View dot4=findViewById(R.id.dot4);

        mdotList.add(dot1);
        mdotList.add(dot2);
        mdotList.add(dot3);
        mdotList.add(dot4);

        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new SVVpAdapter(mImgList));
        mViewPager.setCurrentItem(currentItem);
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    private void initData() {


        mImgList=new ArrayList<ImageView>();
        mdotList=new ArrayList<View>();

    }



    /**
     * 开始轮播图片
     */
    private void startPlay(){
        //在Executors类里面的一个静态方法，返回一个scheduleExecutorService对象
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //scheduleAtFixedRate是一个继承自Executors接口的接口的方法
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),2,4, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图片
     */
    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            if(isAutoPlay){
                //synchronized保证同一时刻只有一个线程执行该代码,为mViewPager对象获取了一个排它锁
                synchronized (mViewPager){
                    currentItem=currentItem%(imgId.length+1)+1;
                    // handler.obtainMessage();和Message msg=new Message();
                    // 他能从全局msg池中取回重用这个引用的实例，而new一个需要重新分配一个实例
                    //利用message传递数据，这里传回主线程才能更新UI
                    handler.obtainMessage().sendToTarget();
                }
            }

        }
    }

    /**
     * viewPager的监听器
     * 当Viewpager中页面状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentItem=position;
        }

        //页面改变，下面的指示器也要跟着改变颜色
        @Override
        public void onPageSelected(int position) {

            for (int i = 0;i <mdotList.size(); i ++){
                if (i==position-1){
                    mdotList.get(i).setBackgroundResource(R.drawable.dot_check);
                }else{
                    mdotList.get(i).setBackgroundResource(R.drawable.dot_uncheck);
                }
            }

        }

        /**
         * 三种viewPager页面状态
         * 1：手势滑动，空闲中
         * 2：界面切换中
         * 0：滑动结束，切换完毕或者是加载完毕
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state){
                case 1:
                    //手势滑动，停止自动轮播
                    isAutoPlay=false;
                    break;
                case 2:
                    //界面正常自动切换,空闲状态
                    isAutoPlay=true;
                    break;
                case 0:
                    //手指离开屏幕，自动完成剩下的动画效果
                    //切换完后，如果当前是最后一张，此时从右向左滑，则切换到第一张
                    if (mViewPager.getCurrentItem()==imgId.length+1&&!isAutoPlay){
                        mViewPager.setCurrentItem(1,false);
                    }
                    //切换完后，如果当前是第一张，此时从左往右滑，则切换到最后一张
                    else if(mViewPager.getCurrentItem()==0&&!isAutoPlay){
                        mViewPager.setCurrentItem(imgId.length,false);
                    }
                    currentItem=mViewPager.getCurrentItem();
                    isAutoPlay=true;
                    break;
                default:
                    break;
            }
        }
    }

//    private void destoryBitmaps() {
//
//        for (int i = 0; i < imgId.length; i++) {
//            ImageView imageView = mImgList.get(i);
//            Drawable drawable = imageView.getDrawable();
//            if (drawable != null) {
//                // 解除drawable对view的引用
//                drawable.setCallback(null);
//            }
//        }
//    }

}

