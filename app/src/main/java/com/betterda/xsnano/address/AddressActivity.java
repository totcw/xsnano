package com.betterda.xsnano.address;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.address.presenter.IAddressPresenter;
import com.betterda.xsnano.address.presenter.IAddressPresenterImpl;
import com.betterda.xsnano.address.view.IAddressView;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 我的地址
 * Created by Administrator on 2016/5/17.
 */
public class AddressActivity extends BaseActivity implements IAddressView, View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_address;
    private LoadingPager loadpager_address;
    private IAddressPresenter addressPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_address);
        topBar = (NormalTopBar) findViewById(R.id.topbar_address);
        rv_address = (RecyclerView) findViewById(R.id.rv_address);
        loadpager_address = (LoadingPager) findViewById(R.id.loadpager_address);
    }

    @Override
    public void initListener() {
        topBar.setOnActionListener(this);
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("我的收货地址");
        topBar.setActionText("添加");
        topBar.setActionTextVisibility(true);

        addressPresenter = new IAddressPresenterImpl(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != loadpager_address) {
            loadpager_address.setLoadVisable();
        }
        if (addressPresenter != null) {
            addressPresenter.start();
        }
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_address;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.bar_action:
                UtilMethod.startIntent(this, AddAddressAcitivy.class);
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public RecyclerView getRecyclyView() {
        return rv_address;
    }
}
