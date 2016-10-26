package com.betterda.xsnano.order.presenter;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.javabean.OrderComfirm;
import com.betterda.xsnano.order.adapter.ComfirmOrderAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface IComfirmOrderPresenter extends IPresenter {
    void address();

    void pay();

    List<OrderComfirm> getList();

    void onBindViewHolder(ComfirmOrderAdapter.OrderHolder holder, int position);

    void ziti();

    void kuaidi();
    void shi();
    void fou();

    void setName(String name);
    void setNumber(String number);
    void setAddress(String address);
}
