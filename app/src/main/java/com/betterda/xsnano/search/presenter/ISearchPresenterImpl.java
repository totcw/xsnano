package com.betterda.xsnano.search.presenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.betterda.xsnano.R;
import com.betterda.xsnano.javabean.StoreList;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.search.adapter.SearchAdapter;
import com.betterda.xsnano.search.view.ISearchView;
import com.betterda.xsnano.shouye.model.Store;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.ConstantsData;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 与搜索交互的presenter
 * Created by Administrator on 2016/5/11.
 */
public class ISearchPresenterImpl implements ISearchPresenter {
    private final HeaderAndFooterRecyclerViewAdapter adapter;
    private ISearchView iSearchView;
    private String keyword;
    private List<Store> list;
    private final RecyclerView recyclerView;
    private String longitude;
    private String dimension;

    public ISearchPresenterImpl(final ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        list = new ArrayList<>();
        recyclerView = iSearchView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iSearchView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Store>(iSearchView.getmActivity(), R.layout.item_xiche, list) {
            @Override
            public void convert(final ViewHolder viewHolder, final Store store) {
                if (null != store) {
                    viewHolder.setText(R.id.tv_item_xiche_address, store.getAddress());
                    viewHolder.setText(R.id.tv_item_xiche_name, store.getName());
                    viewHolder.setText(R.id.tv_item_xiche_type, store.getType());
                    viewHolder.setText(R.id.tv_item_xiche_sum, "月销" + store.getAmount() + "单");
                    viewHolder.setText(R.id.tv_item_xiche_comment, store.getComment() + "条评论");
                    if (!TextUtils.isEmpty(store.getServie()) && !TextUtils.isEmpty(store.getPrice())) {
                        viewHolder.setVisible(R.id.linear_xiche_service, true);
                    } else {
                        viewHolder.setVisible(R.id.linear_xiche_service, false);
                    }
                    viewHolder.setText(R.id.tv_item_xiche_service, store.getServie());
                    viewHolder.setText(R.id.tv_item_xiche_price, "￥ " + store.getPrice());
                    viewHolder.setText(R.id.tv_item_xiche_distance, store.getDistance() + "km");
                    viewHolder.setOnClickListener(R.id.linear_item_xiche, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(iSearchView.getmActivity(), StoreActivity.class,"id",store.getId());
                        }
                    });

                    RatingBar ratingBar = viewHolder.getView(R.id.rb_xiche_comment);
                    if (null != ratingBar) {
                        ratingBar.setRating(store.getRate());

                    }
                }

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public List<Store> getList() {
        return list;
    }

    @Override
    public void setData(SearchAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public void delete() {
        EditText editText = iSearchView.getEditText();
        if (null != editText) {
            editText.setText("");
        }

    }

    @Override
    public void search() {
        LoadingPager loadPager = iSearchView.getLoadPager();
        if (null != loadPager) {
            loadPager.setLoadVisable();
        }
        longitude = CacheUtils.getString(iSearchView.getmActivity(), Constants.Cache.longitude, "0");
        dimension = CacheUtils.getString(iSearchView.getmActivity(), Constants.Cache.dimension, "0");
        start();
    }

    @Override
    public void start() {
        if (iSearchView != null && iSearchView.getEditText() != null) {

            keyword = iSearchView.getEditText().getText().toString().trim();
        }
        if (TextUtils.isEmpty(keyword)) {
            keyword = "空";
        }
        RequestParams params = new RequestParams(Constants.URL__SEARCH);
        params.addBodyParameter("keyword", keyword);
        params.addBodyParameter("longitude", longitude);
        params.addBodyParameter("dimension", dimension);
        //TODO
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {


                if (list != null) {
                        list.clear();
                }
                List<StoreList> listStoreList = GsonParse.getListStoreList(UtilMethod.getString(s));
                if (null != listStoreList) {
                    for (StoreList storeL : listStoreList) {
                        StoreList.ShopInfoBean shopInfo = storeL.getShopInfo();
                        StoreList.ProductBean product = storeL.getProduct();
                        Store store = new Store();
                        if (shopInfo != null) {
                            store.setId(shopInfo.getId());
                            store.setName(shopInfo.getShop_name());
                            store.setDistance(shopInfo.getDistance());
                            try {
                                double services = shopInfo.getServices_score();
                                double proucts = shopInfo.getProducts_score();
                                float v = (float) (services + proucts);
                                store.setRate(v / 2);
                                store.setAmount(Integer.parseInt(storeL.getTotalSellCount()));
                            } catch (Exception e) {

                            }

                            store.setAddress(shopInfo.getRemark() + shopInfo.getAddress());
                            if (!TextUtils.isEmpty(shopInfo.getShop_catalog_id())) {
                                String[] split = shopInfo.getShop_catalog_id().split(",");
                                if (split != null && split.length > 0) {
                                    String type = ConstantsData.getMapString(split[0]);
                                    if (!TextUtils.isEmpty(type)) {

                                        store.setType(type);
                                    }
                                }
                            }
                            store.setUrl(shopInfo.getSmall_picture());
                        }

                        String totalCommentCount = storeL.getTotalCommentCount();
                        try {

                            store.setComment(Integer.parseInt(totalCommentCount));
                        } catch (Exception e) {

                        }


                        if (product != null) {
                            store.setServie(product.getName());
                            store.setPrice(product.getPrice());
                            store.setProductId(product.getId());
                        }

                        if (list != null) {
                            list.add(store);
                        }
                    }
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                LoadingPager loadPager = iSearchView.getLoadPager();
                if (null != loadPager) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadPager.setEmptyVisable();
                        } else {
                            loadPager.hide();
                        }
                    } else {
                        loadPager.setEmptyVisable();
                    }
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                LoadingPager loadPager = iSearchView.getLoadPager();
                if (null != loadPager) {
                    loadPager.setErrorVisable();
                }

            }
        });
    }
}
