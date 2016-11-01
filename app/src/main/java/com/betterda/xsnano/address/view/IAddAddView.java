package com.betterda.xsnano.address.view;

import android.widget.EditText;

import com.betterda.xsnano.BaseView;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IAddAddView extends BaseView {
    EditText getEditViewName();
    EditText getEditViewNumber();

    EditText getEditViewAdress2();

    /**
     * 给textview设置值
     * @param address
     */
    void setText(String address);
}
