package com.betterda.xsnano.pay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.pay.presenter.IPayPresenter;
import com.betterda.xsnano.pay.presenter.IPayPresenterImpl;
import com.betterda.xsnano.pay.view.IPayView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 支付页面
 * Created by Administrator on 2016/5/20.
 */
public class PayActivity extends BaseActivity implements IPayView, View.OnClickListener {
    private NormalTopBar topBar;
    private TextView tv_money, tv_money2, tv_money3, tv_money4;
    private RelativeLayout relative_wxpay, relative_zfbpay, relative_wypay,relative_name;
    private Button btn_pay;
    private IPayPresenter iPayPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_pay);
        topBar = (NormalTopBar) findViewById(R.id.topbar_pay);
        tv_money = (TextView) findViewById(R.id.tv_pay_money);
        tv_money2 = (TextView) findViewById(R.id.tv_pay_money2);
        tv_money3 = (TextView) findViewById(R.id.tv_pay_money3);
        tv_money4 = (TextView) findViewById(R.id.tv_pay_money4);
        relative_wxpay = (RelativeLayout) findViewById(R.id.relative_pay_wxpay);
        relative_zfbpay = (RelativeLayout) findViewById(R.id.relative_pay_zfbpay);
        relative_wypay = (RelativeLayout) findViewById(R.id.relative_pay_wypay);
        relative_name = (RelativeLayout) findViewById(R.id.relative_pay_name);
        btn_pay = (Button) findViewById(R.id.btn_pay_confirm);
    }

    @Override
    public void initListener() {
        relative_wxpay.setOnClickListener(this);
        relative_zfbpay.setOnClickListener(this);
        relative_wypay.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("选择支付方式");
        iPayPresenter = new IPayPresenterImpl(this);
        iPayPresenter.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.relative_pay_wxpay:
                iPayPresenter.wxpay();
                break;
            case R.id.relative_pay_zfbpay:
                iPayPresenter.zfbpay();
                break;
            case R.id.relative_pay_wypay:
                iPayPresenter.wypay();
                break;
            case R.id.btn_pay_confirm:
                iPayPresenter.pay();
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(false);//设置返回键点击不了
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();

    }
    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public TextView getTextViewMoney() {
        return tv_money;
    }

    @Override
    public TextView getTextViewMoney2() {
        return tv_money2;
    }

    @Override
    public TextView getTextViewMoney3() {
        return tv_money3;
    }

    @Override
    public TextView getTextViewMoney4() {
        return tv_money4;
    }

    @Override
    public RelativeLayout getRelativeWxpay() {
        return relative_wxpay;
    }

    @Override
    public RelativeLayout getRelativeWypay() {
        return relative_wypay;
    }

    @Override
    public RelativeLayout getRelativeZfbpay() {
        return relative_zfbpay;
    }

    @Override
    public RelativeLayout getRelativeName() {
        return relative_name;
    }
}
