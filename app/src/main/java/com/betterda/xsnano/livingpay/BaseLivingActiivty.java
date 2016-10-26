package com.betterda.xsnano.livingpay;

import android.view.View;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 生活缴费
 * Created by Administrator on 2016/9/8.
 */
public class BaseLivingActiivty extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topbar;
    private LinearLayout linearBaselivingShui;//水费
    private LinearLayout linearBaselivingDian;//电费
    private LinearLayout linearBaselivingRanQi;//燃气

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_baseliving);
        linearBaselivingShui = (LinearLayout) findViewById(R.id.linear_baseliving_shui);
        linearBaselivingDian = (LinearLayout) findViewById(R.id.linear_baseliving_dian);
        linearBaselivingRanQi = (LinearLayout) findViewById(R.id.linear_baseliving_ranqi);
        topbar = (NormalTopBar) findViewById(R.id.topbar_baseliving);
    }

    @Override
    public void initListener() {
        super.initListener();
        linearBaselivingShui.setOnClickListener(this);
        linearBaselivingDian.setOnClickListener(this);
        linearBaselivingRanQi.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        setTopBar();
    }

    private void setTopBar() {
        topbar.setTitle("生活缴费");
        topbar.setOnBackListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_baseliving_shui:
                UtilMethod.startIntentParams(getmActivity(), LivingPay.class, "type", "水费");
                break;
            case R.id.linear_baseliving_dian:
                UtilMethod.startIntentParams(getmActivity(), LivingPay.class, "type", "电费");
                break;
            case R.id.linear_baseliving_ranqi:
                UtilMethod.startIntentParams(getmActivity(), LivingPay.class, "type", "燃气费");
                break;
            case R.id.bar_back:
                backActivity();
                break;
        }
    }
}
