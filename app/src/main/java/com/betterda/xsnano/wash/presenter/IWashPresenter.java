package com.betterda.xsnano.wash.presenter;

import com.betterda.xsnano.IPresenter;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IWashPresenter extends IPresenter {
    void clickSecond();

    void clickFirst();
    /**
     * 处理筛选界面的点击事件
     * @param id
     */
    void click(int id);

    void ondestroy();
}
