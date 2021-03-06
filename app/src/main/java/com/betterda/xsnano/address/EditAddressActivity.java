package com.betterda.xsnano.address;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.address.presenter.IEditAddressPresenter;
import com.betterda.xsnano.address.presenter.IEditAddressPresenterImpl;
import com.betterda.xsnano.address.view.IEditAddressView;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 编辑收货地址
 * Created by Administrator on 2016/5/20.
 */
public class EditAddressActivity extends BaseActivity implements IEditAddressView, View.OnClickListener {
    private NormalTopBar topBar;
    private IEditAddressPresenter iEditAddressPresenter;
    private EditText et_name, et_number,  et_address2;
    private Button btn_editaddress_moren;
    private RelativeLayout relative_edit_delete,relative_editaddress_province;
    private TextView tv_address;

    @Override
    public void initView() {
        setContentView(R.layout.activity_editaddress);

        et_address2 = (EditText) findViewById(R.id.et_editadress_address2);
        et_name = (EditText) findViewById(R.id.et_editadress_name);
        et_number = (EditText) findViewById(R.id.et_editadress_number);
        btn_editaddress_moren = (Button) findViewById(R.id.btn_editaddress_moren);
        tv_address = (TextView) findViewById(R.id.tv_editadress_address);
        relative_edit_delete = (RelativeLayout) findViewById(R.id.relative_edit_delete);
        relative_editaddress_province = (RelativeLayout) findViewById(R.id.relative_editaddress_province);
        topBar = (NormalTopBar) findViewById(R.id.topbar_editaddress);

    }

    @Override
    public void initListener() {
        relative_edit_delete.setOnClickListener(this);
        btn_editaddress_moren.setOnClickListener(this);
        topBar.setOnBackListener(this);
        topBar.setOnActionListener(this);
        relative_editaddress_province.setOnClickListener(this);
    }

    @Override
    public void init() {
        topBar.setActionText("保存");
        topBar.setActionTextVisibility(true);
        topBar.setTitle("编辑收货地址");
        iEditAddressPresenter = new IEditAddressPresenterImpl(this);
        iEditAddressPresenter.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.bar_action://保存
                iEditAddressPresenter.save();
                break;
            case R.id.relative_edit_delete://删除
                iEditAddressPresenter.delete();
                break;
            case R.id.btn_editaddress_moren://设为默认地址
                iEditAddressPresenter.saveMore();
                break;
            case R.id.relative_editaddress_province:

                iEditAddressPresenter.showProvince();
                break;

        }
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
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
        return et_address2;
    }

    @Override
    public void setText(String address) {
        if (tv_address != null) {
            tv_address.setText(address);
        }
    }
}
