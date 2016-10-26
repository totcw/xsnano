package com.betterda.xsnano.findpwd.view;

import android.widget.Button;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.CountDown;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface IFindPwdView extends BaseView {
    Button getBtnRegister();

    CountDown getCountDown();
}
