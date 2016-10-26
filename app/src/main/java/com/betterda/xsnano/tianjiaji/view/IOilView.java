package com.betterda.xsnano.tianjiaji.view;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.betterda.xsnano.BaseView;

/**
 * Created by Administrator on 2016/5/16.
 */
public interface IOilView extends BaseView {
    RecyclerView getRecycleView();


    LinearLayout getLinearLayout();

    RecyclerView getRecyclyView();

    void closePopupWindow();

    TwoToolBarView getTwoTool();
}
