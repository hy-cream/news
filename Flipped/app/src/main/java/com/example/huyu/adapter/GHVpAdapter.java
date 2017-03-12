package com.example.huyu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by huyu on 2017/3/10.
 */

public class GHVpAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public GHVpAdapter(FragmentManager fm,List<Fragment> fragmentList,String[] titles) {
        super(fm);
        this.mFragmentList=fragmentList;
        this.mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //用了TabLayout绑定要重写这个方法
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
