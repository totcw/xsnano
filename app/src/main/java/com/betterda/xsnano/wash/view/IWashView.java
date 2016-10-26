package com.betterda.xsnano.wash.view;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.tianjiaji.view.TwoToolBarView;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IWashView extends BaseView {
    RecyclerView getRecycleView();

    LinearLayout getLinearLayout();

    NormalTopBar getNorTopBar();

    RecyclerView getRecyclyView();

    TwoToolBarView getTwoTool();

    void closePopupWindow();

    TextView getTextViewK();

    TextView getTextViewS();

    TextView getTextViewW();

    RecyclerView getRecyclyView2();
}
