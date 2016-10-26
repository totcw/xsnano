package com.betterda.xsnano.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 使用viewpager的适配器
 * Created by Administrator on 2016/6/1.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }



        @Override
        public Fragment getItem(int position) {
            if (null != fragmentList) {

                return fragmentList.get(position);
            } else {
                return  null;
            }
        }

        @Override
        public int getCount() {
            if (null != fragmentList) {

                return fragmentList.size();
            } else {
                return  0;
            }
        }



}
