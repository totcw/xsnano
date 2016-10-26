package com.betterda.xsnano.store.presenter;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface IStorePresenter extends IPresenter {
    void call();

    void ondestroy();
}
