package com.betterda.xsnano.youhui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.adapter.MyAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ViewPagerIndicator;
import com.betterda.xsnano.youhui.fragment.Youhuifragment;
import com.betterda.xsnano.youhui.fragment.zitifragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡券
 * Created by lyf
 */
public class YouHuiActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private LoadingPager loadpager_youhui;
    private ViewPagerIndicator youhui_indicator;
    private ViewPager vp_youhui;
    private List<String> mDatas; //viewpager指示器的数据
    private Fragment allfragment,payfragment,bringfragment;
    private List<Fragment> fragmentList;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_youhuiquan);
        topBar = (NormalTopBar) findViewById(R.id.topbar_youhui);
        loadpager_youhui = (LoadingPager) findViewById(R.id.loadpager_youhui);
        vp_youhui = (ViewPager) findViewById(R.id.vp_youhui);
        youhui_indicator = (ViewPagerIndicator) findViewById(R.id.youhui_indicator);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
    }



    @Override
    public void init() {
        super.init();
        topBar.setTitle("卡券");


        mDatas = new ArrayList<>();
        mDatas.add("卡券");
        mDatas.add("自提码");


        fragmentList = new ArrayList<>();
        allfragment = new Youhuifragment();
        Bundle bundle = new Bundle();
        bundle.putInt("item", 0);
        allfragment.setArguments(bundle);
        payfragment = new zitifragment();

        fragmentList.add(allfragment);
        fragmentList.add(payfragment);


        //设置Tab上的标题
        youhui_indicator.setTabItemTitles(mDatas);
        vp_youhui.setAdapter(new MyAdapter(getSupportFragmentManager(),fragmentList));
        //设置关联的ViewPager
        youhui_indicator.setViewPager(vp_youhui, 0);


    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        finish();
    }
}
