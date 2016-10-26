package com.betterda.xsnano.tianjiaji;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.BusActivity;
import com.betterda.xsnano.tianjiaji.presenter.ITianJiaJiPresenter;
import com.betterda.xsnano.tianjiaji.presenter.ITianJiaJiPresenterImpl;
import com.betterda.xsnano.tianjiaji.view.ITianJiaJiView;
import com.betterda.xsnano.tianjiaji.view.TwoToolBarView;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 添加剂的页面
 * Created by Administrator on 2016/5/16.
 */
public class TianJiaJiActivity extends BaseActivity implements ITianJiaJiView, View.OnClickListener {
    private NormalTopBar topBar;
    private TwoToolBarView twotool;
    private RecyclerView rv_tianjiaji;
    private LinearLayout linear_tianjiaji;
    private LoadingPager loadpager_tianjiaji;
    private ITianJiaJiPresenter iTianJiaJiPresenter;
    private RecyclerView rv_tianjiaji_sort;



    @Override
    public void initView() {
        setContentView(R.layout.activity_tianjiaji);
        topBar = (NormalTopBar) findViewById(R.id.topbar_tianjiaji);
        twotool = (TwoToolBarView) findViewById(R.id.twotool_tianjiaji);
        rv_tianjiaji = (RecyclerView) findViewById(R.id.rv_tianjiaji);
        linear_tianjiaji = (LinearLayout) findViewById(R.id.linear_tianjiaji);
        loadpager_tianjiaji = (LoadingPager) findViewById(R.id.loadpager_tianjiaji);

    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
        twotool.setOnFirstClick(this);
        twotool.setOnSecondClick(this);
        topBar.setOnBusListener(this);
    }

    @Override
    public void init() {
        twotool.setTitle("默认排序","全部品牌");
        topBar.setBusVisibility(true);
        //显示加载界面
        loadpager_tianjiaji.setLoadVisable();

        iTianJiaJiPresenter = new ITianJiaJiPresenterImpl(this);
        iTianJiaJiPresenter.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.mfiv_twotool_fist:
                twotool.setFirstSelect(!twotool.isFirstSelected());
                View pp_tianjiaji_sort =  View.inflate(this, R.layout.pp_tianjiaji_sort, null);
                pp_tianjiaji_sort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopupWindow();
                    }
                });
                rv_tianjiaji_sort = (RecyclerView) pp_tianjiaji_sort.findViewById(R.id.rv_tianjiaji_sort);
                setUpPopupWindow(pp_tianjiaji_sort,twotool,1, UtilMethod.getHeight(this));
                iTianJiaJiPresenter.clickFirst();

                break;
            case R.id.mfiv_twotool_second:
                twotool.setSecondSelect(!twotool.isSecondSelected());
              View  pp_tianjiaji_sort2 =  View.inflate(this, R.layout.pp_tianjiaji_sort, null);
                pp_tianjiaji_sort2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopupWindow();
                    }
                });
                rv_tianjiaji_sort = (RecyclerView) pp_tianjiaji_sort2.findViewById(R.id.rv_tianjiaji_sort);
                setUpPopupWindow(pp_tianjiaji_sort2, twotool, 2, UtilMethod.getHeight(this));
                iTianJiaJiPresenter.clickSecond();
                break;
            case R.id.bar_relative_bus:
                UtilMethod.isLogin(this, BusActivity.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closePopupWindow();

    }


    @Override
    public void dismiss() {
        super.dismiss();
        twotool.setFirstSelect(false);
        twotool.setSecondSelect(false);
    }


    @Override
    public RecyclerView getRecycleView() {
        return rv_tianjiaji;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public LinearLayout getLinearLayout() {
        return linear_tianjiaji;
    }

    @Override
    public RecyclerView getRecyclyView() {
        return rv_tianjiaji_sort;
    }

    @Override
    public TwoToolBarView getTwoTool() {
        return twotool;
    }

    @Override
    public NormalTopBar getTopBar() {
        return topBar;
    }

    @Override
    public LoadingPager getLoadPager() {

        return loadpager_tianjiaji;
    }
    @Override
    public void onBackPressed() {


        if (getPopupWindow() != null&&getPopupWindow().isShowing()) {
            getPopupWindow().dismiss();

        } else {

            super.onBackPressed();
        }
    }
}
