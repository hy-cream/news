package com.example.tab_test;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.tab_test.DataAdapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //初始化控件和获取请求队列

    private ViewPager mviewPager;
    private List<Fragment> fragmentList;
    private MyFragmentAdapter myFragmentAdapter;
    private TabLayout mtabLayout;
    private List<String> mtitlelist;
    private RequestQueue mqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mqueue = Volley.newRequestQueue(this);

        initView();

    }

    private void initView() {

        mviewPager = (ViewPager) findViewById(R.id.viewPager);
        mtabLayout= (TabLayout) findViewById(R.id.tabLayout);

        mtitlelist=new ArrayList<String>();
        mtitlelist.add("android");
        mtitlelist.add("ios");
        mtitlelist.add("ganhuo");
        mtitlelist.add("meizi");

        //设置TabLayout的模式
        mtabLayout.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(0)));
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(1)));
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(2)));
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(3)));

        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mqueue,mtitlelist);
        mviewPager.setAdapter(myFragmentAdapter);

        mtabLayout.setupWithViewPager(mviewPager);
    }

}
