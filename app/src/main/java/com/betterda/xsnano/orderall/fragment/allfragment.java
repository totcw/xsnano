package com.betterda.xsnano.orderall.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.orderall.presenter.IAllFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.IAllFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.IAllFragmentView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * 全部的fragment
 * Created by Administrator on 2016/5/27.
 */
public class allfragment extends BaseFragment implements IAllFragmentView{
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private IAllFragmentPresenter iAllFragmentPresenter;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_all);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_all);
        return view;
    }

    @Override
    public void initData() {
        loadpager_all.setLoadVisable();
        iAllFragmentPresenter = new IAllFragmentPresenterImpl(this);
        iAllFragmentPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_all;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_all;
    }
}
