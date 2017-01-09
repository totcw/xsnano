package com.betterda.xsnano.livingpay;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 缴费输入帐号界面
 * Created by Administrator on 2016/9/8.
 */
public class LivingPay extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private Button btnNext;
    private TextView tvLivingpayType;
    private ImageView ivLivingpayIcon;
    private String type;
    private String name;
    private String company;//单位
    private String arrearage;//欠费
    private String balance;//余额

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_livingpay2);
        btnNext = (Button) findViewById(R.id.btn_livingpay2_next);
        topBar = (NormalTopBar) findViewById(R.id.topbar_livingpay2);
        tvLivingpayType = (TextView) findViewById(R.id.tv_livingpay2_type);
        ivLivingpayIcon = (ImageView) findViewById(R.id.iv_livingpay2_icon);
    }

    @Override
    public void initListener() {
        super.initListener();
        btnNext.setOnClickListener(this);
        topBar.setOnBackListener(this);
    }


    @Override
    public void init() {
        super.init();
        getIntentData();
        setTopBar();
        setType();
    }

    /**
     * 设置充值类型
     */
    private void setType() {
        tvLivingpayType.setText(type);
        int id = R.mipmap.pay_shui;
        switch (type) {
            case "水费":
                 id = R.mipmap.pay_shui;
                break;
            case "电费":
                id = R.mipmap.pay_dian;
                break;
            case "燃气费":
                id = R.mipmap.pay_ranqi;
                break;
        }
        ivLivingpayIcon.setImageResource(id);
    }

    private void setTopBar() {
        topBar.setTitle("生活缴费");
    }

    /**
     * 获取充值类型
     */
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_livingpay2_next:
                next();
                break;
            case R.id.bar_back:
                backActivity();
                break;
        }
    }

    private void next() {

        UtilMethod.Toast(getmActivity(),"没有找到相应的帐号,请确认帐号是否正确或联系在线客服");


        /*Intent intent = new Intent(getmActivity(), LivingPayActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("name",name);
        intent.putExtra("company",company);
        intent.putExtra("arrearage",arrearage);
        intent.putExtra("balance",balance);
        startActivity(intent);*/
    }
}
