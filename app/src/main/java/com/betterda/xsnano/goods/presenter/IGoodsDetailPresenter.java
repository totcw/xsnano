package com.betterda.xsnano.goods.presenter;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.goods.adapter.GoodsAdapter;
import com.betterda.xsnano.goods.model.Comment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IGoodsDetailPresenter extends IPresenter {
    /**
     * 加入购物车
     */
    void addBus();

    /**
     * 加数量
     */
    void add();

    /**
     * 减少数量
     */
    void sub();

    /**
     * 开启选择界面时调用的方法
     */
    void show();

    /**
     * 开启评论
     */
    void comment();

    List<Comment> getList();

    void onBindViewHolder(GoodsAdapter.GoodsHolder holder, int position);

    void addBuy();

    void canshu();
}
