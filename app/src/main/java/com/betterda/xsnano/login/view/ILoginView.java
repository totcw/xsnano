package com.betterda.xsnano.login.view;

import android.widget.Button;
import android.widget.EditText;

import com.betterda.xsnano.BaseView;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface ILoginView extends BaseView {


    Button getBtnLogin();

    EditText getEditLogin();
    EditText getEditLoginPwd();
}
