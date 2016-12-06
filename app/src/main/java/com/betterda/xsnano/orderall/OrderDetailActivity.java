package com.betterda.xsnano.orderall;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.OrderList;
import com.betterda.xsnano.javabean.OrderListDetail;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mob.tools.log.NLog;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情
 * Created by Administrator on 2016/5/31.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_orderdetail;
    private LoadingPager loadpager_orderdetail;
    private RelativeLayout relative_orderdetail, relative_orderdetail_tuikuan,
            relative_orderdetail_delete, relative_orderdetail_pay;
    private TextView tv_orderdetail_state, tv_orderdetail_price,
            tv_orderdetail_shouhuoren2, tv_orderdetail_address2,
            tv_orderdetail_number, tv_orderdetail_yunfei, tv_orderdetail_heji_money,
            tv_orderdetail_fapiao, tv_orderdetail_peisong, tv_orderdetail_biamhao, tv_orderdetail_time;
    private LinearLayout linear_orderdetail_pay; //最下面的布局
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Bus> list; //商品的容器
    private CallDialog tuikuanDialog, orderDialog;
    private String orderid; //订单号
    private String fee;//运费
    private float amount; //订单金额
    private float fee2;
    private TextView tv_orderdetail_amnout;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_orderdetail);
        topBar = (NormalTopBar) findViewById(R.id.topbar_oderdetail);
        rv_orderdetail = (RecyclerView) findViewById(R.id.rv_orderdetail);
        loadpager_orderdetail = (LoadingPager) findViewById(R.id.loadpager_orderdetail);
        relative_orderdetail = (RelativeLayout) findViewById(R.id.relative_orderdetail);
        relative_orderdetail_tuikuan = (RelativeLayout) findViewById(R.id.relative_orderdetail_tuikuan);
        relative_orderdetail_delete = (RelativeLayout) findViewById(R.id.relative_orderdetail_delete);
        relative_orderdetail_pay = (RelativeLayout) findViewById(R.id.relative_orderdetail_pay);
        tv_orderdetail_state = (TextView) findViewById(R.id.tv_orderdetail_state);
        tv_orderdetail_price = (TextView) findViewById(R.id.tv_orderdetail_price);
        tv_orderdetail_shouhuoren2 = (TextView) findViewById(R.id.tv_orderdetail_shouhuoren2);
        tv_orderdetail_address2 = (TextView) findViewById(R.id.tv_orderdetail_address2);
        tv_orderdetail_number = (TextView) findViewById(R.id.tv_orderdetail_number);
        tv_orderdetail_yunfei = (TextView) findViewById(R.id.tv_orderdetail_yunfei);
        tv_orderdetail_heji_money = (TextView) findViewById(R.id.tv_orderdetail_heji_money);
        tv_orderdetail_fapiao = (TextView) findViewById(R.id.tv_orderdetail_fapiao);
        tv_orderdetail_peisong = (TextView) findViewById(R.id.tv_orderdetail_peisong);
        tv_orderdetail_biamhao = (TextView) findViewById(R.id.tv_orderdetail_biamhao);
        tv_orderdetail_time = (TextView) findViewById(R.id.tv_orderdetail_time);
        tv_orderdetail_amnout = (TextView) findViewById(R.id.tv_orderdetail_amnout);
        linear_orderdetail_pay = (LinearLayout) findViewById(R.id.linear_orderdetail_pay);
    }

    @Override
    public void initListener() {
        super.initListener();

        topBar.setOnBackListener(this);
        relative_orderdetail_delete.setOnClickListener(this);
        relative_orderdetail_pay.setOnClickListener(this);
        relative_orderdetail_tuikuan.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        list = new ArrayList<>();
        topBar.setTitle("订单详情");
        loadpager_orderdetail.setLoadVisable();
        orderid = getIntent().getStringExtra("orderid");
        rv_orderdetail.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Bus>(this, R.layout.item_recycleview_comfirmorder, list) {

            @Override
            public void convert(ViewHolder viewHolder, Bus bus) {
                if (bus != null) {
                    viewHolder.setText(R.id.tv_item_order_name, bus.getName());
                    viewHolder.setText(R.id.tv_item_order_price, "￥ " + bus.getMoney());
                    viewHolder.setText(R.id.tv_item_order_amount, "X" + bus.getAmount());
                    if (!TextUtils.isEmpty(bus.getUrl())) {
                        SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_order);
                        simpleDraweeView.setImageURI(Uri.parse(bus.getUrl()));
                    }
                    if ("sign".equals(bus.getState())) {
                        viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, true);
                        viewHolder.setOnClickListener(R.id.tv_item_comfirmorder_comment, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UtilMethod.Toast(OrderDetailActivity.this, "立即评价");
                            }
                        });
                    } else {
                        viewHolder.setVisible(R.id.tv_item_comfirmorder_comment, false);
                    }

                }
            }
        });
        rv_orderdetail.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL__ORDER_DETAIL);
        params.addBodyParameter("orderNum", orderid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (list != null) {
                    list.clear();
                }

                OrderListDetail orderListDetail = GsonParse.getObject(UtilMethod.getString(s), OrderListDetail.class);
                if (orderListDetail != null) {
                    OrderListDetail.OrderBean order = orderListDetail.getOrder();

                    if (null != order) {
                        //订单号
                        // orderid1 = order.getOrder_num();
                        //订单状态
                        String status = order.getStatus();
                        //订单交易状态
                        String pay_status = order.getPay_status();
                        //订单金额
                        try {
                            amount = Float.parseFloat(order.getAmount());
                        } catch (Exception e) {

                        }

                        //订单时间
                        String create_date = order.getCreate_date();
                        //收货地址
                        String contact_address = order.getContact_address();
                        //收货人电话
                        String contact_phone = order.getContact_phone();
                        //收货人
                        String contact_user = order.getContact_user();

                        //运费
                        fee = order.getFee();
                        try {
                            fee2 = Float.parseFloat(fee);
                        } catch (Exception e
                                ) {

                        }

                        //配送方式
                        String type = order.getCarry();
                        //是否需要发票
                        String isNeedInvoice = order.getIs_need_invoice();
                        //是否售后过
                        String isCustomerService = order.getIs_customer_service();
                        if (tv_orderdetail_fapiao != null && isNeedInvoice != null) {

                            if ("Y".equals(isNeedInvoice)) {
                                tv_orderdetail_fapiao.setText("是");
                            } else {
                                tv_orderdetail_fapiao.setText("否");
                            }
                        }

                        if (tv_orderdetail_peisong != null && type != null) {
                            if ("10".equals(type)) {
                                tv_orderdetail_peisong.setText("自提");
                            } else {
                                tv_orderdetail_peisong.setText("快递");
                            }
                        }
                        if (null != pay_status) {

                            if ("N".equals(pay_status)) {
                                //未付款的状态
                                if (null != relative_orderdetail_delete && null != relative_orderdetail_pay && linear_orderdetail_pay != null) {

                                    relative_orderdetail_delete.setVisibility(View.VISIBLE);
                                    relative_orderdetail_pay.setVisibility(View.VISIBLE);
                                    linear_orderdetail_pay.setVisibility(View.VISIBLE);
                                } else {
                                    relative_orderdetail_delete.setVisibility(View.INVISIBLE);
                                    relative_orderdetail_pay.setVisibility(View.INVISIBLE);
                                    linear_orderdetail_pay.setVisibility(View.INVISIBLE);
                                }
                            } else if ("Y".equals(pay_status)) {

                                //显示售后
                                if (null != linear_orderdetail_pay && isCustomerService != null) {

                                    if ("Y".equals(isCustomerService)) {

                                        relative_orderdetail_tuikuan.setVisibility(View.INVISIBLE);
                                        linear_orderdetail_pay.setVisibility(View.INVISIBLE);
                                    } else {
                                        relative_orderdetail_tuikuan.setVisibility(View.VISIBLE);
                                        linear_orderdetail_pay.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }

                        if (tv_orderdetail_state != null && pay_status != null) {

                            //设置状态
                            if ("N".equals(pay_status)) {
                                tv_orderdetail_state.setText("待付款");
                            } else if ("C".equals(pay_status)) {
                                tv_orderdetail_state.setText("交易关闭");
                            } else {
                                tv_orderdetail_state.setText("已付款");
                            }
                        }

                        if (tv_orderdetail_heji_money != null) {
                            tv_orderdetail_heji_money.setText("￥ "+amount);
                        }

                        if (tv_orderdetail_price != null) {

                            tv_orderdetail_price.setText("订单金额:￥" + amount);
                        }
                        if (tv_orderdetail_shouhuoren2 != null) {


                            tv_orderdetail_shouhuoren2.setText(contact_user);
                        }
                        if (tv_orderdetail_address2 != null) {

                            tv_orderdetail_address2.setText(contact_address);
                        }
                        if (tv_orderdetail_number != null) {

                            tv_orderdetail_number.setText(contact_phone);
                        }
                        if (null != tv_orderdetail_yunfei && fee != null) {

                            if ("0".equals(fee)) {

                                tv_orderdetail_yunfei.setText("免费");
                            } else {
                                tv_orderdetail_yunfei.setText(fee + "元");
                            }
                        }
                        if (tv_orderdetail_time != null) {
                            tv_orderdetail_time.setText(create_date);
                        }
                        if (tv_orderdetail_biamhao != null) {
                            tv_orderdetail_biamhao.setText(orderid);
                        }


                        if (orderListDetail != null) {
                            List<OrderListDetail.DetailsBean> details = orderListDetail.getDetails();
                            if (null != details) {
                                for (int i = 0; i < details.size(); i++) {
                                    OrderListDetail.DetailsBean bean = details.get(i);
                                    if (null != bean) {
                                        Bus bus = new Bus();
                                        bus.setId(bean.getId());
                                        bus.setName(bean.getName());
                                        bus.setUrl(UtilMethod.url(bean.getLittle_picture()));
                                        bus.setAmount(bean.getNumber());
                                        try {
                                            bus.setMoney(Float.parseFloat(bean.getSale_price()));
                                        } catch (Exception e) {

                                        }
                                        if (list != null) {
                                            list.add(bus);
                                        }

                                        if (adapter != null) {
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }


                if (tv_orderdetail_amnout != null) {
                    tv_orderdetail_amnout.setText("共"+UtilMethod.addAmount(list)+"件商品");
                }

                if (loadpager_orderdetail != null) {
                    loadpager_orderdetail.hide();
                }
      /*          if (null != relative_orderdetail) {

                    relative_orderdetail.setVisibility(View.VISIBLE);
                }*/
             /*   if (linear_orderdetail_pay != null) {

                    linear_orderdetail_pay.setVisibility(View.VISIBLE);
                }*/

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadpager_orderdetail != null) {
                    loadpager_orderdetail.setErrorVisable();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.relative_orderdetail_pay://立即付款

                getPay(amount,fee2,orderid);
                break;
            case R.id.relative_orderdetail_delete: //取消订单
                if (orderDialog == null) {
                    orderDialog = new CallDialog(OrderDetailActivity.this, new CallDialog.onConfirmListener() {
                        @Override
                        public void comfirm() {
                            getData2();
                        }

                        @Override
                        public void cancel() {

                        }
                    }, "确定要取消订单吗", "确定");
                }
                if (!this.isFinishing()) {
                    orderDialog.show();
                }
                break;
            case R.id.relative_orderdetail_tuikuan: //申请售后


                UtilMethod.startIntentParams(getmActivity(),AddResponseActivity.class,"orderid",orderid);
                break;
        }
    }

    /**
     * 申请售后
     */
    private void getData3() {
        RequestParams params = new RequestParams(Constants.URL__ORDER_TUIKUAN);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        params.addBodyParameter("orderNum", orderid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        UtilMethod.Toast(getmActivity(), resultMsg);
                        OrderDetailActivity.this.finish();
                        UtilMethod.setOverdepengingOut(getmActivity());

                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(getmActivity(), "申请售后失败");

            }
        });
    }

    /**
     * 取消订单
     */
    private void getData2() {
        RequestParams params = new RequestParams(Constants.URL__ORDER_CANCEL);
        params.addBodyParameter("orderNum", orderid);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        UtilMethod.Toast(getmActivity(), resultMsg);
                        finish();
                        UtilMethod.setOverdepengingOut(getmActivity());
                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(getmActivity(), "取消订单失败");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tuikuanDialog != null) {

            tuikuanDialog = null;
        }

        if (orderDialog != null) {
            orderDialog = null;
        }
    }

    /**
     * 立即付款
     *
     * @param money   金额
     * @param freight 运费
     */
    private void getPay(float money, float freight, String orderid) {

        Intent intent = new Intent(getmActivity(), PayActivity.class);
        intent.putExtra("orderid", orderid);
        intent.putExtra("money", money);
        intent.putExtra("money3", freight);
        getmActivity().startActivity(intent);
        UtilMethod.setOverdepengingIn(getmActivity());
    }
}
