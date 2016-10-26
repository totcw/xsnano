package com.betterda.xsnano.store.view;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.StoreRecycleView;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface IShangPingView extends BaseView {
    StoreRecycleView getRecycleView1();
    StoreRecycleView getRecycleView2();

    LinearLayout getLinearLayout();
}
