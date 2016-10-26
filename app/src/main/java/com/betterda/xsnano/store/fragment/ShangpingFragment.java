package com.betterda.xsnano.store.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.store.presenter.IShangPingPresenter;
import com.betterda.xsnano.store.presenter.IShangPingPresenterImpl;
import com.betterda.xsnano.store.view.IShangPingView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.StoreRecycleView;

/**
 * 业务范围
 * Created by Administrator on 2016/4/21.
 */
public class ShangpingFragment extends BaseFragment implements IShangPingView{
    private LoadingPager loadpager_shangpin;
    public StoreRecycleView rv_shangpin1;
    public StoreRecycleView rv_shangpin2;
    private LinearLayout linear_shangpin;
    private IShangPingPresenter iShangPingPresenter;
    private String id;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_shangpin,null);
        loadpager_shangpin = (LoadingPager) view.findViewById(R.id.loadpager_shangpin);
        rv_shangpin1 = (StoreRecycleView) view.findViewById(R.id.rv_shangpin1);
        rv_shangpin2 = (StoreRecycleView) view.findViewById(R.id.rv_shangpin2);
        linear_shangpin = (LinearLayout) view.findViewById(R.id.linear_shangpin);
        return view;
    }

    @Override
    public void initData() {
        loadpager_shangpin.setLoadVisable();
        Bundle arguments = getArguments();
        if (null != arguments) {

        id = arguments.getString("id");
        }
        rv_shangpin1.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rv_shangpin2.setOverScrollMode(View.OVER_SCROLL_NEVER);
        iShangPingPresenter = new IShangPingPresenterImpl(this,id);
        iShangPingPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_shangpin;
    }

    @Override
    public StoreRecycleView getRecycleView1() {
        return rv_shangpin1;
    }

    @Override
    public StoreRecycleView getRecycleView2() {
        return rv_shangpin2;
    }

    @Override
    public LinearLayout getLinearLayout() {
        return linear_shangpin;
    }
}
