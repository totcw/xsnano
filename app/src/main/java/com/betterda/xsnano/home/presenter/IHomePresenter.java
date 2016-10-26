package com.betterda.xsnano.home.presenter;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface IHomePresenter {

    public void crateFragment();
    public List<Fragment> getFragmentList();

    boolean onBackPressed();
}
