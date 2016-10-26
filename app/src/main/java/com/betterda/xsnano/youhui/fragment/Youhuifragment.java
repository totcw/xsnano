package com.betterda.xsnano.youhui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.EweiMaActivity;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.javabean.KaQuan;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.wallet.model.Wallet;
import com.betterda.xsnano.youhui.model.YouHui;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 卡券的fragment
 * Created by Administrator on 2016/5/27.
 */
public class Youhuifragment extends BaseFragment implements View.OnClickListener {
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<YouHui> list;
    private int item;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_all);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_all);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

            getData(Constants.URL__KAQUAN);

    }

    @Override
    public void initData() {
        loadpager_all.setLoadVisable();
        Bundle arguments = getArguments();
        if (null != arguments) {
            item = arguments.getInt("item");
        }

        list = new ArrayList<>();

        rv_all.setLayoutManager(new LinearLayoutManager(getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<YouHui>(getmActivity(), R.layout.item_recyclevew_kaquan, list) {

            @Override
            public void convert(ViewHolder viewHolder, final YouHui wallet) {
                if (wallet != null) {
                    if (item == 0) {
                        viewHolder.setVisible(R.id.linear_kaqua_time, false);
                        viewHolder.setText(R.id.tv_item_hexiao_time, wallet.getTime());
                    } else {
                        viewHolder.setVisible(R.id.linear_kaqua_time, false);
                    }

                    viewHolder.setText(R.id.tv_tiem_hexiao_kahao, wallet.getName());
                    viewHolder.setOnClickListener(R.id.linear_kaquan, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(getmActivity(), EweiMaActivity.class, "bianhao", wallet.getName());
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

    private void getData(String url) {
        RequestParams params = new RequestParams(url);
        String number = CacheUtils.getString(getmActivity(), "number", "");
        params.addBodyParameter("account", number);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (list != null) {
                    list.clear();
                }
                if (item == 0) {
                    List<KaQuan> listKaQuan = GsonParse.getListKaQuan(UtilMethod.getString(s));
                    if (listKaQuan != null) {
                        for (KaQuan kaQuan : listKaQuan) {
                            if (kaQuan != null) {
                                YouHui youHui = new YouHui();
                                youHui.setName(kaQuan.getCardStockNo());
                                youHui.setTime(kaQuan.getEndDate());
                                if (list != null)
                                    list.add(youHui);
                            }
                        }
                    }
                } else {
                    List<String> listString = GsonParse.getListString(UtilMethod.getString(s));
                    if (listString != null) {
                        for (String kaQuan : listString) {
                            if (kaQuan != null) {
                                YouHui youHui = new YouHui();
                                youHui.setName(kaQuan);
                                if (list != null)
                                    list.add(youHui);
                            }
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


                    getData(Constants.URL__KAQUAN);


                break;
            case R.id.frame_empty://数据为空的点击事件

                    getData(Constants.URL__KAQUAN);

                break;
        }
    }


}
