package com.betterda.xsnano.findpwd.presenter;

import android.text.Editable;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.view.CountDown;

/**
 * Created by Administrator on 2016/5/26.
 */
public interface IFindPwdPresenter extends IPresenter {
    void findpwd();

    void number(Editable s);
    void yzm(Editable s);
    void pwd(Editable s);

    void countDown();

    void setSelect(CountDown countDown);
}
