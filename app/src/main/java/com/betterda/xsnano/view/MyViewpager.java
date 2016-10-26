package com.betterda.xsnano.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/9/28.
 */

public class MyViewpager extends ViewPager {
    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewpager(Context context) {
        this(context, null);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


}
