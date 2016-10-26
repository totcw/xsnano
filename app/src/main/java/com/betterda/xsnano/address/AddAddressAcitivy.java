package com.betterda.xsnano.address;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    private EditText et_name,et_number,et_address,et_address2;
    private Button btn_setting;
    @Override
    public void initView() {
        setContentView(R.layout.activity_addaddress);
        topBar = (NormalTopBar) findViewById(R.id.topbar_addaddress);
        btn_setting = (Button) findViewById(R.id.btn_address_moren);
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        et_address = (EditText) findViewById(R.id.et_address);
        et_address2 = (EditText) findViewById(R.id.et_address2);
    }

    @Override
    public void initListener() {
        topBar.setOnActionListener(this);
        topBar.setOnBackListener(this);
        btn_setting.setOnClickListener(this);
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
    public EditText getEditViewAdress() {
        return et_address;
    }

    @Override
    public EditText getEditViewAdress2() {
        return  et_address2;
    }
}
