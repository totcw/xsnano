package com.betterda.xsnano.orderall.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.orderall.presenter.ISendFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.ISendFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.ISendFragmentView;
import com.betterda.xsnano.sale.view.ISaleGoodsView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * 代发货的fragment
 * Created by Administrator on 2016/5/27.
 */
public class sendfragment extends BaseFragment implements ISendFragmentView {
    private LoadingPager loadpager_send;
    private RecyclerView rv_send;
    private ISendFragmentPresenter iSendFragmentPresenter;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_send, null);
        loadpager_send = (LoadingPager) view.findViewById(R.id.loadpager_fragment_send);
        rv_send = (RecyclerView) view.findViewById(R.id.rv_fragment_send);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        iSendFragmentPresenter = new ISendFragmentPresenterImpl(this);
        iSendFragmentPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_send;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_send;
    }
}
