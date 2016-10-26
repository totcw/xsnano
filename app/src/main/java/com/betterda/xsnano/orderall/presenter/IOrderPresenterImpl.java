package com.betterda.xsnano.orderall.presenter;

import com.betterda.xsnano.orderall.view.IOrderView;

/**
 * Created by Administrator on 2016/5/27.
 */
public class IOrderPresenterImpl implements IOrderPresenter {
    private IOrderView iOrderView;

    public IOrderPresenterImpl(IOrderView iOrderView) {
        this.iOrderView = iOrderView;
    }

    @Override
    public void start() {

    }
}
