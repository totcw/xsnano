package com.betterda.xsnano.bus.presenter;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.bus.adapter.BusAdapter;
import com.betterda.xsnano.bus.model.Bus;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IBusPresenter extends IPresenter {
    List<Bus> getList();

    void onBindViewHolder(BusAdapter.BusHolder holder, int position);

    /**
     * 编辑
     * @param
     */
    void edit();

    void check();

    void delete();

    void jiesuan();

    void onDestroy();
}
