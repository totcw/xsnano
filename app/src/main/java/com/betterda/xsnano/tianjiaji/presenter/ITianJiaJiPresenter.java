package com.betterda.xsnano.tianjiaji.presenter;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.tianjiaji.adapter.TianJiaJiAdapter;
import com.betterda.xsnano.tianjiaji.model.TianJiaJi;

import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public interface ITianJiaJiPresenter extends IPresenter {

    List<TianJiaJi> getList();



    void onBindViewHolder(TianJiaJiAdapter.TianJiaJiHolder holder, int position);

    void clickFirst();

    void clickSecond();
}
