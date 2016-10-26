package com.betterda.xsnano.orderall.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.interfac.CancelOrderInterface;
import com.betterda.xsnano.orderall.OrderDetailActivity;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.orderall.view.IAllFragmentView;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

import javax.xml.transform.Source;

/**
 * Created by Administrator on 2016/5/27.
 */
public class IAllFragmentPresenterImpl implements IAllFragmentPresenter {
    private IAllFragmentView iAllFragmentView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LoadingPager loadingPager;
    private List<OrderAll> list;
    private CallDialog dialog;
    private CancelOrderInterface cancelOrderInterface;

    public IAllFragmentPresenterImpl(IAllFragmentView iAllFragmentView) {
        this.iAllFragmentView = iAllFragmentView;
    }

    @Override
    public void start() {
        list = new ArrayList<>();
        recyclerView = iAllFragmentView.getRecycleView();
        loadingPager = iAllFragmentView.getLoadPager();
        recyclerView.setLayoutManager(new LinearLayoutManager(iAllFragmentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<OrderAll>(iAllFragmentView.getmActivity(), R.layout.item_recycleview_order, list) {

            @Override
            public void convert(ViewHolder viewHolder,  OrderAll orderAll) {
                UtilMethod.settingView(viewHolder, orderAll,iAllFragmentView.getmActivity());
            }
        });
        recyclerView.setAdapter(adapter);
        getData();
    }




    private void getData() {
        RequestParams params = new RequestParams("http://www.baidu.com");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (loadingPager != null) {
                    loadingPager.hide();
                }

                for (int i = 0; i < 10; i++) {
                    OrderAll all = new OrderAll();
                    List<Bus> busList = new ArrayList<>();
                    for (int j = 0; j < i; j++) {
                        Bus bus = new Bus();
                        bus.setName("商品" + i + j);
                        bus.setMoney(i);
                        bus.setAmount(i);
                        busList.add(bus);
                    }
                    all.setList(busList);
                    all.setTime(UtilMethod.getCurrdentTime());
                    all.setType("交易成功");
                    if (i == 0) {
                        all.setState("待发货");
                    }
                    if (i == 1) {
                        all.setState("待收货");
                    }
                    if (i == 2) {
                        all.setState("待评价");
                    }
                    if (i == 3) {
                        all.setState("待付款");
                    }
                    list.add(all);
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                if (recyclerView != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadingPager != null) {
                    loadingPager.setErrorVisable();
                }
            }
        });
    }


}
