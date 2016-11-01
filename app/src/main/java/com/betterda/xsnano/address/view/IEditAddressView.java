package com.betterda.xsnano.address.view;

import android.widget.EditText;

import com.betterda.xsnano.BaseView;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface IEditAddressView extends BaseView {
    EditText getEditViewName();
    EditText getEditViewNumber();

    EditText getEditViewAdress2();

    void setText(String address);
}
