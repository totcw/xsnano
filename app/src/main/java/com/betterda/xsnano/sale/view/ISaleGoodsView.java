package com.betterda.xsnano.sale.view;

import android.support.v7.widget.RecyclerView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * Created by Administrator on 2016/5/23.
 */
public interface ISaleGoodsView extends BaseView {
    NormalTopBar getTopBar();

    RecyclerView getRecycleView();
}
