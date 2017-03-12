package com.example.huyu.flipped;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.huyu.adapter.MainVpAdapter;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.fragment.AndroidFragment;
import com.example.huyu.fragment.IosFragment;
import com.example.huyu.fragment.MeizhiFragment;
import com.example.huyu.fragment.VideoFragment;
import com.example.huyu.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTb;
    private TabLayout mTl;
    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private  String[] mTitles={"ANDROID","IOS","休闲视频","妹子图"};
    private int[] mImgs={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private MainVpAdapter mMainVpAdapter;
    private TabLayout.Tab androidTab,iosTab,videoTab,meizhiTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        //new HttpManager().retrofitHttpGet("Android",20,1);

    }

    private void initData() {
        AndroidFragment android=new AndroidFragment();
        IosFragment ios=new IosFragment();
        VideoFragment video=new VideoFragment();
        MeizhiFragment meizhi=new MeizhiFragment();
        mFragmentList.add(android);
        mFragmentList.add(ios);
        mFragmentList.add(video);
        mFragmentList.add(meizhi);


        mMainVpAdapter=new MainVpAdapter(getSupportFragmentManager(),mFragmentList,mTitles);
        mVp.setAdapter(mMainVpAdapter);
        //tabLayout绑定ViewPager
        mTl.setupWithViewPager(mVp);
        //
        androidTab = mTl.getTabAt(0).setIcon(mImgs[0]);
        iosTab= mTl.getTabAt(1).setIcon(mImgs[1]);
        videoTab = mTl.getTabAt(2).setIcon(mImgs[2]);
        meizhiTab= mTl.getTabAt(3).setIcon(mImgs[3]);



    }

    private void initView() {
        mTb= (Toolbar) findViewById(R.id.main_tb);
        setSupportActionBar(mTb);
        mVp= (ViewPager) findViewById(R.id.main_vp);
        mTl= (TabLayout) findViewById(R.id.main_tl);
        mFragmentList=new ArrayList<>();

    }
}
