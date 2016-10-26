package com.betterda.xsnano.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.betterda.xsnano.util.UtilMethod;

/**
 * 防uc首页,上拉可以隐藏topview等等,但是在xml中要分2部分topView, bottomView
 *
 * @author gengqiquan
 * @version v1.0
 * @date：2016年1月19日13:48:13
 */
public class UpHideScrollView extends LinearLayout {

    int Max = 0;//顶部待隐藏的高度,通过在measure方法中测量得到
    private boolean isMeasured = false;//是否第一次测量大小
    private View topView, bottomView;
    public boolean mShowing = true; //topview是否显示
    float oldY, mDLastY;
    Scroller mScroller;//控制滑动的辅助类
    int mlastinterceptx, mlastinterceptY;//上次截断的位置
    OnHeaderScrollLiesten onHeaderScrollLiesten;//回调接口

    public UpHideScrollView(Context context, AttributeSet attrs) {
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

                    if (mShowing) {
                        // 这里我们只需要拦截上拉；这样即使放了listview,上拉的时候就不会响应listview的事件
                        //而且只有是显示topview的时候才拦截
                        if (dy < 0) {

                            indelet = true;
                        } else {
                            indelet = false;
                        }
                    } else {
                        if (dy > 0) {
                            //当隐藏了我们下拉时要拦截
                            indelet = true;
                        } else {
                            indelet = false;
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
                    onHeaderScrollLiesten.onScoll(-mSlid,scrollY,Max);


                //控制滑动
                //上拉
                if (mSlid < 0 && scrollY <= Max) { //表示只有上拉,且滑动的距离小于要隐藏的topview

                   // scrollBy(0, -mSlid);//滑动到相对位置
                    if (scrollY - mSlid < Max) { //防止猛滑,出错
                       /* if (onHeaderScrollLiesten != null)
                            onHeaderScrollLiesten.onScoll(-mSlid,scrollY,Max);*/
                        scrollTo(0,scrollY-mSlid);
                    }
                }
                //下拉
                if (mSlid > 0 && scrollY >= 0) {

                    if (scrollY - mSlid > 0) { //加这个判断是防止向下猛滑,topview会不在顶部
                      /*  if (onHeaderScrollLiesten != null)
                            onHeaderScrollLiesten.onScoll(-mSlid,scrollY,Max);*/
                        scrollTo(0, scrollY - mSlid);
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(scrollY) < Max / 2) { //如果没有超过topview的一半就滑动指定位置,也就是一开始的地方
                    smoothScrollTo(0, 0);
                } else {
                    smoothScrollTo(0, Max);
                }
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
        void onScoll(int y,int scrollY, int h);

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
        super.onLayout(changed, l, t, r, b + Max);
    }

    int topHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;
            topView = getChildAt(0);
            bottomView = getChildAt(1);
            Max = topView.getMeasuredHeight() ;//获取需要隐藏的view的高度
        }
        bottomView.measure(widthMeasureSpec, heightMeasureSpec); //重新测量,否则放listview会显示不完全
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