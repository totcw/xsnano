package com.betterda.xsnano.view;

import android.content.Context;
import android.service.carrier.CarrierService;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.TreeMap;

/**
 * 添加了滑动监听可以获取Y值的scrollview,首页使用
 * Created by Administrator on 2016/4/26.
 */
public class ScrollYScrollView extends com.betterda.xsnano.view.NestedScrollView {


    private RecyclerView recyclerView;
    private OnScrollListener onScrollListener;
    private int measuredHeight; //scrollview隐藏的高度
    private int downY; //记录滑动事件的y坐标
    private boolean  isTop;//recycleview是否置顶

    public ScrollYScrollView(Context context) {
        this(context, null);

    }

    public ScrollYScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ScrollYScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }




    /**
     * 滚动的回调接口
     *
     * @author xiaanming
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param scrollY 、
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

    public void smoothto() {
        smoothScrollTo(0, measuredHeight);

    }



    public void setTop(boolean top) {
        isTop = top;
    }

    public int getMHeight() {
        return measuredHeight;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       boolean isup  = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                isup = super.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                //防止从intercept直接跳过来
                if (downY == 0) {
                    downY = (int) ev.getY();
                }
                //获取偏移量
                int diffy = (int) (ev.getY() - downY);
                if (diffy < 0) {//上拉
                    if (getScrollY() >= measuredHeight) {
                       //修改父类的上一次事件的位置,防止跳动
                       mLastMotionY = (int) ev.getY();
                        //置顶了实际上是要让recycleview滑动了
                        isup = recyclerView.dispatchTouchEvent(ev);
                    } else {
                        //scrollview滑动
                        isup = super.onTouchEvent(ev);

                    }
                } else if (diffy > 0) { //下拉
                    //如果recycleview没置顶就应该先让recycleview滑动
                    if (!isTop) {
                        //修改父类的上一次事件的位置
                        mLastMotionY = (int) ev.getY();
                        //recycleview滑动
                        isup = recyclerView.dispatchTouchEvent(ev);

                    } else {
                        //scrollview滑动
                        isup = super.onTouchEvent(ev);
                    }

                }
                //记录上一次的位置
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                downY = 0;
               isup = super.onTouchEvent(ev);
                break;

        }

        return isup;
    }


}
