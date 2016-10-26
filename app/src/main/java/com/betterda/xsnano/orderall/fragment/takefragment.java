package com.betterda.xsnano.orderall.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.interfac.CancelOrderInterface;
import com.betterda.xsnano.orderall.OrderDetailActivity;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.orderall.presenter.ITakeFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.ITakeFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.ITakeFragmentView;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * 待提货的fragment
 * Created by Administrator on 2016/5/27.
 */
public class takefragment extends BaseFragment implements ITakeFragmentView{
    private LoadingPager loadpager_take;
    private  RecyclerView rv_take;
    private ITakeFragmentPresenter iTakeFragmentPresenter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_take, null);
        loadpager_take = (LoadingPager) view.findViewById(R.id.loadpager_fragment_take);
        rv_take = (RecyclerView) view.findViewById(R.id.rv_fragment_take);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        iTakeFragmentPresenter = new ITakeFragmentPresenterImpl(this);
        iTakeFragmentPresenter.start();
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_take;
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_take;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iTakeFragmentPresenter.onDestroy();
    }
}
