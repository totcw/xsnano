package com.betterda.xsnano.bus.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IBusView extends BaseView {

    RecyclerView getRecycleView();


    ImageView getImageViewAll();

    ImageView getImageViewJiesuan();

    NormalTopBar getNormalBar();

    ImageView getImageViewDelete();

    TextView getTextViewSum();

    TextView gettextViewHeji();

    RelativeLayout getRelative();
}
