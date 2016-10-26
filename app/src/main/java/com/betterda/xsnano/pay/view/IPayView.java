package com.betterda.xsnano.pay.view;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface IPayView extends BaseView {
    TextView getTextViewMoney();
    TextView getTextViewMoney2();
    TextView getTextViewMoney3();
    TextView getTextViewMoney4();

    RelativeLayout getRelativeWxpay();
    RelativeLayout getRelativeWypay();
    RelativeLayout getRelativeZfbpay();

    RelativeLayout getRelativeName();
}
