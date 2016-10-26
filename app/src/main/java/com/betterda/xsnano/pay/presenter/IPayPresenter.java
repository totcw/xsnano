package com.betterda.xsnano.pay.presenter;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface IPayPresenter extends IPresenter {
    void wxpay();
    void zfbpay();
    void wypay();
    void pay();
}
