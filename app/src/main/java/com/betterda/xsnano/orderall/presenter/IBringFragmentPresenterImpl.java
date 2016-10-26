package com.betterda.xsnano.orderall.presenter;

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
import com.betterda.xsnano.orderall.view.IBringFragmentView;
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
public class IBringFragmentPresenterImpl implements  IBringFragmentPresenter{
    private IBringFragmentView iBringFragmentView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LoadingPager loadingPager;
    private List<OrderAll> list;
  
    private CallDialog dialog;
    private CancelOrderInterface cancelOrderInterface;
    public IBringFragmentPresenterImpl(IBringFragmentView iBringFragmentView) {
        this.iBringFragmentView = iBringFragmentView;
    }


    @Override
    public void start() {
        list = new ArrayList<>();
        if (null == dialog) {
            dialog = new CallDialog(iBringFragmentView.getmActivity(), new CallDialog.onConfirmListener() {
                @Override
                public void comfirm() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    if (null != cancelOrderInterface) {
                        cancelOrderInterface.Cancel();
                    }

                }

                @Override
                public void cancel() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            },"确定要收货吗?","确定");
        }
        recyclerView = iBringFragmentView.getRecycleView();
        loadingPager = iBringFragmentView.getLoadPager();
        recyclerView.setLayoutManager(new LinearLayoutManager(iBringFragmentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<OrderAll>(iBringFragmentView.getmActivity(), R.layout.item_recycleview_order, list) {

            @Override
            public void convert(ViewHolder viewHolder, OrderAll orderAll) {
              settingView2(viewHolder,orderAll);
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
                    all.setState("待收货");
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
    public  void settingView2(final ViewHolder viewHolder, final OrderAll orderAll) {
        if (null != orderAll) {
            //设置时间
            viewHolder.setText(R.id.tv_item_orderall_time, orderAll.getTime());
            //设置交易类型
            viewHolder.setText(R.id.tv_item_orderall_type, orderAll.getType());

            List<Bus> busList = orderAll.getList();
            if (null != busList) {
                final float price = UtilMethod.addUp(busList);
                //设置商品的件数
                viewHolder.setText(R.id.tv_item_orderall_amount2, "共" + UtilMethod.addAmount(busList) + "件商品");
                //设置商品的价格
                viewHolder.setText(R.id.tv_item_orderall_money, "￥" + UtilMethod.addUp(busList));
                RecyclerView rv = viewHolder.getView(R.id.rv_order_item);
                rv.setLayoutManager(new LinearLayoutManager(iBringFragmentView.getmActivity()));
                rv.setAdapter(new CommonAdapter<Bus>(iBringFragmentView.getmActivity(), R.layout.item_recycleview_comfirmorder, busList) {

                    @Override
                    public void convert(ViewHolder viewHolder, Bus bus) {
                        if (bus != null) {
                            //设置商品信息
                            viewHolder.setText(R.id.tv_item_order_name, bus.getName());
                            viewHolder.setText(R.id.tv_item_order_price, "￥" + bus.getMoney());
                            viewHolder.setText(R.id.tv_item_order_amount, "X " + bus.getAmount());
                            if (!TextUtils.isEmpty(bus.getUrl())) {
                                SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_order);
                                if (simpleDraweeView != null) {
                                    simpleDraweeView.setImageURI(Uri.parse(bus.getUrl()));
                                }
                            }
                            viewHolder.setOnClickListener(R.id.linear_comfirmorder2, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UtilMethod.startIntent(iBringFragmentView.getmActivity(), OrderDetailActivity.class);
                                }
                            });
                            //显示评价按钮
                            if ("待评价".equals(orderAll.getState())) {
                                viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, true);
                            } else {
                                viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, false);
                            }
                            viewHolder.setOnClickListener(R.id.tv_item_comfirmorder_comment, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UtilMethod.Toast(iBringFragmentView.getmActivity(), "立即评价");
                                }
                            });
                        }


                    }
                });

                if ("待付款".equals(orderAll.getState())) {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, true, true, true, false);

                } else if ("待收货".equals(orderAll.getState())) {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, true, false, false, true);
                } else {
                    //显示待付款的界面
                    UtilMethod.show(viewHolder, false, false, false, false);
                }

                //立即收货
                viewHolder.setOnClickListener(R.id.tv_item_orderall_shouhuo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!iBringFragmentView.getmActivity().isFinishing()) {
                            cancelOrderInterface = new CancelOrderInterface() {
                                @Override
                                public void Cancel() {
                                    UtilMethod.Toast(iBringFragmentView.getmActivity(), "立即收货");
                                }
                            };
                            if (dialog != null) {

                                dialog.show();
                            }
                        }

                    }
                });
                //订单详情
                viewHolder.setOnClickListener(R.id.linear_comfirmorder, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilMethod.startIntent(iBringFragmentView.getmActivity(), OrderDetailActivity.class);
                    }
                });
            }


        }
    }

    @Override
    public void onDestroy() {
      
            dialog = null;
        cancelOrderInterface = null;
    }
}
