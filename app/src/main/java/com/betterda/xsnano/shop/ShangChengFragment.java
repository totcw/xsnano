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
    private DuoBaofragment duobaofragment;
    private DuiHuanfragment duihuanfragment;
    private List<Fragment> fragmentList;
    public int item;

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

        shop_indicator.setViewPager(vp_shop, 0);
        shop_indicator.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                item = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//隐藏

        } else {
            if (vp_shop != null) {
                if (item == 0) {
                    vp_shop.setCurrentItem(0);
                } else {
                    vp_shop.setCurrentItem(1);
                }
                //更新数据
                update();

            }
        }
    }

    private void update() {
        if (duobaofragment != null) {
            duobaofragment.getDataAndPage();
        }
        if (duihuanfragment != null) {
            duihuanfragment.getDataAndPage();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getmActivity(), ZhongJRecordActivity.class);
        startActivityForResult(intent,1);
        UtilMethod.setOverdepengingIn(getmActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //更新数据
            update();
        }
    }
}
