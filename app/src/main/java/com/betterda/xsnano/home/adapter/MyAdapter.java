package com.betterda.xsnano.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.betterda.xsnano.home.presenter.IHomePresenter;

/**
 *
 * Created by Administrator on 2016/4/26.
 */
public class MyAdapter extends FragmentStatePagerAdapter {
    private IHomePresenter iHomePresenter;

    public MyAdapter(FragmentManager fm,IHomePresenter iHomePresenter) {
        super(fm);
        this.iHomePresenter = iHomePresenter;
    }

    @Override
    public Fragment getItem(int position) {
        if (null != iHomePresenter.getFragmentList()) {
            return iHomePresenter.getFragmentList().get(position);
        } else {
        return null;
        }
    }

    @Override
    public int getCount() {
        if (null != iHomePresenter.getFragmentList()) {
            return iHomePresenter.getFragmentList().size();
        } else {
            return 0;
        }
    }


}
