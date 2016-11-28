package com.betterda.xsnano.shouye.view;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.shouye.adapter.LunBoTuAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.MainFourView;
import com.betterda.xsnano.view.MyRecycleView;
import com.betterda.xsnano.view.RefreshListView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface IShouyeView extends BaseView{


    /**
     * 得到轮播图圆点的布局
     * @return
     */
    public LinearLayout getLpoint();

    /**
     * 得到首页的viewpager
     * @return
     */
    public ViewPager getViewPager();



    /**
     * 得到首页的分类
     * @return
     */
    public MainFourView getViewThree();

    /**
     * 得到首页的显示城市的textview
     * @return
     */
    public TextView getTextViewCity();


    public RecyclerView getRecyclyView();
    public ListView getMainListview();
    public ListView getMoreListview();


    /**
     * 获取首页的布局
     * @return
     */
    public FrameLayout getFrameShouye();

    void closePopupWindow();




    MyRecycleView getRecyclyViewShouye();




    void setLunBoAdapter();



    ImageView getImageviewMianFirst();

    MainFourView getMfvShouye();

    RecyclerView getRecyclyView2();

    View getScaleView();
}

