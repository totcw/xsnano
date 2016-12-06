package com.betterda.xsnano.sale;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.sale.presenter.ISaleGoodsPresenter;
import com.betterda.xsnano.sale.presenter.ISaleGoodsPresenterImpl;
import com.betterda.xsnano.sale.view.ISaleGoodsView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 特卖商品
 * Created by Administrator on 2016/5/23.
 */
public class SaleGoodsActivity extends BaseActivity implements ISaleGoodsView, View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_salegoods;
    private SimpleDraweeView simpleDraweeView;
    private ISaleGoodsPresenter iSaleGoodsPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_salegoods);
        topBar = (NormalTopBar) findViewById(R.id.topbar_salegoods);
        rv_salegoods = (RecyclerView) findViewById(R.id.rv_salegoods);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sv_salegoods);
    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
        iSaleGoodsPresenter = new ISaleGoodsPresenterImpl(this);
        iSaleGoodsPresenter.start();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public void onClick(View v) {
        backActivity();
    }

    @Override
    public NormalTopBar getTopBar() {
        return topBar;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_salegoods;
    }
}
