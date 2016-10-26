package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 添加了滑动监听可以获取Y值的scrollview,店铺详情使用的
 * Created by Administrator on 2016/4/26.
 */
public class ScrollYScrollView2 extends ScrollView {
    private OnScrollListener onScrollListener;
    private int measuredHeight;//获取需要隐藏的高度

    public ScrollYScrollView2(Context context) {
        this(context, null);
    }

    public ScrollYScrollView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollYScrollView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }
    }



    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         * 				、
         */
        public void onScroll(int scrollY);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        FrameLayout childAt = (FrameLayout) getChildAt(0);
        LinearLayout childAt1 = (LinearLayout) childAt.getChildAt(0);
        View childAt2 = childAt1.getChildAt(0);
        measuredHeight = childAt2.getMeasuredHeight();




    }

    /**
     * 隐藏
     */
    public void smoothto() {
        smoothScrollTo(0,measuredHeight);
    }


    public int getMHeight() {
        return measuredHeight;
    }





}
