package com.betterda.xsnano.store.view;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.StoreRecycleView;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface IShangpCommentView extends BaseView {
    StoreRecycleView getRecycleView();

    TextView getTextViewSum();
    TextView getTextViewService();
    TextView getTextViewyonghu();
    RatingBar getRatingBaryonghu();
    RatingBar getRatingBarService();

    LinearLayout getLinearLayout();
}
