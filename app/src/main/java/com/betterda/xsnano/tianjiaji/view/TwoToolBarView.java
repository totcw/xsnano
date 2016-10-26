package com.betterda.xsnano.tianjiaji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.view.MainFourItemView;


/**
 * 排序,品牌的自定义view
 *
 * @author Administrator
 */
public class TwoToolBarView extends RelativeLayout {
    private MainFourItemView mfiv_fist,mfiv_second;
    public TwoToolBarView(Context context) {
        this(context, null);
    }

    public TwoToolBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将布局文件和 代码进行绑定
        View.inflate(context, R.layout.twotool, this);
        mfiv_fist = (MainFourItemView) findViewById(R.id.mfiv_twotool_fist);
        mfiv_second = (MainFourItemView) findViewById(R.id.mfiv_twotool_second);
        //设置图片
        mfiv_fist.setIvBackground(R.mipmap.filter_tab_under_icon,R.mipmap.filter_tab_under_icon_blue);
        mfiv_second.setIvBackground(R.mipmap.filter_tab_under_icon,R.mipmap.filter_tab_under_icon_blue);
         //设置文字颜色
        mfiv_fist.setLineBackground(getResources().getColor(R.color.shouye_renmen_tv), getResources().getColor(R.color.shouye_fenlei_red));
        mfiv_second.setLineBackground(getResources().getColor(R.color.shouye_renmen_tv), getResources().getColor(R.color.shouye_fenlei_red));

    }
    //统一设置标题
    public  void setTitle(String first,String second){
        mfiv_fist.setTitle(first);
        mfiv_second.setTitle(second);
    }
    //设置第一个的标题
    public void setFirstTitle(String title) {
        mfiv_fist.setTitle(title);
    }
    //设置第二个的标题
    public void setSecondTitle(String title) {
        mfiv_second.setTitle(title);
    }

    //设置第一个的点击事件
    public void setOnFirstClick(OnClickListener listener){
        mfiv_fist.setOnClickListener(listener);
    }
    //设置第二个的点击事件
    public void setOnSecondClick(OnClickListener listener){
        mfiv_second.setOnClickListener(listener);
    }
    //设置第一个的选中事件
    public void setFirstSelect(boolean select){
        mfiv_fist.setTabSelected(select);
        mfiv_second.setTabSelected(false);
    }
    public void setSecondSelect(boolean select){
        mfiv_fist.setTabSelected(false);
        mfiv_second.setTabSelected(select);
    }
    public void setThreeSelect(boolean select){
        mfiv_fist.setTabSelected(false);
        mfiv_second.setTabSelected(false);
    }

    //第一个是否选中
    public boolean isFirstSelected(){
        return mfiv_fist.isSelected();
    }
    public boolean isSecondSelected(){
        return mfiv_second.isSelected();
    }


}
