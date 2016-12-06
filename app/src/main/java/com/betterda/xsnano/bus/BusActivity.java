package com.betterda.xsnano.bus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.presenter.IBusPresenter;
import com.betterda.xsnano.bus.presenter.IBusPresenterImpl;
import com.betterda.xsnano.bus.view.IBusView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 购物车界面
 * Created by Administrator on 2016/5/17.
 */
public class BusActivity extends BaseActivity implements IBusView, View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_bus;
    private LoadingPager loadpager_bus;

    private ImageView iv_bus_all,iv_bus_jiesuan,iv_bus_delete;
    private TextView tv_bus_money,tv_bus_heji;
    private IBusPresenter busPresenter ;
    private RelativeLayout relative_bus_check,relative_bus;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bus);
        topBar = (NormalTopBar) findViewById(R.id.topbar_bus);
        rv_bus = (RecyclerView) findViewById(R.id.rv_bus);
        iv_bus_all = (ImageView) findViewById(R.id.iv_bus_all);
        iv_bus_jiesuan = (ImageView) findViewById(R.id.iv_bus_jiesuan);
        iv_bus_delete = (ImageView) findViewById(R.id.iv_bus_delete);
        tv_bus_money = (TextView) findViewById(R.id.tv_bus_money);
        tv_bus_heji = (TextView) findViewById(R.id.tv_bus_heji);
        relative_bus_check = (RelativeLayout) findViewById(R.id.relative_bus_check);
        relative_bus = (RelativeLayout) findViewById(R.id.relative_bus);
        loadpager_bus = (LoadingPager) findViewById(R.id.loadpager_bus);

    }

    @Override
    public void initListener() {
        topBar.setOnActionListener(this);
        topBar.setOnBackListener(this);

        iv_bus_jiesuan.setOnClickListener(this);
        iv_bus_delete.setOnClickListener(this);
        relative_bus_check.setOnClickListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("购物车");
        topBar.setActionText("编辑");
        topBar.setActionTextVisibility(true);
        loadpager_bus.setLoadVisable();

        busPresenter = new IBusPresenterImpl(this);
        busPresenter.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.bar_action://编辑
                busPresenter.edit();
                break;
            case R.id.relative_bus_check: //全选
                busPresenter.check();
                break;
            case R.id.iv_bus_jiesuan:
                busPresenter.jiesuan();
                break;
            case R.id.iv_bus_delete:
                busPresenter.delete();
                break;
        }
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_bus;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_bus;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public ImageView getImageViewAll() {
        return iv_bus_all;
    }

    @Override
    public ImageView getImageViewJiesuan() {
        return iv_bus_jiesuan;
    }

    @Override
    public NormalTopBar getNormalBar() {
        return topBar;
    }

    @Override
    public ImageView getImageViewDelete() {
        return iv_bus_delete;
    }

    @Override
    public TextView getTextViewSum() {
        return tv_bus_money;
    }

    @Override
    public TextView gettextViewHeji() {
        return tv_bus_heji;
    }

    @Override
    public RelativeLayout getRelative() {
        return relative_bus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        busPresenter.onDestroy();
    }
}
