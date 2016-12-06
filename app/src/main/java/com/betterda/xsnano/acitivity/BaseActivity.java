package com.betterda.xsnano.acitivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.betterda.xsnano.R;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/21.
 */
public class BaseActivity extends FragmentActivity {
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置5.0着色状态栏
        setStatusBar5(R.color.title_red2);
        x.view().inject(this);
        initView();
        //设置4.4着色状态栏
        setStatusBar4(R.color.title_red2);
        initListener();
        init();

    }

    private void setStatusBar5(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            // 有些情况下需要先清除透明flag
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色
            getWindow().setStatusBarColor(getResources().getColor(color));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            //4.4 这样设置只是让状态栏透明,此时布局会衍生到状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        }
    }

    private void setStatusBar4(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
            //让布局不衍生到状态栏
            ViewGroup mContentView = (ViewGroup) findViewById(android.R.id.content);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {

                ViewCompat.setFitsSystemWindows(mChildView, true);
            }
            //添加布局
            addStatusBarView(color,mContentView);
        }
    }

    /**
     * 添加布局到状态栏
     * @param color
     * @param viewGroup
     */
    private void addStatusBarView(int color,ViewGroup viewGroup) {
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UtilMethod.statusHeight(this));
        view.setLayoutParams(params);
        viewGroup.addView(view);


    }


    /**
     * 处理业务逻辑
     */
    public void init() {

    }

    /**
     * 设置监听
     */
    public void initListener() {

    }

    /**
     * 初始化view
     */
    public void initView() {

    }

    /**
     * 关闭activity的方法
     */
    public void backActivity() {
        finish();
        UtilMethod.setOverdepengingOut(this);
    }



    /**
     * 初始化并显示PopupWindow
     *
     * @param view     要显示的界面
     * @param showView 显示在哪个控件下面
     * @param height   显示的高度
     */
    public void setUpPopupWindow(View view, View showView, int first, int height) {

        // 如果activity不在运行 就返回
        if (this.isFinishing()) {
            return;
        }

        if (null == showView) {
            popupWindow = new PopupWindow(view, -1, -2);
        } else {

            popupWindow = new PopupWindow(view, -1, -1);

        }

        // 设置点到外面可以取消,下面这2句要一起
        if (null == showView) {
            popupWindow.setOutsideTouchable(true);
        } else {
            if (first == 100) {
                popupWindow.setOutsideTouchable(true);
            }
        }

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        if (null == showView) {
            popupWindow.setFocusable(true);
        } else {
            if (first == 100) {
                popupWindow.setFocusable(true);
            } else {
                popupWindow.setFocusable(false);

            }
        }
        //设置可以触摸
        popupWindow.setTouchable(true);

        if (null == showView) {
            UtilMethod.backgroundAlpha(0.7f, this);

        } else {
            /*if (first == 100) {
                UtilMethod.backgroundAlpha(0.7f, this);
            }*/
        }
        popupWindow.update();

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (null == showView) {
                    if (first == 100) {
                        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                    } else {
                        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

                        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    }


                } else {

                    popupWindow.setAnimationStyle(R.style.popupwindow_anim);
                    popupWindow.showAsDropDown(showView);
                }

            }
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

                dismiss();
                popupWindow = null;
            }
        });


    }

    /**
     * popupwindow消失回调方法
     */
    public void dismiss() {

    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }


    /**
     * 关闭popupwindow
     */
    public void closePopupWindow() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            popupWindow = null;
        }
    }

    public Activity getmActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        ((MyApplication) getApplication()).removeAcitivty(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UtilMethod.setOverdepengingOut(this);
    }
}
