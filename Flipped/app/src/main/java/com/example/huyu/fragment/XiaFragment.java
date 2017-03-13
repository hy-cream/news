package com.example.huyu.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huyu.adapter.IOSRvAdapter;
import com.example.huyu.adapter.XiaRvAdapter;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.XiaBean;
import com.example.huyu.flipped.R;
import com.example.huyu.http.HttpManager;

/**
 * Created by huyu on 2017/3/9.
 */

public class XiaFragment extends Fragment {

    private RecyclerView mRv;
    private XiaRvAdapter mAdapter;
    private XiaBean mBean;
    private HttpManager httpManager;
    private int page=1;
    private SwipeRefreshLayout mSwipe;
    private Handler handler;

    public XiaFragment(Handler handler){
        this.handler=handler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.xia_layout,container,false);
        mRv= (RecyclerView) view.findViewById(R.id.xia_rv);
        //网络请求数据
        httpManager=new HttpManager(handler);
        getHttp();

        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //初始化刷新空间
        mSwipe= (SwipeRefreshLayout) view.findViewById(R.id.xia_swipe);
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
        mBean= (XiaBean) obj;
        if (mBean!=null){
            mAdapter=new XiaRvAdapter(mBean,getActivity());
            mRv.setAdapter(mAdapter);

            if (mSwipe.isRefreshing()){
                mSwipe.setRefreshing(false);
            }
        }


    }

    public void getHttp() {
        httpManager.retrofitHttpGet("瞎推荐",20,page);
    }
}
