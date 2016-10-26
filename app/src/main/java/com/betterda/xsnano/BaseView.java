package com.betterda.xsnano;

import android.app.Activity;
import android.content.Context;

import com.betterda.xsnano.view.LoadingPager;

/**
 * 基类view
 * Created by Administrator on 2016/5/12.
 */
public interface BaseView {
    /**
     * 获取加载界面
     */
    LoadingPager getLoadPager();

    Activity getmActivity();

    Context getContext();


}
