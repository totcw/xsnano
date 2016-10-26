package com.betterda.xsnano.acitivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
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
        //不能使用这种方法,会造成内存泄漏
        //   ((MyApplication)getApplication()).addActivity(this);
        x.view().inject(this);
        initView();
        initListener();
        init();

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
     *
     */
    public void backActivity() {
        finish();
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
     //   popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        if (null == showView) {
            popupWindow.setFocusable(true);
        } else {

            popupWindow.setFocusable(false);
        }
        //设置可以触摸
        popupWindow.setTouchable(true);

        if (null == showView) {
            UtilMethod.backgroundAlpha(0.7f, this);

        }
        popupWindow.update();

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (null == showView) {
                    popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                  //  popupWindow.setAnimationStyle(R.style.popupwindow_anim);

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
        ((MyApplication)getApplication()).removeAcitivty(this);

    }
}
