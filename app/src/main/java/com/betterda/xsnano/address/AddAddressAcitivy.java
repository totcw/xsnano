package com.betterda.xsnano.address;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.address.presenter.IAddAddPresenter;
import com.betterda.xsnano.address.presenter.IAddAddPresenterImpl;
import com.betterda.xsnano.address.view.IAddAddView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 添加地址
 * Created by Administrator on 2016/5/17.
 */
public class AddAddressAcitivy extends BaseActivity implements IAddAddView, View.OnClickListener {
    private NormalTopBar topBar;
    private IAddAddPresenter addAddPresenter;
    private EditText et_name,et_number,et_address2;
    private Button btn_setting;
    private RelativeLayout relative_province;
    private TextView tv_address;
    @Override
    public void initView() {
        setContentView(R.layout.activity_addaddress);
        topBar = (NormalTopBar) findViewById(R.id.topbar_addaddress);
        btn_setting = (Button) findViewById(R.id.btn_address_moren);
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        relative_province = (RelativeLayout) findViewById(R.id.relative_addaddress_province);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_address2 = (EditText) findViewById(R.id.et_address2);
    }

    @Override
    public void initListener() {
        topBar.setOnActionListener(this);
        topBar.setOnBackListener(this);
        btn_setting.setOnClickListener(this);
        relative_province.setOnClickListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("添加地址");
        topBar.setActionText("保存");
        topBar.setActionTextVisibility(true);
        addAddPresenter = new IAddAddPresenterImpl(this);
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.bar_action:
               addAddPresenter.save();
                break;
            case R.id.btn_address_moren:
               addAddPresenter.saveMoren();
                break;
            case R.id.relative_addaddress_province:
                addAddPresenter.showProvince();
                break;
        }
    }

    @Override
    public EditText getEditViewName() {
        return et_name;
    }

    @Override
    public EditText getEditViewNumber() {
        return et_number;
    }




    @Override
    public EditText getEditViewAdress2() {
        return  et_address2;
    }

    @Override
    public void setText(String address) {
        if (tv_address != null) {
            tv_address.setText(address);
        }
    }
}
