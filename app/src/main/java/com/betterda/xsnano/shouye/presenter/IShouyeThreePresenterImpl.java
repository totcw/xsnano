package com.betterda.xsnano.shouye.presenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.betterda.xsnano.R;
import com.betterda.xsnano.interfac.OnListLoadNextPageListener;
import com.betterda.xsnano.javabean.Cache;
import com.betterda.xsnano.javabean.StoreList;
import com.betterda.xsnano.shouye.adapter.MainAdapter;
import com.betterda.xsnano.shouye.adapter.MoreAdapter;
import com.betterda.xsnano.shouye.model.Store;
import com.betterda.xsnano.shouye.view.IShouyeView;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.ConstantsData;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.MyLinearLayoutManager;
import com.betterda.xsnano.view.MyRecycleView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责与首页分类的交互
 * Created by Administrator on 2016/4/26.
 */
public class IShouyeThreePresenterImpl implements IShouyeThreePresenter, View.OnClickListener {
    private IShouyeView iShouyeView;
    private MainAdapter mainAdapter;//分类主listview的适配器

    private List<String> mainList; //分类的容器
    private List<String> sortList; //排序的容器
    private List<String> shaixuanList; //筛选的容器

    private int selectmain = -1;//mainlistview选中的位置
    private int selectmore = -1;//morelistview选中的位置
    private int selectSort = -1;
    private int selectsaixuan = -1;
    private int color = 0xFF909090;


    private List<Store> storeList;//存放店铺的容器

    //分类对应的id
    private Map<String, String> fenleiMap;
    //排序对应的id
    private Map<String, String> sortMap;
    //筛选对应的id
    private Map<String, String> shaixuanMap;

    private String serviceId;//分类id
    private String sortFiled;//排序id  10 评分  20 附近
    private String catalogId;//筛选id

    private int page = 1;//页码
    private String rows = "10";//每页的条数
    private double longitude;//经度
    private double dimension;//纬度

    private HeaderAndFooterRecyclerViewAdapter adapter;

    /**
     * 定位功能
     */
    public LocationClient mLocationClient; //定位的类
    public BDLocationListener myListener = new MyLocationListener();
    private MyRecycleView recyclerView;

    private Cache cache;//缓存数据

    public IShouyeThreePresenterImpl(IShouyeView iShouyeView) {
        this.iShouyeView = iShouyeView;
    }

    @Override
    public void start() {

        /**
         * 定位相关
         */
        mLocationClient = new LocationClient(iShouyeView.getmActivity());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        UtilMethod.initLocation(mLocationClient);
        //开启定位
        mLocationClient.start();


        storeList = new ArrayList<>();
        recyclerView = iShouyeView.getRecyclyViewShouye();
        //解决scrollview嵌套recycleview 惯性消失的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(iShouyeView.getmActivity()));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<Store>(iShouyeView.getmActivity(), R.layout.item_homelistview, storeList) {

            @Override
            public void convert(com.zhy.base.adapter.ViewHolder viewHolder, final Store store) {

                if (null != store) {

                    if ("Y".equals(store.getIs_since())) {
                        viewHolder.setVisible(R.id.iv_xiche_ziti, true);
                    } else {
                        viewHolder.setVisible(R.id.iv_xiche_ziti, false);
                    }
                    viewHolder.setText(R.id.tv_item_homelistview_name, store.getName());
                    viewHolder.setText(R.id.tv_item_homelistview_sum, "月销" + store.getAmount() + "单");
                    viewHolder.setText(R.id.tv_item_homelistview_location, store.getComment() + "条评论");
                    viewHolder.setText(R.id.tv_item_homelistview_address, store.getAddress());
                    if (!TextUtils.isEmpty(store.getType())) {
                        viewHolder.setText(R.id.tv_item_homelistview_type, store.getType());
                    }
                    viewHolder.setText(R.id.tv_item_homelistview_distance, store.getDistance() + "km");

                    RatingBar ratingBar = viewHolder.getView(R.id.rb_comment);


                    if (null != ratingBar) {

                        ratingBar.setRating(store.getRate());
                    }
                    if (null != store.getUrl()) {
                        SimpleDraweeView simpleDraweeView = viewHolder.getView(R.id.iv_item_homelistview);
                        if (null != simpleDraweeView) {
                            simpleDraweeView.setImageURI(Uri.parse(UtilMethod.url(store.getUrl())));
                        }
                    }

                    //设置点击事件
                    viewHolder.setOnClickListener(R.id.linear_homelistvew, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilMethod.startIntentParams(iShouyeView.getmActivity(), StoreActivity.class, "id", store.getId());
                        }
                    });

                }

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setonListLoadNextPageListener(new OnListLoadNextPageListener() {
            @Override
            public void onLoadNextPage(View view) {
                //设置加载更多
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(recyclerView)) {
                    RecyclerViewStateUtils.setFooterViewState(iShouyeView.getmActivity(), recyclerView, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);
                    page++;
                    getData();

                }
            }
        });

        if (iShouyeView != null && iShouyeView.getLoadPager() != null) {
            iShouyeView.getLoadPager().setonEmptyClickListener(this);
            iShouyeView.getLoadPager().setonErrorClickListener(this);
        }
    }

    @Override
    public void setTile() {
        //设置统一标题

        iShouyeView.getViewThree().setTitle("分类", "排序", "筛选");
        iShouyeView.getMfvShouye().setTitle("分类", "排序", "筛选");
    }

    @Override
    public void clickFirst() {
        if (mainList != null) {
            mainAdapter = new MainAdapter(this);
            iShouyeView.getMainListview().setAdapter(mainAdapter);
            iShouyeView.getMainListview().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }


    }

    @Override
    public void clickSecond() {

        if (sortList == null) {
            return;
        }

        //使用鸿洋的万能适配器
        iShouyeView.getRecyclyView().setAdapter(new CommonAdapter<String>(iShouyeView.getmActivity(), R.layout.item_pp_main_sort2, sortList) {


            @Override
            public void convert(final com.zhy.base.adapter.ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectSort) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iShouyeView.getmActivity().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iShouyeView.getmActivity().getResources()
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
                            iShouyeView.getViewThree().setSecondTitle(sortList.get(selectSort));
                            iShouyeView.getMfvShouye().setSecondTitle(sortList.get(selectSort));
                        }
                        iShouyeView.closePopupWindow();
                        getDataAndPage(1);
                    }
                });
            }
        });
        iShouyeView.getRecyclyView().setLayoutManager(new LinearLayoutManager(iShouyeView.getmActivity()));

    }

    @Override
    public void clickThree() {

        if (shaixuanList == null) {
            return;
        }
        //使用鸿洋的万能适配器
        iShouyeView.getRecyclyView2().setAdapter(new CommonAdapter<String>(iShouyeView.getmActivity(), R.layout.item_pp_main_sort2, shaixuanList) {


            @Override
            public void convert(final com.zhy.base.adapter.ViewHolder viewHolder, String s) {
                viewHolder.setText(R.id.sort2_txt, s);
                if (viewHolder.getPosition() == selectsaixuan) {
                    viewHolder.setVisible(R.id.sort2_img, true);
                    viewHolder.setBackgroundColor(R.id.sort2_layout, iShouyeView.getmActivity().getResources()
                            .getColor(R.color.bg_gray));
                    viewHolder.setTextColor(R.id.sort2_txt, iShouyeView.getmActivity().getResources()
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
                        if (selectsaixuan >= 0 && selectsaixuan < shaixuanList.size()) {

                            catalogId = shaixuanMap.get(shaixuanList.get(selectsaixuan));
                            iShouyeView.getViewThree().setThreeTitle(shaixuanList.get(selectsaixuan));
                            iShouyeView.getMfvShouye().setThreeTitle(shaixuanList.get(selectsaixuan));
                        }
                        iShouyeView.closePopupWindow();
                        getDataAndPage(1);
                    }
                });
            }
        });
        iShouyeView.getRecyclyView2().setLayoutManager(new LinearLayoutManager(iShouyeView.getmActivity()));

    }

    @Override
    public List<String> getCategoryList() {
        return mainList;
    }

    @Override
    public View getMainView(final int position, View convertview) {
        final ViewHolder holder;
        if (convertview == null) {
            convertview = View.inflate(iShouyeView.getmActivity(), R.layout.item_pp_main_sort, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertview.findViewById(R.id.mainitem_txt);
            holder.iv = (ImageView) convertview.findViewById(R.id.mainitem_img);
            holder.mainitem_layout = (LinearLayout) convertview.findViewById(R.id.mainitem_layout);
            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }
        if (null != mainList) {

            holder.tv.setText(mainList.get(position));
        }

        if (selectmain == position) {
            holder.iv.setVisibility(View.VISIBLE);
            holder.mainitem_layout.setSelected(true);
            holder.tv.setTextColor(iShouyeView.getmActivity().getResources()
                    .getColor(R.color.shouye_fenlei_red));
        } else {
            holder.iv.setVisibility(View.INVISIBLE);
            holder.mainitem_layout.setSelected(false);
            holder.tv.setTextColor(color);
        }


        holder.mainitem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置选中的位置
                selectmain = position;
                selectmore = -1;//将更多的先清空
                iShouyeView.getViewThree().setFirstTitle(mainList.get(position));
                iShouyeView.getMfvShouye().setFirstTitle(mainList.get(position));
                //设置分类id
                serviceId = fenleiMap.get(mainList.get(position));
                mainAdapter.notifyDataSetChanged();
                //关闭并请求数据
                iShouyeView.closePopupWindow();
                //从第一页开始加载数据
                getDataAndPage(1);

            }
        });

        return convertview;
    }

    /**
     * 从第几页开始加载数据
     *
     * @param p
     */
    private void getDataAndPage(int p) {
        page = p;
        //显示加载界面
        if (iShouyeView != null && iShouyeView.getLoadPager() != null) {
            iShouyeView.getLoadPager().setLoadVisable();
        }
        getCacheData();
        getData();
    }

    @Override
    public View getMoreView(final int position, View convertview, final List<String> morelist) {
        final ViewHolder2 holder2;
        if (convertview == null) {
            holder2 = new ViewHolder2();
            convertview = View.inflate(iShouyeView.getmActivity(), R.layout.item_pp_main_sort_more, null);
            holder2.tv = (TextView) convertview.findViewById(R.id.moreitem_txt);
            convertview.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertview.getTag();
        }
        if (morelist != null) {
            holder2.tv.setText(morelist.get(position));
        }

        if (selectmore == position) {
            holder2.tv.setTextColor(iShouyeView.getmActivity().getResources()
                    .getColor(R.color.shouye_fenlei_red));
        } else {
            holder2.tv.setTextColor(color);
        }
        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder2.tv.setTextColor(iShouyeView.getmActivity().getResources()
                        .getColor(R.color.shouye_fenlei_red));
                selectmore = position;
                iShouyeView.getViewThree().setFirstTitle(morelist.get(position));
                iShouyeView.getMfvShouye().setFirstTitle(morelist.get(position));
                //设置分类id
                serviceId = fenleiMap.get(morelist.get(position));
                //关闭popupwindow
                iShouyeView.closePopupWindow();

                getDataAndPage(1);

            }
        });

        return convertview;
    }

    @Override
    public void click(int id) {

    }



    /**
     * 获取缓存数据
     */
    private void getCacheData() {
        String cache1 = CacheUtils.getString(iShouyeView.getmActivity(), "cache", "");
        if (TextUtils.isEmpty(cache1)) {//如果为空就从网络获取
            getDataCache();
        } else {
            if (null == cache) { //表示没解析过
                cache = GsonParse.getObject(cache1, Cache.class);
                parser(cache);
            }

        }
    }


    /**
     * 从服务器获取数据
     */
    private void getData() {


        //获取店铺列表
        final RequestParams params = new RequestParams(Constants.URL_STORE_LIST);
        params.addBodyParameter("serviceId", serviceId);
        params.addBodyParameter("sortFiled", sortFiled);
        params.addBodyParameter("catalogId", catalogId);
        params.addBodyParameter("regionId", Constants.regiondId);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);
        params.addBodyParameter("longitude", longitude + "");
        params.addBodyParameter("dimension", dimension + "");

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (storeList != null) {
                    //如果page不==1,就是不上拉加载就清空
                    if (page == 1) {
                        storeList.clear();
                    }

                }

                List<StoreList> listStoreList = GsonParse.getListStoreList(UtilMethod.getString(s));
                if (null != listStoreList) {
                    for (StoreList storeL : listStoreList) {
                        if (storeL != null) {
                            StoreList.ShopInfoBean shopInfo = storeL.getShopInfo();
                            StoreList.ProductBean product = storeL.getProduct();
                            Store store = new Store();

                            if (shopInfo != null) {
                                store.setIs_since(shopInfo.getIs_since());
                                store.setId(shopInfo.getId());
                                store.setName(shopInfo.getShop_name());
                                double services = shopInfo.getServices_score();
                                double products = shopInfo.getProducts_score();
                                try {
                                    float v = (float) (services + products);
                                    store.setRate(v / 2);
                                    store.setAmount(Integer.parseInt(storeL.getTotalSellCount()));
                                } catch (Exception e) {

                                }

                                store.setDistance(shopInfo.getDistance());
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

                            if (storeList != null) {
                                storeList.add(store);

                            }
                        }
                    }
                }
                RecyclerViewStateUtils.setLoad(listStoreList, recyclerView, iShouyeView.getmActivity());
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }



                if (iShouyeView != null) {
                    if (iShouyeView.getLoadPager() != null) {
                        if (storeList != null) {
                            if (storeList.size() == 0) {
                                iShouyeView.getLoadPager().setEmptyVisable();
                            } else {
                                iShouyeView.getLoadPager().hide();
                            }
                        } else {
                            iShouyeView.getLoadPager().setEmptyVisable();
                        }
                    }

                    FrameLayout frameShouye = iShouyeView.getFrameShouye();
                    if (null != frameShouye) {
                        frameShouye.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                //如果是上拉加载错误显示加载错误
                if (page == 1) {
                    if (iShouyeView != null) {

                        LoadingPager loadPager = iShouyeView.getLoadPager();
                        if (null != loadPager) {
                            loadPager.setErrorVisable();
                        }

                        //将popupwindow关闭
                        iShouyeView.closePopupWindow();
                    }
                } else {
                    //将popupwindow关闭
                    if (iShouyeView != null) {

                        iShouyeView.closePopupWindow();
                        RecyclerViewStateUtils.setFooterViewState(iShouyeView.getmActivity(), recyclerView,
                                Constants.PAGE_SIZE, LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getData();
                                    }
                                });
                    }
                }
            }
        });
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


    static class ViewHolder {
        TextView tv;
        ImageView iv;
        LinearLayout mainitem_layout;
    }

    static class ViewHolder2 {
        TextView tv;

    }


    @Override
    public List<Store> getList() {
        return storeList;
    }

    @Override
    public View getView(int position, View convertView) {

        return convertView;
    }


    /**
     * 定位的回调方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location) {

                TextView textViewCity = iShouyeView.getTextViewCity();
                if (null != textViewCity) {
                    String city = location.getCity();
                    if (!TextUtils.isEmpty(city)) {
                        textViewCity.setText(city);
                    }

                    longitude = location.getLongitude();
                    dimension = location.getLatitude();
                    CacheUtils.putString(iShouyeView.getmActivity(), Constants.Cache.longitude, longitude + "");
                    CacheUtils.putString(iShouyeView.getmActivity(), Constants.Cache.dimension, dimension + "");

                }


                //提交地址信息
                uploadAddress(longitude,dimension,location.getCity(),location.getProvince()+location.getCity()+location.getDistrict()+location.getStreet()+location.getStreetNumber());

            }
            //停止定位
            if (mLocationClient != null) {

                mLocationClient.stop();
            }


            //从第一页开始加载
            getDataAndPage(1);


        }
    }

    private void uploadAddress(double longitude,double latitude,String district ,String address) {
        boolean islogin = CacheUtils.getBoolean(iShouyeView.getmActivity(), "islogin", false);
        if (!islogin) {
            return;
        }

        RequestParams params = new RequestParams(Constants.URL_ADD_CHANGYONG_ADDRESS);
        params.addBodyParameter("account", CacheUtils.getString(iShouyeView.getContext(), "number", ""));
        params.addBodyParameter("longitude", longitude+"");
        params.addBodyParameter("latitude", latitude+"");
        params.addBodyParameter("district", district);
        params.addBodyParameter("address", address);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println("s:"+s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }
        });
    }

    public String getSelectSaixuan() {
        return "筛选";
    }

    @Override
    public void onDestroy() {

        if (mainList != null) {
            mainList.clear();
            mainList = null;
        }
        if (sortList != null) {
            sortList.clear();
            sortList = null;
        }
        if (shaixuanList != null) {
            shaixuanList.clear();
            shaixuanList = null;
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
    public void setTop(boolean istop) {
        recyclerView.setTop(istop);
    }

    @Override
    public void onActivityResult(Intent data) {
        //重新请求店铺列表
        //TODO 重新设置区域id,经纬度

        getDataAndPage(1);
    }

    public void getDataCache() {
        RequestParams params = new RequestParams(Constants.URL_CACHE);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                CacheUtils.putString(iShouyeView.getmActivity(), "cache", UtilMethod.getString(s));
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
        mainList = new ArrayList<>();
        shaixuanList = new ArrayList<>();
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
            System.out.println("name:"+tShopCatalogItemsBean.getShopCatalogName());
            System.out.println("id:"+tShopCatalogItemsBean.getId());
            shaixuanMap.put(tShopCatalogItemsBean.getShopCatalogName(), tShopCatalogItemsBean.getId());
            shaixuanList.add(tShopCatalogItemsBean.getShopCatalogName());
            ConstantsData.getHashMap().put(tShopCatalogItemsBean.getId(), tShopCatalogItemsBean.getShopCatalogName());
        }
        // System.out.println("服务分类");
        for (Cache.TShopServiceItemsBean tShopServiceItemsBean : tShopServiceItems) {
            if ("热销服务".equals(tShopServiceItemsBean.getShopServiceName())) {
                continue;
            }
            mainList.add(tShopServiceItemsBean.getShopServiceName());

            fenleiMap.put(tShopServiceItemsBean.getShopServiceName(), tShopServiceItemsBean.getId());
        }
    }
}
