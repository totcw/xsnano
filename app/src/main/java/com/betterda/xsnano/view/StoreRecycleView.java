package com.betterda.xsnano.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.betterda.xsnano.interfac.OnListLoadNextPageListener;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.UtilMethod;

/**
 * 店铺详情和scrollview嵌套使用的recycleview
 * Created by Administrator on 2016/6/21.
 */
public class StoreRecycleView extends RecyclerView {

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

    private boolean isTop;//是否置顶

    private OnListLoadNextPageListener onListLoadNextPageListener;
    private int downY;



    public StoreRecycleView(Context context) {
        super(context);
    }

    public StoreRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

                //防止是从拦截的地方直接跳过来
                if (downY == 0) {
                    downY = (int) ev.getY();
                }
                //获取偏移量

                int diffy = (int) (ev.getY() - downY);
                LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (diffy > 0) {//下拉

                    if (firstCompletelyVisibleItemPosition == 0) {//当recycleview滑倒顶部时就不拦截,让scrollview滑动
                        getParent().requestDisallowInterceptTouchEvent(false);

                    } else {

                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {

                    System.out.println("istop:"+isTop);
                    //如果是上啦
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
                        getParent().requestDisallowInterceptTouchEvent(false);


                    }
                }
                break;

        }


        return super.dispatchTouchEvent(ev);
    }


    public void setTop(boolean top) {
        isTop = top;
    }



}
