package com.example.huyu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by huyu on 2017/3/10.
 */

public class SVVpAdapter extends PagerAdapter {

    private List<ImageView> mImgList;

    public SVVpAdapter(List<ImageView> mImgList){
        this.mImgList=mImgList;
    }

    @Override
    public int getCount() {
        return mImgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //position++;
        ImageView imageView=mImgList.get(position);
        container.addView(imageView);

        //这里注意哦~返回的是一张图，而不是一个ViewGroup，他每换个位置都会调用一次这个方法
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //固定写法
        container.removeView((View) object);
    }
}
