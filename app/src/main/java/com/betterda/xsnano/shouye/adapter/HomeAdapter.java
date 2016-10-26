package com.betterda.xsnano.shouye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.shouye.model.Store;
import com.betterda.xsnano.shouye.presenter.IShouyePresenter;
import com.betterda.xsnano.shouye.presenter.IShouyeThreePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页listview的适配器
 * Created by Administrator on 2016/4/21.
 */
public class HomeAdapter extends BaseAdapter {
    private IShouyeThreePresenter iShouyePresenter;
    public static final int ONE_SCREEN_COUNT = 7; // 一屏能显示的个数，这个根据屏幕高度和各自的需求定

    public HomeAdapter(IShouyeThreePresenter iShouyePresenter) {
        this.iShouyePresenter = iShouyePresenter;

    }

    @Override
    public int getCount() {
        if (null != iShouyePresenter.getList()) {
            return iShouyePresenter.getList().size();
        } else {
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (iShouyePresenter == null) {
            return null;
        }
        View view = iShouyePresenter.getView(position, convertView);
        return view;
    }

    // 设置数据
    public void setData(List<Store> list) {
        iShouyePresenter.getList().clear();
        addALL(list);
        // 添加空数据
        if (list.size() < ONE_SCREEN_COUNT) {
            addALL(createEmptyList(ONE_SCREEN_COUNT - list.size()));
        }

        notifyDataSetChanged();
    }

    // 创建不满一屏的空数据
    public List<Store> createEmptyList(int size) {
        List<Store> emptyList = new ArrayList<>();
        if (size <= 0) {
            //开启上啦加载
          //  iShouyePresenter.startLoad();
            return emptyList;
        }
        //有空数据停止上啦加载
        //iShouyePresenter.stopLoad();
        for (int i = 0; i < size; i++) {
            Store store = new Store();
            store.setIsNotData(true);
            emptyList.add(store);
        }
        return emptyList;
    }

    public void addALL(List<Store> lists) {
        if (lists == null || lists.size() == 0) {
            return;
        }
        iShouyePresenter.getList().addAll(lists);
    }

    /**
     * 上啦加载添加数据时调用
     * @param list
     */
    public void addData(List<Store> list) {
        delteEmptyData(iShouyePresenter.getList());
        addALL(list);
    }

    /**
     * 删除空的数据
     */
    private void delteEmptyData(List<Store> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = list.size()-1; i >= 0; i--) {
            Store store = list.get(i);
            if (store.isNotData()) {
                list.remove(i);
            }
        }

    }
}
