package com.betterda.xsnano.orderall.presenter;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.orderall.view.ISendFragmentView;
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

/**
 * Created by Administrator on 2016/5/27.
 */
public class ISendFragmentPresenterImpl implements ISendFragmentPresenter{
    private ISendFragmentView iSendFragmentView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LoadingPager loadingPager;
    private List<OrderAll> list;

    public ISendFragmentPresenterImpl(ISendFragmentView iSendFragmentView) {
        this.iSendFragmentView = iSendFragmentView;
    }

 


    @Override
    public void start() {
        list = new ArrayList<>();
        recyclerView = iSendFragmentView.getRecycleView();
        loadingPager = iSendFragmentView.getLoadPager();
        recyclerView.setLayoutManager(new LinearLayoutManager(iSendFragmentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<OrderAll>(iSendFragmentView.getmActivity(), R.layout.item_recycleview_order, list) {

            @Override
            public void convert(ViewHolder viewHolder, OrderAll orderAll) {
                UtilMethod.settingView(viewHolder, orderAll,iSendFragmentView.getmActivity());
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