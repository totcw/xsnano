package com.betterda.xsnano.goods.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.cardemulation.OffHostApduService;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.application.MyApplication;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.comment.CommentActivity;
import com.betterda.xsnano.goods.GoodsDetail;
import com.betterda.xsnano.goods.GoodsDetail2;
import com.betterda.xsnano.goods.adapter.GoodsAdapter;
import com.betterda.xsnano.goods.model.Comment;
import com.betterda.xsnano.goods.view.IGoodsDetailView;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.javabean.ShoppingDeatil;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.order.ComfirmOrderActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.LoadingPager;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

/**
 * Created by Administrator on 2016/5/17.
 */
public class IGoodsDetailPresenterImpl implements IGoodsDetailPresenter, View.OnClickListener {
    private IGoodsDetailView iGoodsDetailView;
    private int amount = 1;//选择的数量
    private int amountBus;//购物车中的数量
    private GoodsAdapter adapter;
    private List<Comment> list;

    private RecyclerView recyclerView;

    private String price = "0";//选择商品的价格
    private String url, smallurl; //图片
    private String shopName;//商品名
    private int count;//销售量
    private int sum;//库存
    private String productId;//商品的id
    private String shopId;//店铺id
    private String productHtml;//图文详细
    private int goldCount;//可抵消的金币个数
    private int totalComment;
    private float productTotalStar;
    private ArrayList<String> arrayList;//图文详细的图片集

    public IGoodsDetailPresenterImpl(IGoodsDetailView iGoodsDetailView) {
        this.iGoodsDetailView = iGoodsDetailView;
    }

    @Override
    public void start() {
        list = new ArrayList<>();
        arrayList = new ArrayList<>();

        productId = ((MyApplication) (iGoodsDetailView.getmActivity().getApplication())).getProductid();
        shopId = ((MyApplication) (iGoodsDetailView.getmActivity().getApplication())).getShopid();


        recyclerView = iGoodsDetailView.getRecyclyView();
        recyclerView.setLayoutManager(new LinearLayoutManager(iGoodsDetailView.getContext()));
        adapter = new GoodsAdapter(this, iGoodsDetailView.getContext());

        recyclerView.setAdapter(adapter);


        getData();
        if (iGoodsDetailView != null && iGoodsDetailView.getLoadPager() != null) {
            iGoodsDetailView.getLoadPager().setonEmptyClickListener(this);
            iGoodsDetailView.getLoadPager().setonErrorClickListener(this);
        }
    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_GOODS_DETAIL);
        params.addBodyParameter("id", productId);
        params.addBodyParameter("account", CacheUtils.getString(iGoodsDetailView.getmActivity(), "number", ""));

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println(s);
                ShoppingDeatil shoppingDeatil = GsonParse.getObject(UtilMethod.getString(s), ShoppingDeatil.class);
                List<ShoppingDeatil.Comment4ApiItemsBean> comment4ApiItems = shoppingDeatil.getComment4ApiItems();
                //购物车数目
                ShoppingDeatil.ShopcartEntityBean shopcartEntity = shoppingDeatil.getShopcartEntity();
                if (null != shopcartEntity) {
                    amountBus = shopcartEntity.getTotalcount();
                }
                //商品评分
                double p = shoppingDeatil.getProductTotalStar();
                productTotalStar = (float) p;
                //商品评论总数
                totalComment = shoppingDeatil.getTotalComment();
                if (null != shoppingDeatil) {
                    ShoppingDeatil.ProductBean product = shoppingDeatil.getProduct();

                    if (product != null) {
                        //大图片
                        url = UtilMethod.url(product.getBigPicture());
                        //商品名
                        shopName = product.getName();

                        try {
                            //价格
                            price = Float.parseFloat(product.getSalePrice()) + "";
                        } catch (Exception e) {

                        }

                        //销售量
                        count = product.getSellCount();
                        //库存
                        sum = product.getStock();
                        //可抵消金币数
                        goldCount = product.getPayGolden();
                        //获取图文详细
                        productHtml = product.getImages();
                        //商品小图片
                        smallurl = UtilMethod.url(product.getLittlePicture());
                        //图文详情的图片集
                        String images = product.getImages();
                        if (!TextUtils.isEmpty(images)) {
                            String[] split = images.split(",");
                            for (String split1 : split) {
                                if (arrayList != null) {
                                    arrayList.add(split1);
                                }
                            }
                        }
                    }

                    if (comment4ApiItems != null) {
                        for (ShoppingDeatil.Comment4ApiItemsBean comment4ApiItemsBean : comment4ApiItems) {
                            if (comment4ApiItemsBean != null) {
                                ShoppingDeatil.Comment4ApiItemsBean.CommentEntityBean commentEntity = comment4ApiItemsBean.getCommentEntity();
                                if (commentEntity != null) {
                                    Comment comment = new Comment();
                                    comment.setContent(commentEntity.getContent());
                                    comment.setTime(commentEntity.getCreate_date());
                                    comment.setName(commentEntity.getNick_name());
                                    comment.setUrl(UtilMethod.url(commentEntity.getUser_img()));

                                    if (null != list) {
                                        list.add(comment);
                                    }
                                }

                            }
                        }
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
                if (iGoodsDetailView != null) {
                    if (iGoodsDetailView.getSimpleDraw() != null) {
                        iGoodsDetailView.getSimpleDraw().setImageURI(Uri.parse(url));
                    }

                    if (iGoodsDetailView.getTextViewName() != null) {

                        iGoodsDetailView.getTextViewName().setText(shopName);
                    }

                    if (iGoodsDetailView.getTextViewBus() != null) {
                        if (amountBus > 0) {
                            if (amountBus > 99) {
                                amountBus = 99;
                            }
                            iGoodsDetailView.getTextViewBus().setText(amountBus + "");
                            iGoodsDetailView.getTextViewBus().setVisibility(View.VISIBLE);
                        } else {
                            iGoodsDetailView.getTextViewBus().setVisibility(View.INVISIBLE);
                        }

                    }

                    if (iGoodsDetailView.getTextViewCount() != null) {
                        iGoodsDetailView.getTextViewCount().setText("月销:" + count);
                    }
                    if (iGoodsDetailView.getTextViewSum() != null) {
                        iGoodsDetailView.getTextViewSum().setText("库存:" + sum);
                    }

                    if (iGoodsDetailView.getTextViewPrice() != null) {
                        iGoodsDetailView.getTextViewPrice().setText("￥ " + price);
                    }
                    if (iGoodsDetailView.getTextViewComment() != null) {
                        iGoodsDetailView.getTextViewComment().setText(totalComment + "人评价");
                    }
                    if (iGoodsDetailView.getTextViewCommentStar() != null) {
                        iGoodsDetailView.getTextViewCommentStar().setText(productTotalStar + "");
                    }
                    if (iGoodsDetailView.getRabting() != null) {
                        iGoodsDetailView.getRabting().setRating(productTotalStar);
                    }


                    if (list == null || list.size() == 0) {
                        iGoodsDetailView.getTextViewMore().setText("暂无评论");

                    } else {
                        iGoodsDetailView.getTextViewMore().setText("更多评论");
                    }

                    RelativeLayout relativeGoods = iGoodsDetailView.getRelativeGoods();
                    if (recyclerView != null) {
                        relativeGoods.setVisibility(View.VISIBLE);
                    }

                    iGoodsDetailView.getScrollview().post(new Runnable() {
                        //让scrollview跳转到顶部，必须放在runnable()方法中
                        @Override
                        public void run() {
                            iGoodsDetailView.getScrollview().scrollTo(0, 0);
                            LoadingPager loadingPager = iGoodsDetailView.getLoadPager();
                            if (loadingPager != null)
                                loadingPager.hide();

                        }
                    });
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {


                if (list == null || list.size() == 0) {
                    iGoodsDetailView.getTextViewMore().setText("暂无评论");

                } else {
                    iGoodsDetailView.getTextViewMore().setText("更多评论");
                }

                RelativeLayout relativeGoods = iGoodsDetailView.getRelativeGoods();
                if (recyclerView != null) {
                    relativeGoods.setVisibility(View.VISIBLE);
                }

                iGoodsDetailView.getScrollview().post(new Runnable() {
                    //让scrollview跳转到顶部，必须放在runnable()方法中
                    @Override
                    public void run() {
                        iGoodsDetailView.getScrollview().scrollTo(0, 0);
                        LoadingPager loadingPager = iGoodsDetailView.getLoadPager();
                        if (loadingPager != null)
                            loadingPager.hide();

                    }
                });


            }
        });
    }


    @Override
    public void addBus() {

        if (UtilMethod.isLogin(iGoodsDetailView.getmActivity())) {


            RequestParams params = new RequestParams(Constants.URL_BUS_ADD);
            String number = CacheUtils.getString(iGoodsDetailView.getmActivity(), "number", "");

            params.addBodyParameter("account", number);
            params.addBodyParameter("productId", productId);
            params.addBodyParameter("count", amount + "");
            GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
                @Override
                public void onSuccess(String s) {

                    GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                        @Override
                        public void onSuccess(String result, String resultMsg) {

                            if ("true".equals(result)) {
                                amountBus += amount;

                                if (amountBus < 1) {
                                    iGoodsDetailView.getTextViewBus().setText(0 + "");
                                } else if (amountBus > 99) {
                                    iGoodsDetailView.getTextViewBus().setText(99 + "");
                                } else {
                                    iGoodsDetailView.getTextViewBus().setText(amountBus + "");
                                }
                                iGoodsDetailView.getTextViewBus().setVisibility(View.VISIBLE);


                            }
                            UtilMethod.Toast(iGoodsDetailView.getmActivity(), resultMsg);

                        }
                    });
                }

                @Override
                public void onError(Throwable throwable, boolean b) {


                }
            });
        } else {
            UtilMethod.startIntent(iGoodsDetailView.getmActivity(), LoginActivity.class);
        }


    }

    @Override
    public void add() {
        amount++;
        iGoodsDetailView.getTextViewAmount().setText("已选择" + amount + "件");
        iGoodsDetailView.getTextViewAmount2().setText(amount + "");

    }

    @Override
    public void sub() {
        amount--;
        if (amount < 1) {
            amount = 1;
        }
        iGoodsDetailView.getTextViewAmount().setText("已选择" + amount + "件");
        iGoodsDetailView.getTextViewAmount2().setText(amount + "");

    }

    @Override
    public void show() {
        // iGoodsDetailView.getTextViewAmountpp2().setText("已选" + amount + "件");
    }

    @Override
    public void comment() {

        UtilMethod.startIntentParams(iGoodsDetailView.getContext(), CommentActivity.class, "id", productId);
    }

    @Override
    public List<Comment> getList() {
        return list;
    }

    @Override
    public void onBindViewHolder(GoodsAdapter.GoodsHolder holder, int position) {
        if (null != list) {
            Comment comment = list.get(position);
            holder.tv_name.setText(comment.getName());
            holder.tv_time.setText(comment.getTime());
            holder.tv_content.setText(comment.getContent());
            if (!TextUtils.isEmpty(comment.getUrl())) {
                holder.sv.setImageURI(Uri.parse(comment.getUrl()));
            }
        }
    }

    @Override
    public void addBuy() {

        if (UtilMethod.isLogin(iGoodsDetailView.getmActivity())) {
            List<Bus> busList = new ArrayList<>();
            Bus bus = new Bus();
            bus.setName(shopName);
            bus.setUrl(smallurl);
            bus.setAmount(amount);
            bus.setMoney(Float.parseFloat(price));
            bus.setId(productId);
            bus.setGoldCount(goldCount);
            bus.setShopId(shopId);
            busList.add(bus);
            ((MyApplication) iGoodsDetailView.getmActivity().getApplication()).getBusList().clear();
            ((MyApplication) iGoodsDetailView.getmActivity().getApplication()).getBusList().addAll(busList);

            UtilMethod.startIntent(iGoodsDetailView.getContext(), ComfirmOrderActivity.class);
        } else {
            UtilMethod.startIntent(iGoodsDetailView.getmActivity(), LoginActivity.class);
        }


    }

    /**
     * 图文详细
     */
    @Override
    public void canshu() {

        UtilMethod.startIntentParams(iGoodsDetailView.getmActivity(), GoodsDetail2.class, "images", productHtml);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_error:  //加载错误时的点击事件

                getData();

                break;
            case R.id.frame_empty://数据为空的点击事件
                getData();
                break;
        }
    }
}
