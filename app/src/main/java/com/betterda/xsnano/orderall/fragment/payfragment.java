package com.betterda.xsnano.orderall.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.orderall.presenter.IPayFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.IPayFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.IPayFragmentView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * 代付款
 * Created by Administrator on 2016/5/27.
 */
public class payfragment extends BaseFragment implements IPayFragmentView{
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private IPayFragmentPresenter iPayFragmentPresenter;
    private int i;
    private MyBroadcastReceiver receiver;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_pay, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_pay);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_pay);
        i = getArguments().getInt("item");
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        iPayFragmentPresenter = new IPayFragmentPresenterImpl(this,i);
        iPayFragmentPresenter.start();

        if (null == receiver) {

            receiver = new MyBroadcastReceiver();
            getmActivity().registerReceiver(receiver, new IntentFilter("com.betterda.xsnano.orderall"));

        }


    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_all;
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_all;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (receiver != null) {
            getmActivity().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPayFragmentPresenter.onDestroy();


    }

    @Override
    public void onStart() {
        super.onStart();
        iPayFragmentPresenter.onStart2();
    }

    class  MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String item = intent.getStringExtra("item");
                iPayFragmentPresenter.onStart(item);
            }
        }
    }

}
