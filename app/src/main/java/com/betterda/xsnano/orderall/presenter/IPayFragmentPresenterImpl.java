package com.betterda.xsnano.orderall.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.comment.AddCommentActivity;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.interfac.CancelOrderInterface;
import com.betterda.xsnano.javabean.OrderList;
import com.betterda.xsnano.javabean.SaleDay;
import com.betterda.xsnano.orderall.OrderDetailActivity;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.orderall.view.IPayFragmentView;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by Administrator on 2016/5/27.
 */
public class IPayFragmentPresenterImpl implements IPayFragmentPresenter, View.OnClickListener {
    private IPayFragmentView iPayFragmentView;

    private HeaderAndFooterRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LoadingPager loadingPager;
    private List<OrderAll> list;
    private CallDialog dialog;
    private CancelOrderInterface cancelOrderInterface;
    private int item;
    private float price2; //订单的价格
    private String status; //订单状态  null 全部
    private String payStatus; //支付状态  null 全部  Y已经支付 N未支付   C取消
    private int page = 1;
    private String rows = "10";


    public IPayFragmentPresenterImpl(IPayFragmentView iPayFragmentView, int item) {
        this.iPayFragmentView = iPayFragmentView;
        this.item = item;

    }



    @Override
    public void start() {
        list = new ArrayList<>();
        if (null == dialog) {
            dialog = new CallDialog(iPayFragmentView.getmActivity(), new CallDialog.onConfirmListener() {
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
            }, "确定要取消订单吗?", "确定");
        }
        recyclerView = iPayFragmentView.getRecycleView();
        loadingPager = iPayFragmentView.getLoadPager();
        recyclerView.setLayoutManager(new LinearLayoutManager(iPayFragmentView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<OrderAll>(iPayFragmentView.getmActivity(), R.layout.item_recycleview_order, list) {

            @Override
            public void convert(ViewHolder viewHolder, OrderAll orderAll) {
                settingView2(viewHolder, orderAll);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {

                    RecyclerViewStateUtils.setFooterViewState(iPayFragmentView.getmActivity(), recyclerView, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);

                    page++;
                    getData();
                }
            }
        });


        if (iPayFragmentView != null && iPayFragmentView.getLoadPager() != null) {
            iPayFragmentView.getLoadPager().setonEmptyClickListener(this);
            iPayFragmentView.getLoadPager().setonErrorClickListener(this);
        }
        switch (item) {
            case 0:
                setStatus(null, null);
                break;
            case 1:
                setStatus("N", null);
                break;
            case 2:
                setStatus(null, "pass");
                break;
            case 3:
                setStatus(null, "alsend");
                break;
            case 4:
                setStatus(null, "pick");
                break;
            case 5:
                setStatus(null, "sign");
                break;
        }

      //  getDataAndPage(1);

    }

    /**
     * 设置请求订单的状态
     */
    private void setStatus(String payStatus, String status) {
        clear();
        this.payStatus = payStatus;
        this.status = status;
    }

    /**
     * 将状态初始化
     */
    private void clear() {
        payStatus = null;
        status = null;
    }

    private void getDataAndPage(int p) {

        page = p;
        if (loadingPager != null) {
            loadingPager.setLoadVisable();
        }
        getData();
    }

    private void getData() {

        RequestParams params = new RequestParams(Constants.URL__ORDER_GET);
        params.addBodyParameter("account", CacheUtils.getString(iPayFragmentView.getmActivity(), "number", ""));
        params.addBodyParameter("payStatus", payStatus);
        params.addBodyParameter("status", status);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println(s);
                List<OrderList> listOrderList = GsonParse.getListOrderList(UtilMethod.getString(s));
                if (list != null) {
                    if (page == 1) {

                        list.clear();
                    }
                }
                if (null != listOrderList) {
                    for (OrderList orderList : listOrderList) {
                        if (null != orderList) {
                            OrderList.OrderBean order = orderList.getOrder();
                            List<OrderList.DetailsBean> details = orderList.getDetails();
                            String order_num = order.getOrder_num();
                            OrderAll orderAll = new OrderAll();
                            orderAll.setId(order_num);
                            orderAll.setState(order.getStatus());
                            orderAll.setType(order.getPay_status());
                            orderAll.setTime(order.getCreate_date());
                            if (!TextUtils.isEmpty(order.getAmount())) {
                                try {
                                    orderAll.setAmount(Float.parseFloat(order.getAmount()));
                                } catch (Exception e
                                        ) {

                                }

                            }
                            if (!TextUtils.isEmpty(order.getFee())) {
                                try {
                                    orderAll.setFreight(Float.parseFloat(order.getFee()));
                                } catch (Exception e
                                        ) {

                                }

                            }

                            if (null != details) {
                                List<Bus> busList = new ArrayList<Bus>();
                                for (OrderList.DetailsBean detailsBean : details) {
                                    Bus bus = new Bus();
                                    bus.setId(detailsBean.getId());
                                    bus.setName(detailsBean.getName());
                                    bus.setAmount(detailsBean.getNumber());
                                    bus.setUrl(UtilMethod.url(detailsBean.getLittle_picture()));
                                    bus.setIsComment(detailsBean.getIs_comment());
                                    bus.setShopId(detailsBean.getShop_id());
                                    try {
                                        bus.setMoney(Float.parseFloat(detailsBean.getSale_price()));
                                    } catch (Exception e) {

                                    }

                                    busList.add(bus);

                                }
                                orderAll.setList(busList);
                            }

                            if (list != null) {
                                list.add(orderAll);
                            }
                        }
                    }
                }
                if (iPayFragmentView != null) {

                    RecyclerViewStateUtils.setLoad(listOrderList, recyclerView, iPayFragmentView.getmActivity());
                }

                if (loadingPager != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadingPager.setEmptyVisable();
                        } else {
                            loadingPager.hide();
                        }
                    } else {
                        loadingPager.setEmptyVisable();
                    }
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

                System.out.println(throwable);
                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (loadingPager != null) {
                        loadingPager.setErrorVisable();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(iPayFragmentView.getmActivity(), recyclerView,
                            Constants.PAGE_SIZE, LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getData();
                                }
                            });
                }
            }
        });
    }

    public void settingView2(final ViewHolder viewHolder, final OrderAll orderAll) {

        if (null != orderAll) {

            //设置时间
            viewHolder.setText(R.id.tv_item_orderall_time, orderAll.getTime());
            //设置交易类型
            if ("Y".equals(orderAll.getType())) {
                viewHolder.setText(R.id.tv_item_orderall_type, "交易完成");
            } else if ("N".equals(orderAll.getType())) {
                viewHolder.setText(R.id.tv_item_orderall_type, "未支付");
            } else {
                viewHolder.setText(R.id.tv_item_orderall_type, "交易关闭");
            }

            List<Bus> busList = orderAll.getList();
            if (null != busList) {

                //设置商品的件数
                viewHolder.setText(R.id.tv_item_orderall_amount2, "共" + UtilMethod.addAmount(busList) + "件商品");
                //设置商品的价格
                viewHolder.setText(R.id.tv_item_orderall_money, "￥" + orderAll.getAmount());
                RecyclerView rv = viewHolder.getView(R.id.rv_order_item);
                rv.setLayoutManager(new LinearLayoutManager(iPayFragmentView.getmActivity()));
                rv.setAdapter(new CommonAdapter<Bus>(iPayFragmentView.getmActivity(), R.layout.item_recycleview_comfirmorder, busList) {

                    @Override
                    public void convert(ViewHolder viewHolder, final Bus bus) {
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
                            //订单详情
                            viewHolder.setOnClickListener(R.id.linear_comfirmorder2, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startOnDetail(orderAll.getId());
                                }
                            });
                            //显示评价按钮
                            if ("sign".equals(orderAll.getState())) {

                                if ("N".equals(bus.getIsComment())) {
                                    viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, true);
                                } else {
                                    viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, false);
                                }
                            }
                            viewHolder.setOnClickListener(R.id.tv_item_comfirmorder_comment, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(iPayFragmentView.getmActivity(), AddCommentActivity.class);
                                    intent.putExtra("orderid", orderAll.getId());
                                    intent.putExtra("productid", bus.getId());
                                    intent.putExtra("shopId", bus.getShopId());
                                    iPayFragmentView.getmActivity().startActivity(intent);

                                }
                            });
                        }


                    }
                });
            }
            //先判断有没有付款,没有是待付款的状态
            if ("N".equals(orderAll.getType())) {
                //显示待付款的界面
                UtilMethod.show(viewHolder, true, true, true, false);


            } else if ("alsend".equals(orderAll.getState())) {
                //显示待收货的界面
                UtilMethod.show(viewHolder, true, false, false, true);
            } else {
                //
                UtilMethod.show(viewHolder, false, false, false, false);
            }

            //立即付款
            viewHolder.setOnClickListener(R.id.tv_item_orderall_pay, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getPay(orderAll.getAmount(), orderAll.getFreight(), orderAll.getId());

                }
            });
            //取消订单
            viewHolder.setOnClickListener(R.id.tv_item_orderall_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!iPayFragmentView.getmActivity().isFinishing()) {
                        cancelOrderInterface = new CancelOrderInterface() {
                            @Override
                            public void Cancel() {
                                getData3(orderAll.getId());
                            }
                        };
                        if (dialog != null) {
                            dialog.setTitle("确定要取消订单吗?");
                            dialog.show();
                        }
                    }

                }
            });
            //立即收货
            viewHolder.setOnClickListener(R.id.tv_item_orderall_shouhuo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!iPayFragmentView.getmActivity().isFinishing()) {
                        cancelOrderInterface = new CancelOrderInterface() {
                            @Override
                            public void Cancel() {
                                getData2(orderAll.getId());
                            }
                        };
                        if (dialog != null) {
                            dialog.setTitle("确定要收货吗?");
                            dialog.show();
                        }
                    }
                }
            });
            //订单详情
            viewHolder.setOnClickListener(R.id.linear_comfirmorder, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startOnDetail(orderAll.getId());
                }
            });

        }
    }

    /**
     * 立即付款
     *
     * @param money   金额
     * @param freight 运费
     */
    private void getPay(float money, float freight, String orderid) {

        Intent intent = new Intent(iPayFragmentView.getmActivity(), PayActivity.class);
        intent.putExtra("orderid", orderid);
        intent.putExtra("money", money);
        intent.putExtra("money3", freight);
        iPayFragmentView.getmActivity().startActivity(intent);
    }

    /**
     * 取消订单
     *
     * @param orderid
     */
    private void getData3(String orderid) {
        RequestParams params = new RequestParams(Constants.URL__ORDER_CANCEL);
        params.addBodyParameter("orderNum", orderid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                getDataAndPage(1);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }

    private void startOnDetail(String orderid) {
        Intent intent = new Intent(iPayFragmentView.getmActivity(), OrderDetailActivity.class);
      /*  if (1 == item) {
            intent.putExtra("orderid", "待付款");
        } else {*/
        intent.putExtra("orderid", orderid);
        //  }
        iPayFragmentView.getmActivity().startActivity(intent);
    }

    /**
     * 立即收货
     */
    private void getData2(String orderid) {
        RequestParams params = new RequestParams(Constants.URL__SHOUHUO);
        params.addBodyParameter("orderNum", orderid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {


                getDataAndPage(1);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }

    @Override
    public void onDestroy() {

        dialog = null;


        cancelOrderInterface = null;

    }

    @Override
    public void onStart(String str) {

        try {
            int s = Integer.parseInt(str);
            if (s == item) {

                getDataAndPage(1);
            }
        } catch (Exception c) {

        }
    }

    @Override
    public void onStart2() {
        getDataAndPage(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件
                //从第一页开始加载

                getDataAndPage(1);
                break;
            case R.id.frame_empty://数据为空的点击事件
                //从第一页开始加载

                getDataAndPage(1);
                break;
        }
    }
}
