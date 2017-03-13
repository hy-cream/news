package com.example.huyu.flipped;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.huyu.adapter.MainVpAdapter;
import com.example.huyu.bean.AndroidBean;
import com.example.huyu.fragment.AndroidFragment;
import com.example.huyu.fragment.IosFragment;
import com.example.huyu.fragment.MeizhiFragment;
import com.example.huyu.fragment.XiaFragment;
import com.example.huyu.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTb;
    private TabLayout mTl;
    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private  String[] mTitles={"ANDROID","IOS","瞎推荐","妹子图"};
    private int[] mImgs={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private MainVpAdapter mMainVpAdapter;
    private TabLayout.Tab androidTab,iosTab,xiaTab,meizhiTab;
    private static final int ANDROID_CODE=0x001;
    private static final int IOS_CODE=0x002;
    private static final int MEIZHI_CODE=0x003;
    private static final int XIA_CODE=0x004;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ANDROID_CODE:
                    //由于他在viewPager里面动态生成，所以这里取出来时一个空对象
//                    AndroidFragment androidFragment= (AndroidFragment) getSupportFragmentManager().findFragmentById(R.id.android);
//                    if (androidFragment!=null){
//                        androidFragment.initView(msg.obj);
//                    }
                    //基于FragmentPagerAdapter的实现是有效的,数字表示在viewPager中位置
                    AndroidFragment androidf= (AndroidFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.main_vp+":0");
                    if (androidf!=null){
                        androidf.initView(msg.obj);
                    }
                    break;

                case IOS_CODE:
                    IosFragment iosf= (IosFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.main_vp+":1");
                    if (iosf!=null){
                        iosf.initView(msg.obj);
                    }
                    break;

                case MEIZHI_CODE:
                    MeizhiFragment meizhif= (MeizhiFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.main_vp+":3");
                    if (meizhif!=null){
                        meizhif.initView(msg.obj);
                    }
                    else {
                        Log.i("meizhitu------------","fragment is null");
                    }
                    break;

                case XIA_CODE:
                    XiaFragment xiaf= (XiaFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.main_vp+":2");
                    if (xiaf!=null){
                        xiaf.initView(msg.obj);
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initData() {
        AndroidFragment android=new AndroidFragment(handler);
        IosFragment ios=new IosFragment(handler);
        XiaFragment xia=new XiaFragment(handler);
        MeizhiFragment meizhi=new MeizhiFragment(handler);
        mFragmentList.add(android);
        mFragmentList.add(ios);
        mFragmentList.add(xia);
        mFragmentList.add(meizhi);


        mMainVpAdapter=new MainVpAdapter(getSupportFragmentManager(),mFragmentList,mTitles);
        mVp.setAdapter(mMainVpAdapter);
        //tabLayout绑定ViewPager
        mTl.setupWithViewPager(mVp);
        //
        androidTab = mTl.getTabAt(0).setIcon(mImgs[0]);
        iosTab= mTl.getTabAt(1).setIcon(mImgs[1]);
        xiaTab = mTl.getTabAt(2).setIcon(mImgs[2]);
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
