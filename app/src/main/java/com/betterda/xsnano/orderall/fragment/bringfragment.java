package com.betterda.xsnano.orderall.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.orderall.presenter.IBringFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.IBringFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.IBringFragmentView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * 待收货的fragment
 * Created by Administrator on 2016/5/27.
 */
public class bringfragment extends BaseFragment implements IBringFragmentView{
    private LoadingPager loadpager_bring;
    private RecyclerView rv_bring;
    private IBringFragmentPresenter iBringFragmentPresenter;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_bring, null);
        loadpager_bring = (LoadingPager) view.findViewById(R.id.loadpager_fragment_bring);
        rv_bring = (RecyclerView) view.findViewById(R.id.rv_fragment_bring);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        iBringFragmentPresenter = new IBringFragmentPresenterImpl(this);
        iBringFragmentPresenter.start();
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_bring;
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_bring;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iBringFragmentPresenter.onDestroy();
    }
}
