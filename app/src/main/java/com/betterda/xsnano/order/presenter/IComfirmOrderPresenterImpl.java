package com.betterda.xsnano.order.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.IccOpenLogicalChannelResponse;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.address.AddressActivity;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.comment.presenter.ICommentPresenter;
import com.betterda.xsnano.dialog.CallDialog;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.OrderComfirm;
import com.betterda.xsnano.javabean.Wallet;
import com.betterda.xsnano.order.adapter.ComfirmOrderAdapter;
import com.betterda.xsnano.order.view.IComfirmOrderView;
import com.betterda.xsnano.orderall.OrderActivity;
import com.betterda.xsnano.orderall.model.OrderAll;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.GsonTools;
import com.betterda.xsnano.util.UUIDUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by Administrator on 2016/5/19.
 */
public class IComfirmOrderPresenterImpl implements IComfirmOrderPresenter {
    private IComfirmOrderView comfirmOrderView;
    private List<Bus> list;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private String name, address, number;
    private int userGoldCount; //用户的金币个数
    private String writeGoldAmount = "0";//用户输入的金币个数
    private HashSet<String> hashSet; //根据店铺id区分店铺
    private RecyclerView recycleView2;
    private List<OrderComfirm> orderComfirmList; //订单的list
    private ShapeLoadingDialog dialog;
    private boolean isKuaiDi;//是否是快递, 所有订单只要有一个快递就是快递

    public IComfirmOrderPresenterImpl(IComfirmOrderView comfirmOrderView) {
        this.comfirmOrderView = comfirmOrderView;
    }

    @Override
    public void start() {
        list = new ArrayList<>();
        orderComfirmList = new ArrayList<>();

        //获取商品
        if (((MyApplication) comfirmOrderView.getmActivity().getApplication()).getBusList() != null) {
            list.addAll(((MyApplication) comfirmOrderView.getmActivity().getApplication()).getBusList());
            //将application中的容器情况避免内存泄漏
            ((MyApplication) comfirmOrderView.getmActivity().getApplication()).getBusList().clear();
        }

        getShopId();
        addOrderList();
        getData();


    }

    /**
     * 初始化订单的容器
     */
    private void addOrderList() {
        if (hashSet != null) {
            //按店铺生成订单
            for (String shopid : hashSet) {
                if (!TextUtils.isEmpty(shopid)) {
                    List<Bus> busList = new ArrayList<>();
                    for (Bus bus : list) {
                        //将同一个店铺的商品放到一起
                        if (bus != null) {
                            if (shopid.equals(bus.getShopId())) {
                                busList.add(bus);
                            }
                        }
                    }
                    OrderComfirm orderComfirm = getOrderComfirm(busList);
                    orderComfirmList.add(orderComfirm);
                }
            }
        }
    }

    /**
     * 获取用户的金币个数
     */
    private void getData() {
        RequestParams params = new RequestParams(Constants.URL__WALLET);
        params.addBodyParameter("account", CacheUtils.getString(comfirmOrderView.getmActivity(), "number", ""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                Wallet object = GsonParse.getObject(UtilMethod.getString(s), Wallet.class);
                if (null != object) {
                    userGoldCount = object.getGolden();
                }
                if (comfirmOrderView.getTextViewPay() != null) {
                    comfirmOrderView.getTextViewPay().setText("￥ " + addOrderUp(orderComfirmList));
                }

                comfirmOrderView.getLoadPager().hide();
                if (comfirmOrderView != null && comfirmOrderView.getRecycleView2() != null) {
                    recycleView2 = comfirmOrderView.getRecycleView2();
                    recycleView2.setLayoutManager(new LinearLayoutManager(comfirmOrderView.getmActivity()));
                    recycleView2.setAdapter(new CommonAdapter<OrderComfirm>(comfirmOrderView.getmActivity(), R.layout.item_recyclevie_ordercomfirm2, orderComfirmList) {

                        @Override
                        public void convert(final ViewHolder viewHolder, final OrderComfirm orderComfirm) {
                            //商品列表
                            RecyclerView recyclerView = viewHolder.getView(R.id.rv_order);
                            recyclerView.setLayoutManager(new LinearLayoutManager(comfirmOrderView.getContext()));
                            adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Bus>(comfirmOrderView.getmActivity(), R.layout.item_recycleview_comfirmorder, orderComfirm.getBusList()) {

                                @Override
                                public void convert(ViewHolder viewHolder, Bus bus) {
                                    SimpleDraweeView view = viewHolder.getView(R.id.sv_item_order);
                                    if (view != null) {
                                        view.setImageURI(Uri.parse(bus.getUrl()));
                                    }
                                    viewHolder.setText(R.id.tv_item_order_name, bus.getName());
                                    viewHolder.setText(R.id.tv_item_order_amount, "X " + bus.getAmount());
                                    viewHolder.setText(R.id.tv_item_order_price, "￥ " + bus.getMoney());
                                }
                            });

                            recyclerView.setAdapter(adapter);

                            viewHolder.setText(R.id.tv_order_amnout, "共计" + UtilMethod.addAmount(orderComfirm.getBusList()) + "件商品");
                            viewHolder.setText(R.id.tv_order_heji_money, "￥ " + UtilMethod.addUp(orderComfirm.getBusList()));
                            viewHolder.setText(R.id.tv_confirmorder_goldcount, "可抵消金币个数" + addAmount(orderComfirm.getBusList()));

                            EditText et_goldcount = viewHolder.getView(R.id.et_confirmorder_goldcount);
                            et_goldcount.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    String s1 = s.toString();
                                    if (TextUtils.isEmpty(s1)) {
                                        s1 = "0";
                                    }
                                    try {
                                        int count = Integer.parseInt(s1);
                                        //订单可抵消的金币个数
                                        int amount = addAmount(orderComfirm.getBusList());
                                        if (count > amount) {
                                            UtilMethod.Toast(comfirmOrderView.getmActivity(), "该订单最多只能抵消" + amount + "个金币");
                                            count = amount;
                                        }
                                        if (count > orderComfirm.getMoney()) {
                                            UtilMethod.Toast(comfirmOrderView.getmActivity(), "金币数不能大于金额数");
                                            return;
                                        }
                                        //设置抵消的金币个数
                                        orderComfirm.setGoldCount(count);
                                        //设置订单的金额
                                        orderComfirm.setMoney(UtilMethod.addUp(orderComfirm.getBusList()) - orderComfirm.getGoldCount());
                                        //修改总计
                                        if (comfirmOrderView.getTextViewPay() != null) {
                                            comfirmOrderView.getTextViewPay().setText("￥ " + addOrderUp(orderComfirmList));
                                        }
                                    } catch (Exception e) {

                                    }


                                }
                            });


                            //发票
                            viewHolder.setOnClickListener(R.id.relative_order_fapiao, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final View view = View.inflate(comfirmOrderView.getmActivity(), R.layout.pp_choose_fapiao, null);
                                    TextView tv_shi = (TextView) view.findViewById(R.id.tv_fapiao_shi);
                                    TextView tv_fou = (TextView) view.findViewById(R.id.tv_fapiao_fou);
                                    tv_shi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            viewHolder.setText(R.id.tv_order_fapiao, "是");
                                            orderComfirm.setBill("Y");
                                            comfirmOrderView.closePopup();
                                        }
                                    });
                                    tv_fou.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            viewHolder.setText(R.id.tv_order_fapiao, "否");
                                            orderComfirm.setBill("N");
                                            comfirmOrderView.closePopup();
                                        }
                                    });
                                    comfirmOrderView.setUpPopup(view, null, 1, UtilMethod.getHeight(comfirmOrderView.getmActivity()));
                                }
                            });
                            //配送方式
                            viewHolder.setOnClickListener(R.id.relative_order_youhuiquan, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    View view2 = View.inflate(comfirmOrderView.getmActivity(), R.layout.pp_choose_peisong, null);
                                    TextView tv_ziti = (TextView) view2.findViewById(R.id.tv_peisong_ziti);
                                    TextView tv_kuaidi = (TextView) view2.findViewById(R.id.tv_peisong_kuaidi);
                                    tv_kuaidi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            viewHolder.setText(R.id.tv_order_peisong, "快递");
                                            orderComfirm.setType("20");
                                            comfirmOrderView.closePopup();
                                        }
                                    });
                                    tv_ziti.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            viewHolder.setText(R.id.tv_order_peisong, "自提");
                                            orderComfirm.setType("10");
                                            comfirmOrderView.closePopup();
                                        }
                                    });
                                    comfirmOrderView.setUpPopup(view2, null, 1, UtilMethod.getHeight(comfirmOrderView.getmActivity()));

                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (comfirmOrderView.getLoadPager() != null) {
                    comfirmOrderView.getLoadPager().setErrorVisable();
                }
            }
        });
    }

    /**
     * 计算一个订单可抵消的总的金币个数
     *
     * @return
     */
    private int addAmount(List<Bus> list) {
        int count = 0;
        if (list != null) {
            for (Bus bus : list) {
                if (bus != null) {
                    //数量
                    int amount = bus.getAmount();
                    //抵消的金币个数
                    int goldCount = bus.getGoldCount();
                    int count2 = amount * goldCount;
                    count += count2;
                }
            }
        }

        return count;
    }


    @Override
    public void address() {
        Intent intent = new Intent(comfirmOrderView.getContext(), AddressActivity.class);
        comfirmOrderView.getmActivity().startActivityForResult(intent, 0);
        UtilMethod.setOverdepengingIn(comfirmOrderView.getmActivity());
    }

    @Override
    public void pay() {

        isKuaiDi = true; //默认是快递
        if (orderComfirmList != null) {
            for (OrderComfirm all : orderComfirmList) {
                if (all != null) {
                    if ("20".equals(all.getType())) {
                        isKuaiDi = false;
                        break;
                    }
                }
            }
        }
        if (!isKuaiDi) {
            name = comfirmOrderView.getTextViewShouHuoren().getText().toString().trim();
            number = comfirmOrderView.getTextViewShouHuoNumber().getText().toString().trim();
            address = comfirmOrderView.getTextViewShouHuoAddress().getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                UtilMethod.Toast(comfirmOrderView.getmActivity(), "收货人姓名不能为空");
                return;
            }
            if (TextUtils.isEmpty(number)) {
                UtilMethod.Toast(comfirmOrderView.getmActivity(), "收货人电话不能为空");
                return;
            }
            if (TextUtils.isEmpty(address)) {
                UtilMethod.Toast(comfirmOrderView.getmActivity(), "收货人地址不能为空");
                return;
            }
        }

        //计算用户输入的金币个数
        if (orderComfirmList != null) {
            int count = 0;
            for (OrderComfirm orderComfirm : orderComfirmList) {
                count += orderComfirm.getGoldCount();
            }
            writeGoldAmount = String.valueOf(count);
            if (count > userGoldCount) {
                UtilMethod.Toast(comfirmOrderView.getmActivity(), "您只有" + userGoldCount + "个金币");
                return;
            }


        }

        String json = GsonTools.getJsonListObject(orderComfirmList);

        RequestParams requestParams = new RequestParams(Constants.URL__ORDER_CREAE);
        requestParams.addBodyParameter("orders", json);
        if (null != dialog) {

            dialog = UtilMethod.createDialog(comfirmOrderView.getmActivity(), "");
        }
        UtilMethod.showDialog(comfirmOrderView.getmActivity(), dialog);
        comfirmOrderView.getIvPay().setSelected(false);
        GetNetUtil.getData(GetNetUtil.POST, requestParams, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {


                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {
                            if (orderComfirmList != null && orderComfirmList.size() > 0) {
                                if (orderComfirmList.size() == 1) {
                                    //如果只有一个订单就直接去支付
                                    OrderComfirm orderComfirm = orderComfirmList.get(0);
                                    if (orderComfirm != null) {
                                        //支付
                                        Intent intent = new Intent(comfirmOrderView.getmActivity(), PayActivity.class);
                                        intent.putExtra("orderid", orderComfirm.getOrderId());
                                        //付款的金,减去金币个数
                                        intent.putExtra("money", orderComfirm.getMoney());
                                        //运费
                                        try {

                                        float fee = Float.parseFloat(orderComfirm.getFreight());
                                        intent.putExtra("money3",fee);
                                        } catch (Exception e) {

                                        }
                                        comfirmOrderView.getmActivity().startActivity(intent);
                                         comfirmOrderView.getmActivity().finish();
                                        UtilMethod.setOverdepengingIn(comfirmOrderView.getmActivity());
                                    }

                                } else {
                                    //跳到待付款界面
                                    Intent intent = new Intent(comfirmOrderView.getmActivity(), OrderActivity.class);
                                    intent.putExtra("item", 2);
                                    comfirmOrderView.getmActivity().startActivity(intent);
                                    comfirmOrderView.getmActivity().finish();
                                    UtilMethod.setOverdepengingIn(comfirmOrderView.getmActivity());

                                }


                            }
                        } else {
                            UtilMethod.Toast(comfirmOrderView.getmActivity(),"订单生成失败");
                        }
                    }
                });


                comfirmOrderView.getIvPay().setSelected(true);
                UtilMethod.dissmissDialog(comfirmOrderView.getmActivity(), dialog);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                comfirmOrderView.getIvPay().setSelected(true);
                UtilMethod.Toast(comfirmOrderView.getmActivity(), "订单生成失败");
                UtilMethod.dissmissDialog(comfirmOrderView.getmActivity(), dialog);
            }
        });


    }

    /**
     * 计算购物车中有几个店铺的商品
     */
    private void getShopId() {
        if (list != null) {
            hashSet = new HashSet<>();
            for (Bus bus : list) {
                if (bus != null) {
                    if (!TextUtils.isEmpty(bus.getShopId())) {
                        hashSet.add(bus.getShopId());
                    }
                }
            }
        }
    }

    /**
     * 生成订单
     *
     * @param list
     * @return
     */
    @NonNull
    private OrderComfirm getOrderComfirm(List<Bus> list) {
        OrderComfirm orderComfirm = new OrderComfirm();
        if (list != null && list.size() > 0) {
            Bus bus = list.get(0);
            orderComfirm.setShopId(bus.getShopId());
        }

        orderComfirm.setName(name);
        orderComfirm.setOrderId(UUIDUtils.getUUID());
        orderComfirm.setNumber(number);
        orderComfirm.setAddress(address);
        orderComfirm.setAccount(CacheUtils.getString(comfirmOrderView.getmActivity(), "number", ""));
        orderComfirm.setTime(UtilMethod.getCurrdentTime());
        orderComfirm.setType("10");
        orderComfirm.setBill("N");
        orderComfirm.setGoldCount(0);
        orderComfirm.setBusList(list);
        orderComfirm.setFreight(0 + ""); //0表示免费
        orderComfirm.setOrderType("30");
        //减去金币数才是实际支付的金额
        orderComfirm.setMoney(UtilMethod.addUp(list) - orderComfirm.getGoldCount());
        return orderComfirm;
    }

    @Override
    public List<OrderComfirm> getList() {
        return orderComfirmList;
    }

    @Override
    public void onBindViewHolder(ComfirmOrderAdapter.OrderHolder holder, int position) {
        if (null != list) {
            Bus bus = list.get(position);
            if (null != bus) {
                holder.tv_name.setText(bus.getName());
                holder.tv_amount.setText("X " + bus.getAmount());
                holder.tv_price.setText("￥ " + bus.getMoney());
                if (!TextUtils.isEmpty(bus.getUrl())) {
                    holder.sv.setImageURI(Uri.parse(bus.getUrl()));
                }

            }
        }
    }

    @Override
    public void ziti() {
    /*    if (comfirmOrderView != null) {
            if (null != comfirmOrderView.getTextViewPeisong()) {
                peisong = "自提";
                comfirmOrderView.getTextViewPeisong().setText(peisong);
            }

        }*/
    }

    @Override
    public void kuaidi() {
   /*     if (comfirmOrderView != null) {
            if (null != comfirmOrderView.getTextViewPeisong()) {
                peisong = "快递";
                comfirmOrderView.getTextViewPeisong().setText(peisong);
            }

        }*/
    }

    @Override
    public void shi() {
   /*     if (comfirmOrderView != null) {
            if (null != comfirmOrderView.getTextViewFapiao()) {
                fapiao = "是";
                comfirmOrderView.getTextViewFapiao().setText(fapiao);
            }

        }*/
    }

    @Override
    public void fou() {
  /*      if (comfirmOrderView != null) {
            if (null != comfirmOrderView.getTextViewFapiao()) {
                fapiao = "否";
                comfirmOrderView.getTextViewFapiao().setText(fapiao);
            }

        }*/
    }


    @Override
    public void setName(String name) {
        this.name = name;
        if (orderComfirmList != null) {
            for (OrderComfirm orderComfirm : orderComfirmList) {
                if (orderComfirm != null) {
                    orderComfirm.setName(name);
                }
            }
        }

    }

    @Override
    public void setNumber(String number) {
        this.number = number;
        if (orderComfirmList != null) {
            for (OrderComfirm orderComfirm : orderComfirmList) {
                if (orderComfirm != null) {
                    orderComfirm.setNumber(number);
                }
            }
        }
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
        if (orderComfirmList != null) {
            for (OrderComfirm orderComfirm : orderComfirmList) {
                if (orderComfirm != null) {
                    orderComfirm.setAddress(address);
                }
            }
        }
    }

    /**
     * 计算所有订单的总支付价格减去金币个数
     *
     * @param list
     * @return
     */
    private float addOrderUp(List<OrderComfirm> list) {
        float count = 0;
        if (list != null) {

            for (OrderComfirm orderComfirm : list) {
                if (orderComfirm != null) {
                    count += orderComfirm.getMoney();
                }
            }
        }
        return count;
    }

}
