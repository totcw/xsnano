package com.betterda.xsnano.orderall;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.javabean.OrderList;
import com.betterda.xsnano.orderall.model.OrderAll;
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
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 售后
 * Created by Administrator on 2016/5/30.
 */
public class AfterSaleActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_aftersale;
    private LoadingPager loadpager_aftersale;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<OrderAll> list;
    // private float price;
    private int page = 1;
    private String rows = "10";
    private String refund_status;  //20等待卖家同意退款   10等待退货 30退款成功

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_aftersale);
        topBar = (NormalTopBar) findViewById(R.id.topbar_aftersale);
        rv_aftersale = (RecyclerView) findViewById(R.id.rv_aftersale);
        loadpager_aftersale = (LoadingPager) findViewById(R.id.loadpager_aftersale);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        super.init();
        topBar.setTitle("售后");
        loadpager_aftersale.setLoadVisable();
        list = new ArrayList<>();
        rv_aftersale.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<OrderAll>(this, R.layout.item_recycleview_order, list) {

            @Override
            public void convert(ViewHolder viewHolder, OrderAll orderAll) {
                if (null != orderAll) {

                    viewHolder.setText(R.id.tv_item_orderall_time, orderAll.getTime());
                    String refund_status = orderAll.getRefund_status();
                    if (!TextUtils.isEmpty(refund_status)) {
                        if ("10".equals(refund_status)) {
                            viewHolder.setText(R.id.tv_item_orderall_type, "等待退货");
                        } else if ("20".equals(refund_status)) {
                            viewHolder.setText(R.id.tv_item_orderall_type, "等待卖家同意退款");
                        } else if ("30".equals(refund_status)) {
                            viewHolder.setText(R.id.tv_item_orderall_type, "退款成功");
                        }

                    }


                    List<Bus> busList = orderAll.getList();
                    if (null != busList) {

                        viewHolder.setText(R.id.tv_item_orderall_amount2, "共" + UtilMethod.addAmount(busList) + "件商品");
                        viewHolder.setText(R.id.tv_item_orderall_money, "￥" + orderAll.getAmount());
                        viewHolder.setText(R.id.tv_item_orderall_money2, "退款金额:");
                        RecyclerView rv = viewHolder.getView(R.id.rv_order_item);
                        rv.setLayoutManager(new LinearLayoutManager(AfterSaleActivity.this));
                        rv.setAdapter(new CommonAdapter<Bus>(AfterSaleActivity.this, R.layout.item_recycleview_comfirmorder, busList) {

                            @Override
                            public void convert(ViewHolder viewHolder, Bus bus) {
                                if (bus != null) {
                                    viewHolder.setText(R.id.tv_item_order_name, bus.getName());
                                    viewHolder.setText(R.id.tv_item_order_price, "￥" + bus.getMoney());
                                    viewHolder.setText(R.id.tv_item_order_amount, "X " + bus.getAmount());
                                    if (!TextUtils.isEmpty(bus.getUrl())) {
                                        SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_order);
                                        if (simpleDraweeView != null) {
                                            simpleDraweeView.setImageURI(Uri.parse(bus.getUrl()));
                                        }

                                    }

                                }
                             /*   viewHolder.setOnClickListener(R.id.linear_comfirmorder2, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        UtilMethod.Toast(AfterSaleActivity.this, "订单详情");
                                    }
                                });*/

                            }
                        });

                    }


                }
            }
        });
        rv_aftersale.setAdapter(adapter);
        rv_aftersale.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(rv_aftersale)) {

                    RecyclerViewStateUtils.setFooterViewState(getmActivity(), rv_aftersale, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);

                    page++;
                    getData();
                }
            }
        });

        if (loadpager_aftersale != null) {
            loadpager_aftersale.setonEmptyClickListener(this);
            loadpager_aftersale.setonErrorClickListener(this);
        }
        getDataAndPage(1);

    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL__ORDER_TUIKUAN_GET);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                if (list != null) {
                    if (page == 1) {
                        list.clear();
                    }
                }
                List<OrderList> listOrderList = GsonParse.getListOrderList(UtilMethod.getString(s));
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
                            orderAll.setRefund_status(order.getRefund_status());
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
                RecyclerViewStateUtils.setLoad(listOrderList, rv_aftersale, getmActivity());

                if (loadpager_aftersale != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadpager_aftersale.setEmptyVisable();
                        } else {
                            loadpager_aftersale.hide();
                        }
                    } else {
                        loadpager_aftersale.setEmptyVisable();
                    }
                }


                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                if (rv_aftersale != null) {
                    rv_aftersale.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (loadpager_aftersale != null) {
                        loadpager_aftersale.setErrorVisable();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(getmActivity(), rv_aftersale,
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
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

    private void getDataAndPage(int i) {
        page = i;
        if (loadpager_aftersale != null) {
            loadpager_aftersale.setLoadVisable();
        }
        getData();
    }
}
