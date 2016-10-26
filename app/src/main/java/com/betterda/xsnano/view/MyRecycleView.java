package com.betterda.xsnano.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.betterda.xsnano.interfac.OnListLoadNextPageListener;
import com.betterda.xsnano.interfac.OnRecycleviewFist;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.UtilMethod;

import java.io.File;

/**
 * 首页和scrollview嵌套使用的recycleview
 * Created by Administrator on 2016/6/21.
 */
public class MyRecycleView extends RecyclerView {

    /**
     * 当前RecyclerView类型
     */
    protected EndlessRecyclerOnScrollListener.LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    private boolean isTop;//scrollview是否置顶

    private OnListLoadNextPageListener onListLoadNextPageListener;
    private int downY; //记录滑动事件的y坐标
    private ScrollYScrollView scrollYScrollView;

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取状态栏高度
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        //重新计算listview的高度,让它刚好是屏幕的高度,这样当 数据小于一个屏幕时,scrollview也可以滑动
        int expandSpec = MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().heightPixels - UtilMethod.dip2px(getContext(), Constants.IDV_HEIGHT) - result - UtilMethod.dip2px(getContext(), Constants.SHOUYE_TITLE)
                        - UtilMethod.dip2px(getContext(), Constants.SHOUYE_FN),

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    public void setonListLoadNextPageListener(OnListLoadNextPageListener onListLoadNextPageListener) {
        this.onListLoadNextPageListener = onListLoadNextPageListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                //在down的时候拦截,否则后面都拦截不到
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (scrollYScrollView != null) {
                    if (firstCompletelyVisibleItemPosition == 0) {//recycleview置顶,通知scrollview
                        scrollYScrollView.setTop(true);
                    } else {
                        scrollYScrollView.setTop(false);
                    }
                }
                int diffy = (int) (ev.getY() - downY);
                if (diffy > 0) {//下拉
                    if (firstCompletelyVisibleItemPosition == 0) {//当recycleview滑倒顶部时就不拦截,让scrollview滑动
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        //recycleview没滑到顶部,就先让recycleview滑动
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else if (diffy < 0) {//上拉

                    if (isTop) {//当scrollview置顶时就拦截,让recycleview滑动
                        getParent().requestDisallowInterceptTouchEvent(true);
                        //滑到底部的时候加载更多
                        layoutManagerType = EndlessRecyclerOnScrollListener.LayoutManagerType.LinearLayout;
                        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                            if (onListLoadNextPageListener != null) {
                                onListLoadNextPageListener.onLoadNextPage(this);
                            }
                        }
                    } else {
                        //这里不仅要拦截,还要返回false,直接让scrollview滑动,不让recycleview滑动,
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;

                    }
                }
                //记录上一次的位置
                downY = (int) ev.getY();
                break;

        }


        return super.dispatchTouchEvent(ev);
    }


    public void setTop(boolean top) {
        isTop = top;
    }

    public void setScrollYScrollView(ScrollYScrollView scrollYScrollView) {
        this.scrollYScrollView = scrollYScrollView;
    }



}
