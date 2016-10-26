package com.betterda.xsnano.tianjiaji.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * Created by Administrator on 2016/5/16.
 */
public interface ITianJiaJiView extends BaseView {
    RecyclerView getRecycleView();


    LinearLayout getLinearLayout();

    RecyclerView getRecyclyView();

    void closePopupWindow();

    TwoToolBarView getTwoTool();

    NormalTopBar getTopBar();
}
