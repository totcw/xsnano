package com.betterda.xsnano.bus.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.bus.adapter.BusAdapter;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.bus.view.IBusView;
import com.betterda.xsnano.dialog.DeleteDialog;
import com.betterda.xsnano.goods.GoodsDetail;
import com.betterda.xsnano.home.HomeActivity;
import com.betterda.xsnano.javabean.BusDetails;
import com.betterda.xsnano.javabean.BusShopping;
import com.betterda.xsnano.order.ComfirmOrderActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.GsonTools;
import com.betterda.xsnano.util.NetworkUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.ShapeLoadingDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class IBusPresenterImpl implements IBusPresenter, View.OnClickListener {
    private IBusView busView;
    private BusAdapter adapter;
    private List<Bus> list;
    private boolean isEdit;//是否是编辑状态
    private boolean isCheck = true;//是否全选
    private boolean isAll = true;//recycleview中是否全部都选中了
    private boolean isHas;//是否有选中的
    private ShapeLoadingDialog dialog;
    private DeleteDialog deleteDialog;
    private LoadingPager loadpager_bus;

    public IBusPresenterImpl(IBusView busView) {
        this.busView = busView;
    }

    @Override
    public void start() {
        loadpager_bus = busView.getLoadPager();
        //默认选中
        busView.getImageViewAll().setSelected(true);
        busView.getImageViewJiesuan().setSelected(true);
        list = new ArrayList<>();

        RecyclerView recycleView = busView.getRecycleView();
        recycleView.setLayoutManager(new LinearLayoutManager(busView.getContext()));
        adapter = new BusAdapter(this, busView.getContext());
        recycleView.setAdapter(adapter);

        getDataQuery();
        if (busView != null && busView.getLoadPager() != null) {
            busView.getLoadPager().setonEmptyClickListener(this);
            busView.getLoadPager().setonErrorClickListener(this);
        }
    }

    /**
     * 查询购物车
     */
    private void getDataQuery() {
        busView.getLoadPager().setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_BUS_GET);
        String number = CacheUtils.getString(busView.getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println(UtilMethod.getString(s));
                if (list != null) {
                    list.clear();
                }
                BusShopping busShopping = GsonParse.getObject(UtilMethod.getString(s), BusShopping.class);
                if (busShopping != null) {
                    List<BusDetails> details = busShopping.getDetails();
                    if (details != null) {
                        for (int i = 0; i < details.size(); i++) {
                            BusDetails busDetails = details.get(i);
                            if (busDetails != null) {
                                Bus bus = new Bus();
                                bus.setName(busDetails.getProduct_name());
                                bus.setMoney(busDetails.getSale_price());
                                bus.setAmount(busDetails.getCount());
                                bus.setId(busDetails.getProduct_id());
                                bus.setUrl(UtilMethod.url(busDetails.getPicture()));
                                bus.setGoldCount(busDetails.getPay_golden());
                                bus.setShopId(busDetails.getShop_id());
                                if (list != null) {
                                    list.add(bus);
                                }
                            }


                        }

                    }

                }

                if (list != null) {

                    if (list.size() == 0) {

                        if (loadpager_bus != null) {
                            //设置数据为空时的页面
                            loadpager_bus.setEmptyVisable();
                            loadpager_bus.setEmptyBackground(R.mipmap.bus);
                            loadpager_bus.setEmptyText("您的购物车中还没有商品,去首页逛逛");
                            loadpager_bus.setonEmptyClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != busView && null != busView.getmActivity()) {
                                        UtilMethod.startIntent(busView.getmActivity(), HomeActivity.class);
                                    }

                                }
                            });
                        }
                    } else {
                        if (loadpager_bus != null) {
                            loadpager_bus.hide();
                        }
                    }
                } else {
                    if (loadpager_bus != null) {
                        loadpager_bus.setEmptyVisable();
                    }
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                //更新合计的ui
                if (busView.getTextViewSum() != null) {
                    busView.getTextViewSum().setText("￥ " + addUp());
                }

                if (busView != null) {
                    if (busView.getRelative() != null) {
                        busView.getRelative().setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                if (loadpager_bus != null) {
                    loadpager_bus.setErrorVisable();
                }

            }
        });
    }


    @Override
    public List<Bus> getList() {

        return list;
    }

    @Override
    public void onBindViewHolder(final BusAdapter.BusHolder holder, int position) {
        if (list != null) {
            final Bus bus = list.get(position);
            if (null != bus) {
                holder.tv_name.setText(bus.getName());
                holder.tv_amount.setText("" + bus.getAmount());
                holder.tv_price.setText("￥ " + bus.getMoney());
                if (!TextUtils.isEmpty(bus.getUrl())) {
                    holder.sv.setImageURI(Uri.parse(bus.getUrl()));
                }

                holder.iv.setSelected(bus.isChosed());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(busView.getmActivity(), GoodsDetail.class);
                        ((MyApplication) (busView.getmActivity().getApplication())).setProductid(bus.getId());
                        ((MyApplication) (busView.getmActivity().getApplication())).setShopid(bus.getShopId());
                       /* intent.putExtra("id", bus.getId());
                        intent.putExtra("shopid", bus.getShopId());*/
                        busView.getmActivity().startActivity(intent);
                    }
                });

                holder.relative_item_bus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean ischosed = !bus.isChosed();
                        bus.setIsChosed(ischosed);
                        adapter.notifyDataSetChanged();
                        //更新合计的ui
                        if (busView.getTextViewSum() != null) {
                            busView.getTextViewSum().setText("￥ " + addUp());
                        }
                    }
                });

                holder.iv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(bus, true);

                    }
                });

                holder.iv_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(bus, false);
                    }
                });

                isAll = true;
                //遍历集合只要有一个没有选中,就不是全部选中了
                for (Bus bus1 : list) {
                    if (!bus1.isChosed()) {
                        isAll = false;
                        break;
                    }
                }
                //更新全部选中的状态和UI
                if (isAll) {

                    isCheck = true;
                    change(isCheck);
                } else {
                    isCheck = false;
                    change(isCheck);
                }
                isHas = false;
                //遍历集合中是否有一个是选中状态
                for (Bus bus1 : list) {
                    if (bus1.isChosed()) {
                        isHas = true;
                        break;
                    }
                }
                if (isHas) {
                    change2(isHas);
                } else {
                    change2(isHas);
                }


            }


        }

    }


    @Override
    public void edit() {
        isEdit = !isEdit;
        if (isEdit) {
            busView.getNormalBar().setActionText("完成");
            isCheck = false;
        } else {
            isCheck = true;
            busView.getNormalBar().setActionText("编辑");
        }
        //改变状态
        change(isCheck);
        change2(isHas);
        //更新recycleview的状态
        if (list != null) {
            for (Bus bus : list) {
                bus.setIsChosed(isCheck);
            }

            adapter.notifyDataSetChanged();
        }
        //更新合计的ui
        if (busView.getTextViewSum() != null) {
            busView.getTextViewSum().setText("￥ " + addUp());
        }

        //显示结算还删除
        if (isEdit) {
            busView.gettextViewHeji().setVisibility(View.INVISIBLE);
            busView.getImageViewDelete().setVisibility(View.VISIBLE);
            busView.getImageViewJiesuan().setVisibility(View.INVISIBLE);
            busView.getTextViewSum().setVisibility(View.INVISIBLE);
        } else {
            busView.getImageViewJiesuan().setVisibility(View.VISIBLE);
            busView.getImageViewDelete().setVisibility(View.INVISIBLE);
            busView.getTextViewSum().setVisibility(View.VISIBLE);
            busView.gettextViewHeji().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void check() {
        isCheck = !isCheck;
        isAll = isCheck;
        isHas = isCheck;
        change(isCheck);
        change2(isCheck);
        //更新recycleview的状态
        if (list != null) {
            for (Bus bus : list) {
                bus.setIsChosed(isCheck);
            }

            adapter.notifyDataSetChanged();
        }

        //更新合计的ui
        if (busView.getTextViewSum() != null) {
            busView.getTextViewSum().setText("￥ " + addUp());
        }
    }

    @Override
    public void delete() {

        if (!busView.getImageViewDelete().isSelected()) {
            return;
        }

        deleteDialog = new DeleteDialog(busView.getContext(), new DeleteDialog.onConfirmListener() {
            @Override
            public void comfirm() {
                //存放删除的商品id
                List<String> productList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Bus bus = list.get(i);
                    if (bus != null) {
                        if (bus.isChosed()) {
                            productList.add(bus.getId());
                        }
                    }
                }

                RequestParams params = new RequestParams(Constants.URL_BUS_DELETE);
                String number = CacheUtils.getString(busView.getmActivity(), "number", "");
                params.addBodyParameter("account", number);
                String productId = GsonTools.getJsonListString(productList);
                params.addBodyParameter("productId", productId);
                if (null == dialog) {
                    dialog = new ShapeLoadingDialog(busView.getmActivity());
                }
                if (!busView.getmActivity().isFinishing()) {

                    dialog.show();
                }
                GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
                    @Override
                    public void onSuccess(String s) {

                        if (!busView.getmActivity().isFinishing()) {

                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                            getDataQuery();
                       /* if (list != null) {
                            for (int i = list.size() - 1; i >= 0; i--) {
                                Bus bus = list.get(i);
                                if (null != bus) {
                                    if (bus.isChosed()) {
                                        list.remove(i);
                                    }
                                }
                            }
                            if (null != adapter) {
                                adapter.notifyDataSetChanged();
                            }
                        }*/
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        if (!busView.getmActivity().isFinishing()) {

                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    }
                });


            }
        });

        if (!busView.getmActivity().isFinishing()) {

            deleteDialog.show();
        }

    }

    @Override
    public void jiesuan() {
        if (!busView.getImageViewJiesuan().isSelected()) {
            return;
        }

        if (list != null && ((MyApplication) busView.getmActivity().getApplication()).getBusList() != null) {
            ((MyApplication) busView.getmActivity().getApplication()).getBusList().clear();

            for (Bus bus : list) {
                if (bus != null) {
                    //如果选中就添加到结算的列表中
                    if (bus.isChosed()) {
                        ((MyApplication) busView.getmActivity().getApplication()).getBusList().add(bus);
                    }
                }
            }


        }

        UtilMethod.startIntent(busView.getmActivity(), ComfirmOrderActivity.class);
        busView.getmActivity().finish();

    }

    @Override
    public void onDestroy() {
        if (dialog != null) {
            dialog = null;
        }

        if (deleteDialog != null) {
            deleteDialog = null;
        }
    }

    public void change(boolean change) {
        busView.getImageViewAll().setSelected(change);

    }

    /**
     * 改变结算和删除的状态
     *
     * @param change2
     */
    public void change2(boolean change2) {
        busView.getImageViewJiesuan().setSelected(change2);
        busView.getImageViewDelete().setSelected(change2);
    }

    public void queryData() {

    }

    /**
     * 添加购物车到服务器
     */
    public void addData(final Bus bus, boolean isAdd) {
        if (bus != null) {
            int amount = bus.getAmount();
            if (isAdd) {
                amount++;
            } else {
                amount--;
                if (amount < 1) {
                    amount = 1;
                    bus.setAmount(amount);
                    if (null != adapter) {
                        adapter.notifyDataSetChanged();
                    }
                    if (dialog != null) {

                        dialog.dismiss();
                    }
                    UtilMethod.Toast(busView.getContext(), "不能在少了");
                    return;
                }
            }
            RequestParams params = new RequestParams(Constants.URL_BUS_UPDATE);
            final int finalAmount = amount;
            String number = CacheUtils.getString(busView.getmActivity(), "number", "");
            params.addBodyParameter("account", number);
            params.addBodyParameter("productId",bus.getId());
            params.addBodyParameter("count", finalAmount + "");

            GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
                @Override
                public void onSuccess(String s) {

                    if (bus != null) {

                        bus.setAmount(finalAmount);
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    //更新合计的ui
                    if (busView.getTextViewSum() != null) {
                        busView.getTextViewSum().setText("￥ " + addUp());
                    }
                    if (dialog != null) {

                        dialog.dismiss();
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                    if (dialog != null) {
                        dialog.dismiss();
                    }

                }
            });
        }

    }


    /**
     * 购物车更新
     *
     * @param bus
     * @param isAdd
     */
    public void getData(Bus bus, boolean isAdd) {
        if (NetworkUtils.isNetAvailable(busView.getContext())) {
            if (null == dialog) {
                dialog = new ShapeLoadingDialog(busView.getmActivity());
            }
            if (!busView.getmActivity().isFinishing()) {

                dialog.show();
            }
            addData(bus, isAdd);
        } else {
            UtilMethod.Toast(busView.getContext(), "网络不给力");
        }
    }

    /**
     * 合计
     */
    public float addUp() {
        float total = 0;
        if (list != null) {
            for (Bus bus : list) {
                if (bus.isChosed()) {
                    int amount = bus.getAmount();
                    float money = bus.getMoney();
                    float sum = amount * money;
                    total += sum;
                }
            }
        }
        return total;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件

                getDataQuery();

                break;
            case R.id.frame_empty://数据为空的点击事件
                getDataQuery();
                break;
        }
    }
}
