package com.betterda.xsnano.address.presenter;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/5/20.
 */
public interface IEditAddressPresenter extends IPresenter{
    void save();

    void delete();

    void saveMore();

    void showProvince();
}
