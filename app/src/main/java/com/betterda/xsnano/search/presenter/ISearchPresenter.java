package com.betterda.xsnano.search.presenter;

import android.view.View;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.search.adapter.SearchAdapter;
import com.betterda.xsnano.shouye.model.Store;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public interface ISearchPresenter extends IPresenter {

    /**
     * 创建recycleview的ivew
     */
    public View getView();

    /**
     * 数据的容器
     *
     */
    public List<Store> getList();

    /**
     * 设置recycleview的数据
     * @param holder
     * @param position
     */
    public void setData(SearchAdapter.MyViewHolder holder, int position);

    /**
     * 删除
     */
    public void delete();
    /**
     * 搜索
     */
    public void search();
}
