package com.betterda.xsnano.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.betterda.xsnano.R;
import com.betterda.xsnano.adapter.MyAdapter;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.shop.fragment.DuiHuanfragment;
import com.betterda.xsnano.shop.fragment.DuoBaofragment;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ViewPagerIndicator;
import com.betterda.xsnano.youhui.fragment.Youhuifragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 金币商城
 * Created by Administrator on 2016/4/21.
 */
public class ShangChengFragment extends BaseFragment implements View.OnClickListener {
    protected NormalTopBar topBar;
    private ViewPagerIndicator shop_indicator;
    public ViewPager vp_shop;
    private LoadingPager loadpager_shop;
    private List<String> mDatas; //viewpager指示器的数据
    private Fragment duobaofragment, duihuanfragment;
    private List<Fragment> fragmentList;
    private int item;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_shangcheng, null);
        topBar = (NormalTopBar) view.findViewById(R.id.topbar_shop);
        shop_indicator = (ViewPagerIndicator) view.findViewById(R.id.shop_indicator);
        vp_shop = (ViewPager) view.findViewById(R.id.vp_shop);
        loadpager_shop = (LoadingPager) view.findViewById(R.id.loadpager_shop);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        topBar.setTitle("金币商城");
        topBar.setBackVisibility(false);
        topBar.setOnActionListener(this);
        topBar.setActionText("开奖记录");
        topBar.setActionTextVisibility(true);
        mDatas = new ArrayList<>();
        mDatas.add("金币夺宝");
        mDatas.add("金币兑换");

        Bundle arguments = getArguments();
        if (arguments != null) {
            item = arguments.getInt("item");
        }

        fragmentList = new ArrayList<>();
        duobaofragment = new DuoBaofragment();
        duihuanfragment = new DuiHuanfragment();

        fragmentList.add(duobaofragment);
        fragmentList.add(duihuanfragment);


        //设置Tab上的标题
        shop_indicator.setTabItemTitles(mDatas);
        //在fragment里面嵌套viewpager使用fragment需要传递getChildFragmentManager(),否则第二次加载会出现空白
        vp_shop.setAdapter(new MyAdapter(getChildFragmentManager(), fragmentList));
        //设置关联的ViewPager
        if (item == 1) {
            shop_indicator.setViewPager(vp_shop, 1);
        } else {
            shop_indicator.setViewPager(vp_shop, 0);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        UtilMethod.startIntent(getmActivity(), ZhongJRecordActivity.class);
    }
}
