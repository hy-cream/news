package com.example.tab_test.Fragment;



/**
 * Created by 胡钰 on 2016/7/26.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.example.tab_test.DataAdapter.ImagesAdapter;
import com.example.tab_test.DividerItemDecoration;
import com.example.tab_test.JsonHttp;
import com.example.tab_test.R;
import com.example.tab_test.DataAdapter.RecAdapter;
import com.example.tab_test.WebActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡钰 on 2016/7/19.
 */
public class MyFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private List<String> mdatas;
    private RecAdapter mrecAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mswipeRefreshLayout;
    private static final int REFRESH_COMPLETE=0x110;
    private int change;

    private RequestQueue mqueue;
    private int num=1;


    private String url;

    private JsonHttp mjson;
    private ImagesAdapter mimagesAdapter;

    private Map<String, String> map;
    private List<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();





    public MyFragment1(RequestQueue mqueue,String url,int change){

        this.mqueue=mqueue;
        this.url=url;
        this.change=change;
    }



    private Handler  mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {
                case REFRESH_COMPLETE:
                    num++;

                    Log.i("suaxin","start");
//                    JsonHttp jsonHttp=new JsonHttp(url,mqueue,mHandler).jsonjiexi(num);
                    mjson=new JsonHttp(url,mqueue,mHandler);
                    mjson.jsonjiexi(num);
                    initView();



                    mswipeRefreshLayout.setRefreshing(false);

                    Log.i("suaxin","stop");
                    break;
                case 0x1234:
                    list = (List<HashMap<String, String>>) msg.obj;
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentlist,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mswipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.srl);

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mjson=new JsonHttp(url,mqueue,mHandler);
        mjson.jsonjiexi(num);

//       SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                Message msg=new Message();
//                msg.what=0x110;
//                mHandler.sendMessage(msg);
//
//                mswipeRefreshLayout.setRefreshing(false);
//
//            }
//        };

        mswipeRefreshLayout.setOnRefreshListener(this);

        initView();

//        SwipeRefreshLayout.post(new Runable(){
//            @Override
//            public void run() {
//                SwipeRefreshLayout.setRefreshing(true);
//            }
//        });
//        onRefresh();




    }

    private void initView() {

        if (change==1){

            mLinearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(mLinearLayoutManager);

            mrecAdapter=new RecAdapter(getContext(),list);
            recyclerView.setAdapter(mrecAdapter);

            // 设置分割线
            recyclerView.addItemDecoration(new DividerItemDecoration(
                    getActivity(), DividerItemDecoration.VERTICAL_LIST));

            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            recyclerView.setHasFixedSize(true);


            //监听事件
            mrecAdapter.setOnItemClickListener(new RecAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position,String url) {
//                Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),WebActivity.class);
                    String url_web=list.get(position).get("url");
                    intent.putExtra("url_web",url_web);
                    startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            });

        }else{

            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mimagesAdapter=new ImagesAdapter(getContext(),list,mqueue);
            recyclerView.setAdapter(mimagesAdapter);

        }


    }


    @Override
    public void onRefresh() {
        Message msg=new Message();
        msg.what=0x110;
        mHandler.sendMessage(msg);
    }
}
