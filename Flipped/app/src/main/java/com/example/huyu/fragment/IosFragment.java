package com.example.huyu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huyu.adapter.AndroidRvAdapter;
import com.example.huyu.adapter.IOSRvAdapter;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.flipped.R;
import com.example.huyu.http.HttpManager;

/**
 * Created by huyu on 2017/3/10.
 */

public class IosFragment extends Fragment {

    private RecyclerView mRv;
    private IOSRvAdapter mAdapter;
    private IOSBean mBean;
    private HttpManager httpManager;
    private int page=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x002:
                    mBean=(IOSBean) msg.obj;
                    initView();
                    break;
            }
        }


    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ios_layout,container,false);
        mRv= (RecyclerView) view.findViewById(R.id.ios_rv);
        //网络请求数据
        httpManager=new HttpManager(handler);
        httpManager.retrofitHttpGet("Ios",20,page);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void initView() {
        mAdapter=new IOSRvAdapter(mBean,getActivity());
        mRv.setAdapter(mAdapter);
    }
}
