package com.betterda.xsnano.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.betterda.xsnano.util.Constants;

/**
 * 自定义的framelayout 防止父控件拦截事件
 * Created by Administrator on 2016/7/13.
 */
public class MyView extends FrameLayout {
    private int downX;
    private int downY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int diffX = downX - moveX;
                int diffY = downY - moveY;
                
                if (Math.abs(diffY) > Math.abs(diffX)) {
                    //上下滑动 可以拦截
                    getParent().requestDisallowInterceptTouchEvent(false);

                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);

                break;


            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
