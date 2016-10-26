package com.betterda.xsnano.wallet.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.javabean.WalletList;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.wallet.model.Wallet;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部的fragment
 * Created by Administrator on 2016/5/27.
 */
public class Allfragment3 extends BaseFragment implements View.OnClickListener {
    private LoadingPager loadpager_all;
    private RecyclerView rv_all;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Wallet> list;
    private int  item;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_all, null);
        loadpager_all = (LoadingPager) view.findViewById(R.id.loadpager_fragment_all);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_fragment_all);
        return view;
    }

    @Override
    public void initData() {

        list = new ArrayList<>();
        Bundle arguments = getArguments();
        if (null != arguments) {
            item = arguments.getInt("item");
        }
        rv_all.setLayoutManager(new LinearLayoutManager(getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Wallet>(getmActivity(), R.layout.item_recycleview_wallet, list) {

            @Override
            public void convert(ViewHolder viewHolder,  Wallet wallet) {
                if (wallet != null) {
                    viewHolder.setText(R.id.tv_item_wallet_time, wallet.getTime());
                    viewHolder.setText(R.id.tv_item_wallet_name, wallet.getExtFiled());
                   // if ("金币".equals(wallet.getType())) {
                        viewHolder.setText(R.id.tv_item_wallet_type, "购物送金币");
                        viewHolder.setBackgroundRes(R.id.iv_item_wallet_jinbi, R.mipmap.myjinbi);
                  /*  } else {
                        viewHolder.setText(R.id.tv_item_wallet_type, "购物送银币");
                        viewHolder.setBackgroundRes(R.id.iv_item_wallet_jinbi, R.mipmap.myyinbi);
                    }*/

                   // if (wallet.isAdd()) {
                        viewHolder.setText(R.id.tv_item_wallet_money, "" + wallet.getMoney());
                        viewHolder.setTextColor(R.id.tv_item_wallet_money, 0xFFf75555);
                   /* } else {
                        viewHolder.setText(R.id.tv_item_wallet_money, "-" + wallet.getMoney());
                        viewHolder.setTextColor(R.id.tv_item_wallet_money, 0xFF01d227);
                    }*/

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
        getData("20");
    }

    private void getData(final String queryType) {
        loadpager_all.setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL__WALLET_JILU);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(),"number",""));
        params.addBodyParameter("queryType", queryType);

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                System.out.println("我的钱包"+queryType+UtilMethod.getString(s));
                if (list != null) {
                    list.clear();
                }
                List<WalletList> listWallet = GsonParse.getListWallet(UtilMethod.getString(s));
                if (listWallet != null) {
                    for (WalletList walletList : listWallet) {
                        if (walletList != null) {
                            Wallet wallet = new Wallet();
                            wallet.setMoney( walletList.getGold());
                            wallet.setTime(walletList.getCreate_date());
                            wallet.setName(walletList.getProduct_id());
                            wallet.setType(walletList.getStatus());
                            wallet.setExtFiled(walletList.getExt_filed());
                            if (list != null) {
                                list.add(wallet);
                            }
                        }
                    }
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

                if (rv_all != null) {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    rv_all.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("金币记录错误:"+throwable);
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
                    getData("20");
                break;
            case R.id.frame_empty://数据为空的点击事件
                    getData("20");
                break;
        }
    }
}
