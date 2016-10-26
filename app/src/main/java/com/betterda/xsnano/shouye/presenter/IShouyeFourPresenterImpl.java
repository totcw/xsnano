package com.betterda.xsnano.shouye.presenter;

import com.betterda.xsnano.shouye.view.IShouyeView;

/**
 * 与首页金币夺宝区域的交互
 * Created by Administrator on 2016/5/12.
 */
public class IShouyeFourPresenterImpl implements IShouyeFourPresenter {
    private IShouyeView iShouyeView;

    public IShouyeFourPresenterImpl(IShouyeView iShouyeView) {
        this.iShouyeView = iShouyeView;
    }
}
