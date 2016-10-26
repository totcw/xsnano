package com.betterda.xsnano.youhui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.EweiMaActivity;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.javabean.KaQuan;
import com.betterda.xsnano.javabean.ZiTiMa;
import com.betterda.xsnano.orderall.OrderDetailActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.youhui.model.YouHui;
import com.betterda.xsnano.youhui.model.ZiTi;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 自提码的fragment
 * Created by Administrator on 2016/5/27.
 */
public class zitifragment extends BaseFragment implements View.OnClickListener {
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<ZiTi> list;


    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_all);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_all);
        return view;
    }

    @Override
    public void initData() {
        loadpager_all.setLoadVisable();

        list = new ArrayList<>();

        rv_all.setLayoutManager(new LinearLayoutManager(getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<ZiTi>(getmActivity(), R.layout.item_recyclevew_kaquan, list) {

            @Override
            public void convert(ViewHolder viewHolder, final ZiTi wallet) {
                if (wallet != null) {
                        viewHolder.setVisible(R.id.linear_kaqua_time, false);
                        viewHolder.setVisible(R.id.iv_kaquan, false);
                    viewHolder.setText(R.id.tv_tiem_hexiao_kahao, wallet.getNumber());

                    viewHolder.setOnClickListener(R.id.linear_kaquan, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(getmActivity(), OrderDetailActivity.class, "orderid", wallet.getOrderNum());
                        }
                    });
                    viewHolder.setOnClickListener(R.id.iv_kaquan, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(getmActivity(), EweiMaActivity.class, "bianhao", wallet.getNumber());
                        }
                    });


                }
            }
        });
        rv_all.setAdapter(adapter);



        if (loadpager_all != null && loadpager_all != null) {
            loadpager_all.setonEmptyClickListener(this);
            loadpager_all.setonErrorClickListener(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDataAndPage();
    }

    private void getDataAndPage() {
        if (loadpager_all != null) {

        loadpager_all.setLoadVisable();
        }
        getData(Constants.URL__ZITIMA);
    }

    private void getData(final String url) {

        RequestParams params = new RequestParams(url);
        String number = CacheUtils.getString(getmActivity(), "number", "");
        params.addBodyParameter("account",number);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (list != null) {
                    list.clear();
                }
                List<ZiTiMa> listZiTiMa = GsonParse.getListZiTiMa(UtilMethod.getString(s));
                if (null != listZiTiMa) {
                    for (ZiTiMa ziTiMa : listZiTiMa) {
                        ZiTi ziTi = new ZiTi();
                        ziTi.setNumber(ziTiMa.getBarCode());
                        ziTi.setOrderNum(ziTiMa.getOrderNum());
                        if (null != list) {

                        list.add(ziTi);
                        }
                    }
                }


                if (adapter != null) {

                    adapter.notifyDataSetChanged();
                }

                if (loadpager_all != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadpager_all.setEmptyVisable();
                        } else {
                            loadpager_all.hide();
                        }
                    } else {
                        loadpager_all.setEmptyVisable();
                    }
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadpager_all != null) {
                    loadpager_all.setErrorVisable();


                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件


                    getDataAndPage();


                break;
            case R.id.frame_empty://数据为空的点击事件

                    getDataAndPage();

                break;
        }
    }
}
