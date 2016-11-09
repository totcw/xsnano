package com.betterda.xsnano.sale;

import android.app.Service;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.sale.presenter.ISalePresenter;
import com.betterda.xsnano.sale.presenter.ISalePresentereImpl;
import com.betterda.xsnano.sale.view.ISaleView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

import static com.betterda.xsnano.R.id.topbar_sale;

/**
 * 特卖页面
 * Created by Administrator on 2016/5/20.
 */
public class SaleActivity extends BaseActivity implements ISaleView, View.OnClickListener {
    private LoadingPager loadpager_sale;
    private NormalTopBar topBar;
    private RecyclerView rv_sale;

    private ISalePresenter iSalePresenter;
    @Override
    public void initView() {
        setContentView(R.layout.activity_sale);
        loadpager_sale = (LoadingPager) findViewById(R.id.loadpger_sale);
        topBar = (NormalTopBar) findViewById(topbar_sale);
        rv_sale = (RecyclerView) findViewById(R.id.rv_sale);
    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("每日特卖");
        loadpager_sale.setLoadVisable();
        iSalePresenter = new ISalePresentereImpl(this);
        iSalePresenter.start();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_sale;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_sale;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        iSalePresenter.onDestroy();
    }
}
