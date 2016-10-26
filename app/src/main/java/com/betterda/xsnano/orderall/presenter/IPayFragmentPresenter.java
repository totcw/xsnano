package com.betterda.xsnano.orderall.presenter;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface IPayFragmentPresenter extends IPresenter {


    void onDestroy();

    void onStart(String item);

    void onStart2();
}
