package com.betterda.xsnano.sale.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.sale.model.SaleGoods;
import com.betterda.xsnano.sale.view.ISaleGoodsView;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class ISaleGoodsPresenterImpl implements ISaleGoodsPresenter {
    private ISaleGoodsView iSaleGoodsView;
    private List<SaleGoods>list;
    private RecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private Thread thread;
    private boolean isEnd; //控制计时线程的结束
    public ISaleGoodsPresenterImpl(ISaleGoodsView iSaleGoodsView) {
        this.iSaleGoodsView = iSaleGoodsView;
    }

    @Override
    public void start() {
        //设置标题
        final Intent intent = iSaleGoodsView.getmActivity().getIntent();
        String name = intent.getStringExtra("name");
        iSaleGoodsView.getTopBar().setTitle(name);

        list = new ArrayList<>();
        recyclerView = iSaleGoodsView.getRecycleView();
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<SaleGoods>(iSaleGoodsView.getContext(), R.layout.item_recycleview_salegoods,list) {


            @Override
            public void convert(ViewHolder viewHolder, final SaleGoods saleGoods) {

                if (null != saleGoods) {
                    if (!TextUtils.isEmpty(saleGoods.getUrl())) {
                        SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.sv_item_salegoods);
                        if (null != simpleDraweeView) {
                            simpleDraweeView.setImageURI(Uri.parse(saleGoods.getUrl()));
                        }
                    }
                    viewHolder.setText(R.id.tv_item_salegoods_name, saleGoods.getName());
                    viewHolder.setText((R.id.tv_item_salegoods_price), "￥ "+saleGoods.getPrice());
                    viewHolder.setOnClickListener(R.id.iv_item_salegoods_buy, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(iSaleGoodsView.getContext(), PayActivity.class);
                            intent.putExtra("money", saleGoods.getPrice());
                            intent.putExtra("name", saleGoods.getName());
                            intent.putExtra("money3", 0);
                            iSaleGoodsView.getmActivity().startActivity(intent);
                        }
                    });
                }


            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(iSaleGoodsView.getContext(),2));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams("http://www.baidu.com");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                for (int i = 0; i < 10; i++) {
                    SaleGoods saleGoods = new SaleGoods();
                    list.add(saleGoods);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }




}
