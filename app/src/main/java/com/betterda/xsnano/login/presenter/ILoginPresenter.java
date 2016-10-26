package com.betterda.xsnano.login.presenter;

import android.content.Intent;
import android.text.Editable;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface ILoginPresenter extends IPresenter {
    //微信登录
    void wxLogin();

    void login();

    void editPwd();

    void register();

    void number(Editable s);

    void pwd(Editable s);

    void onDestroy();

    void weiboLogin();

    void weiboCallBack(int requestCode, int resultCode, Intent data);

    void QQlogin();

    void QqCallBack(int requestCode, int resultCode, Intent data);
}
