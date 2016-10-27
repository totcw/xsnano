package com.betterda.xsnano.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;


/**
 * 分类,赛选的自定义view
 *
 * @author Administrator
 */
public class MainFourView extends RelativeLayout {
    private MainFourItemView mfiv_fist, mfiv_second, mfiv_three;
    private int downX;
    private int downY;
    private boolean isTop;//Scrollview是否置顶

    public MainFourView(Context context) {
        this(context, null);
    }

    public MainFourView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将布局文件和 代码进行绑定
        View.inflate(context, R.layout.main_four, this);
        mfiv_fist = (MainFourItemView) findViewById(R.id.mfiv_fist);
        mfiv_second = (MainFourItemView) findViewById(R.id.mfiv_second);
        mfiv_three = (MainFourItemView) findViewById(R.id.mfiv_three);
        //设置图片
        mfiv_fist.setIvBackground(R.mipmap.filter_tab_under_icon, R.mipmap.filter_tab_under_icon_blue);
        mfiv_second.setIvBackground(R.mipmap.filter_tab_under_icon, R.mipmap.filter_tab_under_icon_blue);
        mfiv_three.setIvBackground(R.mipmap.filter_tab_under_icon, R.mipmap.filter_tab_under_icon_blue);
        //设置文字颜色
        mfiv_fist.setLineBackground(getResources().getColor(R.color.shouye_renmen_tv), getResources().getColor(R.color.shouye_fenlei_red));
        mfiv_second.setLineBackground(getResources().getColor(R.color.shouye_renmen_tv), getResources().getColor(R.color.shouye_fenlei_red));
        mfiv_three.setLineBackground(getResources().getColor(R.color.shouye_renmen_tv), getResources().getColor(R.color.shouye_fenlei_red));

    }

    //统一设置标题
    public void setTitle(String first, String second, String three) {
        mfiv_fist.setTitle(first);
        mfiv_second.setTitle(second);
        mfiv_three.setTitle(three);
    }

    //设置第一个的标题
    public void setFirstTitle(String title) {
        mfiv_fist.setTitle(title);
    }

    //设置第二个的标题
    public void setSecondTitle(String title) {
        mfiv_second.setTitle(title);
    }

    //设置第三个的标题
    public void setThreeTitle(String title) {
        mfiv_three.setTitle(title);
    }

    //设置第一个的点击事件
    public void setOnFirstClick(OnClickListener listener) {
        mfiv_fist.setOnClickListener(listener);
    }

    //设置第二个的点击事件
    public void setOnSecondClick(OnClickListener listener) {
        mfiv_second.setOnClickListener(listener);
    }

    public void setOnThreeClick(OnClickListener listener) {
        mfiv_three.setOnClickListener(listener);
    }

    //设置第一个的选中事件
    public void setFirstSelect(boolean select) {
        mfiv_fist.setTabSelected(select);
        mfiv_second.setTabSelected(false);
        mfiv_three.setTabSelected(false);
    }

    public void setSecondSelect(boolean select) {
        mfiv_fist.setTabSelected(false);
        mfiv_second.setTabSelected(select);
        mfiv_three.setTabSelected(false);
    }

    public void setThreeSelect(boolean select) {
        mfiv_fist.setTabSelected(false);
        mfiv_second.setTabSelected(false);
        mfiv_three.setTabSelected(select);
    }

    //第一个是否选中
    public boolean isFirstSelected() {
        return mfiv_fist.isSelected();
    }

    public boolean isSecondSelected() {
        return mfiv_second.isSelected();
    }

    public boolean isThreeSelected() {
        return mfiv_three.isSelected();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isTop) {//scrollview置顶的时候,不让拦截,也就不可以滑动
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        return super.dispatchTouchEvent(ev);
    }

    public void setTop(boolean top) {
        isTop = top;
    }
}
