package com.betterda.xsnano.view;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.betterda.xsnano.interfac.OnUpHideScrollState;

/**
 * 防uc首页,上拉可以隐藏topview等等,但是在xml中要分2部分topView, bottomView
 *
 * @author gengqiquan
 * @version v1.0
 * @date：2016年1月19日13:48:13
 */
public class UpHideScrollView2 extends LinearLayout {
    private int FirstMax;//记录最大的高度
    int Max = 0;//顶部待隐藏的高度,通过在measure方法中测量得到
    private boolean isMeasured = false;//是否第一次测量大小
    private View topView, bottomView;
    public boolean mShowing = true; //topview是否显示
    float oldY, mDLastY;
    Scroller mScroller;//控制滑动的辅助类
    int mlastinterceptx, mlastinterceptY;//上次截断的位置
    OnHeaderScrollLiesten onHeaderScrollLiesten;//回调接口
    private boolean isFirst = true;//默认是到顶部
    private boolean ishas = true;//recycleview是否有数据


    public void setOnUpHideScrollState(OnUpHideScrollState onUpHideScrollState) {
        this.onUpHideScrollState = onUpHideScrollState;
    }

    private OnUpHideScrollState onUpHideScrollState;//获取recyvleview是否到达顶部的回调接口


    public UpHideScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollBarStyle(SCROLL_AXIS_NONE);
        mScroller = new Scroller(context);
    }

    /**
     * 用来控制滑动的方法
     *
     * @param dx
     * @param dy
     */
    private void smoothScrollTo(int dx, int dy) {
        //从开始位置滑到指定位置
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy - getScrollY());
        //要重新绘制view才会显示效果
        invalidate();


        if (dy == 0) {//这里可以判断滑动完成后顶部是隐藏还是现实，在回调中可以控制界面的底部的展现和隐藏，比如改变tab的透明度什么的
            mShowing = true;
            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(true);

        } else if (dy == Max) {//到了topview的高度就将topview隐藏

            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(false);
            mShowing = false;
        }
    }

    /**
     * 用来控制滑动的方法
     *
     * @param dx
     * @param dy
     */
    private void smoothScrollTo(int dx, int dy, int time) {
        //从开始位置滑到指定位置
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy - getScrollY(), time);
        //要重新绘制view才会显示效果
        invalidate();
        if (getScrollY() == 0) {//这里可以判断滑动完成后顶部是隐藏还是现实，在回调中可以控制界面的底部的展现和隐藏，比如改变tab的透明度什么的
            mShowing = true;
            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(true);

        } else if (getScrollY() == Max) {//到了topview的高度就将topview隐藏
            if (onHeaderScrollLiesten != null)
                onHeaderScrollLiesten.isCompute(false);
            mShowing = false;
        }
    }

    /**
     * 重写父类的的这个方法来完成scroller滑动实际的操作
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//是否滑动完成了
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean indelet = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                indelet = false;
                if (!mScroller.isFinished()) {// 保证停止动画防止冲突
                    mScroller.abortAnimation();
                    indelet = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mlastinterceptx;
                float dy = ev.getY() - mlastinterceptY;

                // 横向滑动
                if (Math.abs(dx) >= Math.abs(dy)) {

                    indelet = false;
                } else {// 竖向滑动
                    if (onUpHideScrollState != null) {
                        isFirst = onUpHideScrollState.isFisrt();
                        ishas = onUpHideScrollState.isHas();
                    }


                    if (mShowing) {
                        // 这里我们只需要拦截上拉；这样即使放了listview,上拉的时候就不会响应listview的事件
                        //当头显示的时候上下都要拦截
                        if (isFirst) {

                            indelet = true;
                        } else {
                            if (dy < 0) {  //表示上拉
                                indelet = true;
                            } else {
                                indelet = false;
                            }
                        }

                        if (!ishas) {
                            indelet = true;
                        }

                    } else {

                        if (isFirst) {
                            if (dy > 0) { //表示下拉
                                //当隐藏了我们下拉时要拦截,才能往下滑动
                                indelet = true;
                            } else {
                                indelet = false;
                            }
                        } else {
                            indelet = false;
                        }

                        if (!ishas) {
                            indelet = true;
                        }

                    }
                }

                break;
            case MotionEvent.ACTION_UP:


                indelet = false;
                break;
        }
        mlastinterceptx = (int) ev.getX();
        mlastinterceptY = (int) ev.getY();
        mDLastY = ev.getY();
        return indelet;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int scrollY = getScrollY();//返回的y方向的滑动位置,上拉滑动大于0

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getY();
                mDLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int mSlid = (int) (ev.getY() - mDLastY); //获取y方向移动的值,大于0下拉,小于0表示上拉
                if (onHeaderScrollLiesten != null)
                    onHeaderScrollLiesten.onScoll(-mSlid, scrollY, Max);


                if (scrollY >= Max - 3) { //有点误差

                    //这时是隐藏头布局
                    mShowing = false;
                } else {
                    mShowing = true;
                }

                //控制滑动
                //上拉
                if (mSlid < 0 && scrollY <= Max) { //表示只有上拉,且滑动的距离小于要隐藏的topview

                    // scrollBy(0, -mSlid);//滑动到相对位置
                    if (scrollY - mSlid < Max) { //防止猛滑,出错
                       /* if (onHeaderScrollLiesten != null)
                            onHeaderScrollLiesten.onScoll(-mSlid,scrollY,Max);*/
                        //  scrollTo(0, scrollY - mSlid);
                        if (mScroller != null) {
                            mScroller.startScroll(getScrollX(),getScrollY(),0,-mSlid,0);
                            invalidate();
                        }
                    }
                }
                //下拉
                if (mSlid > 0 && scrollY >= 0) {

                    if (scrollY - mSlid > 0) { //加这个判断是防止向下猛滑,topview会不在顶部
                      /*  if (onHeaderScrollLiesten != null)
                            onHeaderScrollLiesten.onScoll(-mSlid,scrollY,Max);*/
                        //scrollTo(0, scrollY - mSlid);
                        if (mScroller != null) {
                            mScroller.startScroll(getScrollX(),getScrollY(),0,-mSlid,0);
                            invalidate();
                        }
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
         /*       if (Math.abs(scrollY) < Max / 2) { //如果没有超过topview的一半就滑动指定位置,也就是一开始的地方
                    smoothScrollTo(0, 0);
                } else {
                    smoothScrollTo(0, Max);
                }*/
                break;
        }
        mDLastY = ev.getY();
        return true;

    }

    public void setOnHeaderScrollLiesten(OnHeaderScrollLiesten liesten) {
        onHeaderScrollLiesten = liesten;
    }

    /**
     * 回调接口
     */
    public interface OnHeaderScrollLiesten {
        //滑动时候调用的方法
        void onScoll(int y, int scrollY, int h);

        //ture表示topview显示,false表示隐藏
        void isCompute(boolean isShow);//
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev); //将事件传递给子view
        return true; //表示我能消费
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //viewgroup的整体高度要改变 才能滑动 ,这里多加了MAx表示可以最多可以滑动max
        super.onLayout(changed, l, t, r, b + Max);

        //  if (changed) {


        //重新计算viewgroup的高度,否则超过屏幕的显示不了,listview需要重写将高度设置了屏幕的高度否则会无限大就不能滑动了
   /*     int height2 = 0;

        //这里显示界面最大的高度,防止有时时候显示不全
        if (getChildAt(0).getMeasuredHeight() >= Max) {
            height2 += getChildAt(0).getMeasuredHeight();
        } else {
            height2 += Max;
        }

        height2 +=getChildAt(1).getMeasuredHeight();

        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
        lp.height = height2;
        setLayoutParams(lp);
*/


        //  }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        FirstMax = getChildAt(0).getMeasuredHeight();//动态获取需要隐藏的view的高度
        if (FirstMax >= Max) {
            Max = FirstMax;
        }

    }

    int topHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec); //先测量viewgrop的高度,根据父容器推荐的宽,高测量
        // if (!isMeasured) {
        isMeasured = true;
        topView = getChildAt(0);
        bottomView = getChildAt(1);
        //先测量第一个view的高度
        topView.measure(widthMeasureSpec,0);
        FirstMax = topView.getMeasuredHeight();//获取需要隐藏的view的高度
        if (FirstMax >= Max) {
            Max = FirstMax;
        }

        bottomView.measure(widthMeasureSpec, heightMeasureSpec); //在重新测量listview的高度



        // }


    }

    /**
     * 立即显示原本隐藏的topview
     */
    public void ShowHeader() {
        smoothScrollTo(0, 0);
    }

    /**
     * 显示原本隐藏的topview,完成时间2秒
     */
    public void ShowHeaderDelay() {
        smoothScrollTo(0, 0, 2000);
    }

    /**
     * 隐藏topview
     */
    public void HideHeader() {

        smoothScrollTo(0, Max);

    }


}