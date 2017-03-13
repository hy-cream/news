package com.example.huyu.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huyu.adapter.IOSRvAdapter;
import com.example.huyu.adapter.MeiRvAdapter;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.MeiZhiBean;
import com.example.huyu.flipped.R;
import com.example.huyu.http.HttpManager;

/**
 * Created by huyu on 2017/3/9.
 */

public class MeizhiFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MeiZhiBean meiZhiBean;
    private MeiRvAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private HttpManager httpManager;
    private int page=1;
    private Handler handler;

    public MeizhiFragment(Handler handler){
        this.handler=handler;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.meizhi_layout,container,false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.meizhi_rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //网络请求数据
        httpManager=new HttpManager(handler);
        getHttp();

        //初始化刷新空间
        mSwipe= (SwipeRefreshLayout) view.findViewById(R.id.meizhi_swipe);
        mSwipe.setColorSchemeColors(Color.BLUE);
        //刷新事件的监听
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                getHttp();
            }
        });
        return view;
    }

    public void initView(Object obj) {
        meiZhiBean = (MeiZhiBean) obj;
        if (meiZhiBean != null) {
            Log.i("meizhi-------","set adapter");
            mAdapter = new MeiRvAdapter(getActivity(), meiZhiBean);
            mRecyclerView.setAdapter(mAdapter);
            Log.i("meizhi-------","set adapter finsh");

            if (mSwipe.isRefreshing()) {
                mSwipe.setRefreshing(false);
            }
        }else {
            Log.i("meizhitu-----------","nullllll");
        }
    }
    private void getHttp(){
        httpManager.retrofitHttpGet("福利",20,page);
    }
}
