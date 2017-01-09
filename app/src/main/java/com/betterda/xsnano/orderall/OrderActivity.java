package com.betterda.xsnano.orderall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.adapter.MyAdapter;
import com.betterda.xsnano.orderall.fragment.allfragment;
import com.betterda.xsnano.orderall.fragment.bringfragment;
import com.betterda.xsnano.orderall.fragment.commentfragment;
import com.betterda.xsnano.orderall.fragment.payfragment;
import com.betterda.xsnano.orderall.fragment.sendfragment;
import com.betterda.xsnano.orderall.fragment.takefragment;
import com.betterda.xsnano.orderall.view.IOrderView;
import com.betterda.xsnano.store.fragment.CommentFragment;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理界面
 * Created by Administrator on 2016/5/27.
 */
public class OrderActivity extends BaseActivity implements IOrderView, View.OnClickListener {
    private NormalTopBar topBar;
    private ViewPagerIndicator order_indicator;
    private ViewPager vp_order;
    private List<String> mDatas; //viewpager指示器的数据
    private Fragment allfragment,payfragment,sendfragment,
            bringfragment,takefragment,commentfragment;
    private List<Fragment> fragmentList;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_order);
        topBar = (NormalTopBar) findViewById(R.id.topbar_order);
        order_indicator = (ViewPagerIndicator) findViewById(R.id.order_indicator);
        vp_order = (ViewPager) findViewById(R.id.vp_order);


    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("订单");
        mDatas = new ArrayList<>();
        mDatas.add("全部");
        mDatas.add("待发货");
        mDatas.add("待付款");
        mDatas.add("待收货");
        mDatas.add("待提货");
        mDatas.add("待评价");

        fragmentList = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("item", 0);
        allfragment = new payfragment();
        allfragment.setArguments(bundle);
        //待发货
        Bundle bundle2 = new Bundle();
        bundle2.putInt("item", 2);
        payfragment = new payfragment();
        payfragment.setArguments(bundle2);
        //待付款
        Bundle bundle3 = new Bundle();
        bundle3.putInt("item", 1);
        sendfragment = new payfragment();
        sendfragment.setArguments(bundle3);
        Bundle bundle4 = new Bundle();
        bundle4.putInt("item", 3);
        bringfragment = new payfragment();
        bringfragment.setArguments(bundle4);
        Bundle bundle5 = new Bundle();
        bundle5.putInt("item", 4);
        takefragment = new payfragment();
        takefragment.setArguments(bundle5);
        Bundle bundle6 = new Bundle();
        bundle6.putInt("item", 5);
        commentfragment = new payfragment();
        commentfragment.setArguments(bundle6);
        fragmentList.add(allfragment);
        fragmentList.add(payfragment);
        fragmentList.add(sendfragment);
        fragmentList.add(bringfragment);
        fragmentList.add(takefragment);
        fragmentList.add(commentfragment);
        vp_order.setOffscreenPageLimit(1);
        //设置Tab上的标题
        order_indicator.setTabItemTitles(mDatas);
        vp_order.setAdapter(new MyAdapter(getSupportFragmentManager(),fragmentList));
        int item = getIntent().getIntExtra("item", 0);
        //设置关联的ViewPager
        order_indicator.setViewPager(vp_order, item);
      /*  order_indicator.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Intent intent = new Intent();
                intent.setAction("com.betterda.xsnano.orderall");
                intent.putExtra("item", position + "");
                sendBroadcast(intent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/


    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
        }
    }



}
