package com.betterda.xsnano.register.presenter;

import android.text.Editable;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.view.CountDown;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface IRegisterPresenter extends IPresenter {
    void register();

    void number(Editable s);
    void yzm(Editable s);
    void pwd(Editable s);
    void pwd2(Editable s);

    void countDown();

    void setSelect(CountDown countDown);

    void ondestroy();
}
