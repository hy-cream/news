package com.example.tab_test.DataAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.volley.RequestQueue;
import com.example.tab_test.Fragment.MyFragment1;

import java.util.List;

/**
 * Created by 胡钰 on 2016/7/26.
 */
//viewPager的适配器
public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> mtitlelist;
    private RequestQueue mqueue;
    private int change;

    public MyFragmentAdapter( FragmentManager supportFragmentManager,RequestQueue mqueue, List<String> mtitlelist) {
        super(supportFragmentManager);
        this.mtitlelist=mtitlelist;
        this.mqueue=mqueue;
    }

    private String url_android="http://gank.io/api/data/Android/10/";
    private String url_ios="http://gank.io/api/data/iOS/20/";
    private String url_all="http://gank.io/api/data/all/20/";
    private String url_meizi="http://gank.io/api/data/福利/10/";

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                MyFragment1 fragment1=new MyFragment1(mqueue,url_android,1);
                return fragment1;

            case 1:
                MyFragment1 fragment2=new MyFragment1(mqueue,url_ios,1);
                return fragment2;

            case 2:
                MyFragment1 fragment3=new MyFragment1(mqueue,url_all,1);
                return fragment3;

            case 3:
                MyFragment1 fragment4=new MyFragment1(mqueue,url_meizi,0);
                return fragment4;

        }
        return null;
    }

    @Override
    public int getCount() {
        return mtitlelist.size();
    }

    public CharSequence getPageTitle(int position) {
        return mtitlelist.get(position % mtitlelist.size());
    }

}