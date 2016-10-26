package com.betterda.xsnano.order.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.util.UtilMethod;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface IComfirmOrderView extends BaseView {
    //RecyclerView getRecycleView();

    RelativeLayout getRelaviteOrder();

   // TextView getTextViewHeji();

    TextView getTextViewPay();

   /* ScrollView getScrollView();

    TextView getTextViewAmount();

    TextView getTextViewPeisong();

    TextView getTextViewFapiao();

    TextView getTextviewGold();

    EditText getEditView();*/

    TextView getTextViewShouHuoren();
    TextView getTextViewShouHuoNumber();
    TextView getTextViewShouHuoAddress();

    RecyclerView getRecycleView2();

    void setUpPopup(View view, View view2, int i, int p);

    void closePopup();

    ImageView getIvPay();
}
