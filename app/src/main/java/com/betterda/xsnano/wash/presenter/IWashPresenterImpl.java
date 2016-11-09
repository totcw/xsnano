package com.betterda.xsnano.wash.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.javabean.Cache;
import com.betterda.xsnano.javabean.StoreList;
import com.betterda.xsnano.pay.PayActivity;
import com.betterda.xsnano.sale.view.ISaleView;
import com.betterda.xsnano.shouye.model.Store;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.ConstantsData;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.TimeTool;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.wash.view.IWashView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/24.
 */
public class IWashPresenterImpl implements IWashPresenter, View.OnClickListener {
    private IWashView iWashView;
    private RecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<Store> list;
    private int color = 0xFF909090;
    private List<String> sortList, pinpaiList, serviceList;
    private int selectSort = -1;
    private int selectsaixuan = -1;
    private int selectservice = -1;
    private int page = 1;
    private String rows = "10";
    private String serviceId;//服务id
    private String sortFiled;//排序id
    private String catalogId;//筛选id
    private String cooperation;//合作项目id

    private String longitude; //经度
    private String dimension;//纬度


    //分类对应的id
    private Map<String, String> fenleiMap;
    //排序对应的id
    private Map<String, String> sortMap;
    //筛选对应的id
    private Map<String, String> shaixuanMap;


    private boolean isShop;//是否是上面8个按钮
    private RecyclerView recyclyView2;
    private Cache cache;
    private String title = "";


    public IWashPresenterImpl(IWashView iWashView) {
        this.iWashView = iWashView;
    }

    @Override
    public void start() {
        Intent intent = iWashView.getmActivity().getIntent();
        if (null != intent) {
            title = intent.getStringExtra("title");
            isShop = intent.getBooleanExtra("isshop", false);
            iWashView.getNorTopBar().setTitle(title);

        }
        longitude = CacheUtils.getString(iWashView.getmActivity(), Constants.Cache.longitude, "0");
        dimension = CacheUtils.getString(iWashView.getmActivity(), Constants.Cache.dimension, "0");

        list = new ArrayList<>();
        recyclerView = iWashView.getRecycleView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iWashView.getContext()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Store>(iWashView.getContext(), R.layout.item_xiche, list) {
            @Override
            public void convert(final ViewHolder viewHolder, final Store store) {
                if (null != store) {
                    viewHolder.setText(R.id.tv_item_xiche_address, store.getAddress());
                    viewHolder.setText(R.id.tv_item_xiche_name, store.getName());
                    if (!TextUtils.isEmpty(store.getType())) {
                        viewHolder.setText(R.id.tv_item_xiche_type, store.getType());
                    }
                    viewHolder.setText(R.id.tv_item_xiche_sum, "月销" + store.getAmount() + "单");
                    viewHolder.setText(R.id.tv_item_xiche_comment, store.getComment() + "条评论");
                    if ("Y".equals(store.getIs_since())) {
                        viewHolder.setVisible(R.id.iv_xiche_ziti, true);
                    } else {
                        viewHolder.setVisible(R.id.iv_xiche_ziti, false);
                    }
                    viewHolder.setText(R.id.tv_item_xiche_service, store.getServie());
                    viewHolder.setText(R.id.tv_item_xiche_distance, store.getDistance() + "km");
                    viewHolder.setText(R.id.tv_item_xiche_price, "￥ " + store.getPrice());
                    viewHolder.setOnClickListener(R.id.tv_item_xiche_buy, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(iWashView.getContext(), PayActivity.class);
                            intent.putExtra("money", store.getPrice());
                            intent.putExtra("money2", 0);
                            intent.putExtra("money3", 0);
                            iWashView.getmActivity().startActivity(intent);
                        }
                    });
                    viewHolder.setOnClickListener(R.id.linear_item_xiche, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(iWashView.getmActivity(), StoreActivity.class, "id", store.getId());
                        }
                    });

                    RatingBar ratingBar = viewHolder.getView(R.id.rb_xiche_comment);
                    if (null != ratingBar) {
                        ratingBar.setRating(store.getRate());

                    }
                    SimpleDraweeView view = viewHolder.getView(R.id.sv_item_xiche);
                    if (view != null) {
                        view.setImageURI(Uri.parse(store.getUrl()));
                    }

                }

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {

                    RecyclerViewStateUtils.setFooterViewState(iWashView.getmActivity(), recyclerView, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);

                    page++;
                    getData();
                }
            }
        });



        if (iWashView != null && iWashView.getLoadPager() != null) {
            iWashView.getLoadPager().setonEmptyClickListener(this);
            iWashView.getLoadPager().setonErrorClickListener(this);
        }

        getCacheData();
        isShop();
        getDataAndPage(1);
    }

    /**
     * 判断是否是首页的8个按钮
     */
    private void isShop() {
        if (isShop) {
            iWashView.getTwoTool().setTitle("默认", "筛选");
            if (fenleiMap != null) {
                //根据标题 设置服务id
                serviceId = fenleiMap.get(title);
            }

        } else {
            iWashView.getTwoTool().setTitle("分类", "默认");
            if (shaixuanMap != null) {

                //根据标题,设置合作项目id
                if ("车管家".equals(title)) {
                    cooperation = shaixuanMap.get("其它");
                } else {
                    cooperation = shaixuanMap.get(title);

                }
            }
        }
    }

    /**
     * 从第几页开始加载数据
     *
     * @param i
     */
    private void getDataAndPage(int i) {
        page = i;
        //显示加载界面
        if (iWashView != null && iWashView.getLoadPager() != null) {
            iWashView.getLoadPager().setLoadVisable();
        }

        getData();
    }

    private void getData() {

        catalogId = null;

        //获取店铺列表
        RequestParams params = new RequestParams(Constants.URL_STORE_LIST);
        params.addBodyParameter("serviceId", serviceId);
        params.addBodyParameter("sortFiled", sortFiled);
        params.addBodyParameter("catalogId", catalogId);
        params.addBodyParameter("cooperationProject", cooperation);
        params.addBodyParameter("regionId", Constants.regiondId);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);
        params.addBodyParameter("longitude", longitude + "");
        params.addBodyParameter("dimension", dimension + "");



        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {


                if (list != null) {
                    if (page == 1) {
                        list.clear();
                    }
                }

                List<StoreList> listStoreList = GsonParse.getListStoreList(UtilMethod.getString(s));
                if (null != listStoreList) {
                    for (StoreList storeL : listStoreList) {
                        StoreList.ShopInfoBean shopInfo = storeL.getShopInfo();
                        StoreList.ProductBean product = storeL.getProduct();

                        Store store = new Store();
                        if (shopInfo != null) {
                            store.setIs_since(shopInfo.getIs_since());
                            store.setId(shopInfo.getId());
                            store.setName(shopInfo.getShop_name());
                            double services = shopInfo.getServices_score();
                            double products = shopInfo.getProducts_score();
                            store.setDistance(shopInfo.getDistance());
                            try {
                                float v = (float) (services + products);
                                store.setRate(v / 2);
                            } catch (Exception e) {

                            }
                            store.setAmount(shopInfo.getSale_count());
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
                            store.setUrl(UtilMethod.url(shopInfo.getSmall_picture()));
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
                RecyclerViewStateUtils.setLoad(listStoreList, recyclerView, iWashView.getmActivity());

                if (iWashView.getLoadPager() != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            iWashView.getLoadPager().setEmptyVisable();
                        } else {
                            iWashView.getLoadPager().hide();
                        }
                    } else {
                        iWashView.getLoadPager().setEmptyVisable();
                    }
                }


                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                if (iWashView.getLinearLayout() != null) {
                    iWashView.getLinearLayout().setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (iWashView.getLoadPager() != null) {
                        iWashView.getLoadPager().setErrorVisable();
                    }
                } else {
                    RecyclerViewStateUtils.setFooterViewState(iWashView.getmActivity(), recyclerView,
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
    public void clickSecond() {
        if (isShop) {
            if (pinpaiList == null) {
                return;
            }
            pinpai();
        } else {
            if (sortList == null) {
                return;
            }
            sort();
        }


    }

    private void pinpai() {

        //使用鸿洋的万能适配器
        iWashView.getRecyclyView2().setAdapter(new CommonAdapter<String>(iWashView.getContext(), R.layout.item_pp_main_sort2, pinpaiList) {


            @Override
            public void convert(final ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectsaixuan) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iWashView.getContext().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iWashView.getContext().getResources()
                            .getColor(R.color.shouye_fenlei_red));
                } else {
                    viewHolder.setVisible(R.id.sort2_img, false);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, 0xFFFFFFFF);
                    viewHolder.setTextColor(R.id.sort2_txt, color);
                }
                viewHolder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectsaixuan = viewHolder.getPosition();
                        if (selectsaixuan >= 0 && selectsaixuan < pinpaiList.size()) {
                            catalogId = shaixuanMap.get(pinpaiList.size());
                        }
                        getDataAndPage(1);

                        if (isShop) {
                            iWashView.getTwoTool().setSecondTitle(pinpaiList.get(selectsaixuan));
                        } else {

                            iWashView.getTwoTool().setFirstTitle(pinpaiList.get(selectsaixuan));
                        }
                        iWashView.closePopupWindow();
                    }
                });
            }
        });
        iWashView.getRecyclyView2().setLayoutManager(new LinearLayoutManager(iWashView.getContext()));
    }

    @Override
    public void clickFirst() {
        if (isShop) {
            if (sortList == null) {
                return;
            }
            sort();
        } else {
            if (serviceList == null) {
                return;
            }
            fenlei();
        }

    }

    private void sort() {

        //使用鸿洋的万能适配器
        if (isShop) {

            recyclyView2 = iWashView.getRecyclyView();
        } else {
            recyclyView2 = iWashView.getRecyclyView2();
        }
        recyclyView2.setAdapter(new CommonAdapter<String>(iWashView.getContext(), R.layout.item_pp_main_sort2, sortList) {


            @Override
            public void convert(final ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectSort) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iWashView.getContext().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iWashView.getContext().getResources()
                            .getColor(R.color.shouye_fenlei_red));
                } else {
                    viewHolder.setVisible(R.id.sort2_img, false);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, 0xFFFFFFFF);
                    viewHolder.setTextColor(R.id.sort2_txt, color);
                }
                viewHolder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectSort = viewHolder.getPosition();
                        if (selectSort >= 0 && selectSort < sortList.size()) {
                            sortFiled = sortMap.get(sortList.get(selectSort));
                        }
                        if (isShop) {

                            iWashView.getTwoTool().setFirstTitle(sortList.get(selectSort));
                        } else {
                            iWashView.getTwoTool().setSecondTitle(sortList.get(selectSort));
                        }


                        getDataAndPage(1);

                        iWashView.closePopupWindow();
                    }
                });
            }
        });
        recyclyView2.setLayoutManager(new LinearLayoutManager(iWashView.getContext()));
    }


    private void fenlei() {

        //使用鸿洋的万能适配器
        iWashView.getRecyclyView().setAdapter(new CommonAdapter<String>(iWashView.getContext(), R.layout.item_pp_main_sort2, serviceList) {


            @Override
            public void convert(final ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectSort) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iWashView.getContext().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iWashView.getContext().getResources()
                            .getColor(R.color.shouye_fenlei_red));
                } else {
                    viewHolder.setVisible(R.id.sort2_img, false);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, 0xFFFFFFFF);
                    viewHolder.setTextColor(R.id.sort2_txt, color);
                }
                viewHolder.setOnClickListener(R.id.sort2_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectservice = viewHolder.getPosition();
                        if (selectservice >= 0 && selectservice < serviceList.size()) {
                            serviceId = fenleiMap.get(serviceList.get(selectservice));
                        }
                        getDataAndPage(1);
                        iWashView.getTwoTool().setFirstTitle(serviceList.get(selectservice));
                        iWashView.closePopupWindow();
                    }
                });
            }
        });
        iWashView.getRecyclyView().setLayoutManager(new LinearLayoutManager(iWashView.getContext()));
    }


    @Override
    public void click(int id) {

    }

    @Override
    public void ondestroy() {
        if (sortList != null) {
            sortList.clear();
            sortList = null;
        }
        if (serviceList != null) {
            serviceList.clear();
            serviceList = null;
        }
        if (pinpaiList != null) {
            pinpaiList.clear();
            pinpaiList = null;
        }
        if (fenleiMap != null) {
            fenleiMap.clear();
            fenleiMap = null;
        }
        if (sortMap != null) {
            sortMap.clear();
            sortMap = null;
        }
        if (shaixuanMap != null) {
            shaixuanMap.clear();
            shaixuanMap = null;
        }
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

    /**
     * 获取缓存数据
     */
    private void getCacheData() {
        String cache1 = CacheUtils.getString(iWashView.getmActivity(), "cache", "");
        if (TextUtils.isEmpty(cache1)) {//如果为空就从网络获取
            getDataCache();
        } else {
            if (null == cache) { //表示没解析过
                cache = GsonParse.getObject(cache1, Cache.class);
                parser(cache);
            }

        }
    }

    public void getDataCache() {
        RequestParams params = new RequestParams(Constants.URL_CACHE);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                CacheUtils.putString(iWashView.getmActivity(), "cache", UtilMethod.getString(s));
                cache = GsonParse.getObject(UtilMethod.getString(s), Cache.class);
                if (null != cache) {
                    parser(cache);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }

    /**
     * 解析缓存数据
     *
     * @param cache
     */
    private void parser(Cache cache) {
        sortList = new ArrayList<>();
        pinpaiList = new ArrayList<>();
        serviceList = new ArrayList<>();
        fenleiMap = new HashMap<>();
        sortMap = new HashMap<>();
        shaixuanMap = new HashMap<>();

        List<Cache.SortMapItemsBean> sortMapItems = cache.getSortMapItems();
        List<Cache.TShopCatalogItemsBean> tShopCatalogItems = cache.getTShopCatalogItems();
        List<Cache.TShopServiceItemsBean> tShopServiceItems = cache.getTShopServiceItems();
        // System.out.println("排序");
        for (Cache.SortMapItemsBean sortMapItemsBean : sortMapItems) {
            sortList.add(sortMapItemsBean.getSort_name());
            sortMap.put(sortMapItemsBean.getSort_name(), sortMapItemsBean.getSort_code());
        }
        // System.out.println("店铺分类");
        for (Cache.TShopCatalogItemsBean tShopCatalogItemsBean : tShopCatalogItems) {
            shaixuanMap.put(tShopCatalogItemsBean.getShopCatalogName(), tShopCatalogItemsBean.getId());
            pinpaiList.add(tShopCatalogItemsBean.getShopCatalogName());
        }
        // System.out.println("服务分类");
        for (Cache.TShopServiceItemsBean tShopServiceItemsBean : tShopServiceItems) {
            if ("热销服务".equals(tShopServiceItemsBean.getShopServiceName())) {
                continue;
            }
            serviceList.add(tShopServiceItemsBean.getShopServiceName());
            fenleiMap.put(tShopServiceItemsBean.getShopServiceName(), tShopServiceItemsBean.getId());
        }
    }
}
