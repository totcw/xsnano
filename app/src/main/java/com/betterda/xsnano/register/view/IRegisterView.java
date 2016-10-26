package com.betterda.xsnano.register.view;

import android.widget.Button;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.CountDown;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface IRegisterView extends BaseView{
    Button getBtnRegister();

    CountDown getCountDown();
}
