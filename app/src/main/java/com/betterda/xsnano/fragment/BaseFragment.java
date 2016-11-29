package com.betterda.xsnano.fragment;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.betterda.xsnano.R;
import com.betterda.xsnano.util.UtilMethod;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lyf
 */
public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    private PopupWindow popupWindow;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity; //在这里获取到acitiviy,防止内存不够,activity被销毁,调用getactivity方法时返回null
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = initView(inflater);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        initListenr();
        initData();
    }

    /**
     * 设置监听
     */
    public void initListenr() {

    }


    /**
     * 子类必须实现此方法, 返回一个View对象, 作为当前Fragment的布局来展示.
     *
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 如果子类需要初始化自己的数据, 把此方法给覆盖.
     */
    public void initData() {

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
        if (getActivity().isFinishing()) {
            return;
        }


        if (null == showView) {
            popupWindow = new PopupWindow(view, -1, -2);
        } else {
            popupWindow = new PopupWindow(view, -1, -1);
        }
        if (null == showView) {
            popupWindow.setOutsideTouchable(true);
        }
        // 设置点到外面可以取消,下面这2句要一起
       // popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //设置为true 会拦截事件,pop外部的控件无法获取到事件
        if (null == showView) {
            popupWindow.setFocusable(true);
        } else {

            popupWindow.setFocusable(false);
        }

        if (null == showView) {
            UtilMethod.backgroundAlpha(0.7f, getmActivity());

        }
        //设置可以触摸
        popupWindow.setTouchable(true);
        popupWindow.update();

        if (popupWindow != null) {
            if (!popupWindow.isShowing()) {
                if (null == showView) {
                    //设置动画
                    popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
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
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public Activity getmActivity() {
        return mActivity;
    }


    /**
     * 因为popupwindow的settouchModal方法被hide,无法只掉调用,所以用发射来设置,设置为false
     * 就可以将事件传递给下面的activity
     * @param popupWindow
     * @param touchModal
     */
/*    public  void setPopupWindowTouchModal(PopupWindow popupWindow,
                                                boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {

            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }*/


}
