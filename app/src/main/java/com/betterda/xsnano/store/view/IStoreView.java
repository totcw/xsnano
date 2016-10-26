package com.betterda.xsnano.store.view;

import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ScrollYScrollView2;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface IStoreView extends BaseView{
    ScrollYScrollView2 getUpHideScrollView();

    //店名
    TextView getTextViewName();
    //店铺的类型
    TextView getTextViewtype();
    //营业状态
    TextView getTextViewStatus();
    //营业时间
    TextView getTextViewTime();
    //地址
    TextView getTextViewAddress();

    SimpleDraweeView getSimpleDrawView();

    void setViewpager(double score, double servicescore);

    NormalTopBar getNormalTopbar();

    void getPermissions();
}
