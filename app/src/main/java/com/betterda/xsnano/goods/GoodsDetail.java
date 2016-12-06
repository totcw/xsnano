package com.betterda.xsnano.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.BusActivity;
import com.betterda.xsnano.goods.presenter.IGoodsDetailPresenter;
import com.betterda.xsnano.goods.presenter.IGoodsDetailPresenterImpl;
import com.betterda.xsnano.goods.view.IGoodsDetailView;
import com.betterda.xsnano.params.ParamsActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 商品详情界面
 * Created by Administrator on 2016/5/16.
 */
public class GoodsDetail extends BaseActivity implements IGoodsDetailView, View.OnClickListener {
    private RelativeLayout relative_goodsdetail_chose, relative_goodsdetail_bus,
             relative_goodsdetail_bus_title, relative_goodsdetail_buy,
            relative_goodsdetail_comment, relative_goodsdetail_canshu,
            relative_goodsdetail_morecomment, relative_goodsdetail;
    private ImageView iv_goodesdetail_delete, iv_goodesdetail_add,iv_goodsdetail_back;
    private TextView  tv_goodsdetail_amount, tv_goodsdetail_bus,
             tv_goodsdetail_morecomment,tv_goodsdetail_amount2,tv_name,tv_price,tv_count,tv_shangpingdetail_comment2,
            tv_shangpingdetail_comment3,tv_goodsdetail_sum;
    private ImageView simpleDraweeView;
    private RecyclerView rv_goodsdetail;
    private LoadingPager loadpager_goodsdetail;
    private IGoodsDetailPresenter iGoodsDetailPresenter;
    private ScrollView scrollView_goods;
    private RatingBar rb_detail_comment;

    @Override
    public void initView() {
        setContentView(R.layout.activity_shangpingdetail);
        relative_goodsdetail = (RelativeLayout) findViewById(R.id.relative_goodsdetail);
        loadpager_goodsdetail = (LoadingPager) findViewById(R.id.loadpager_goodsdetail);
        relative_goodsdetail_chose = (RelativeLayout) findViewById(R.id.relative_goodsdetail_chose);
        relative_goodsdetail_bus = (RelativeLayout) findViewById(R.id.relative_goodsdetail_bus);
        relative_goodsdetail_bus_title = (RelativeLayout) findViewById(R.id.relative_goodsdetail_bus_title);
        relative_goodsdetail_canshu = (RelativeLayout) findViewById(R.id.relative_goodsdetail_canshu);
        tv_goodsdetail_amount = (TextView) findViewById(R.id.tv_goodsdetail_amount);
        tv_goodsdetail_bus = (TextView) findViewById(R.id.tv_goodsdetail_bus);
        tv_count = (TextView) findViewById(R.id.tv_shangpingdetail_count);
        tv_price = (TextView) findViewById(R.id.tv_shangpingdetail_price);
        tv_name = (TextView) findViewById(R.id.tv_shangpingdetail_name);
        tv_goodsdetail_sum = (TextView) findViewById(R.id.tv_shangpingdetail_sum);
        relative_goodsdetail_comment = (RelativeLayout) findViewById(R.id.relative_goodsdetail_comment);
        relative_goodsdetail_buy = (RelativeLayout) findViewById(R.id.relative_goodsdetail_buy);
        relative_goodsdetail_morecomment = (RelativeLayout) findViewById(R.id.relative_goodsdetail_morecomment);
        tv_goodsdetail_morecomment = (TextView) findViewById(R.id.tv_goodsdetail_morecomment);
        rv_goodsdetail = (RecyclerView) findViewById(R.id.rv_goodsdetail);
        iv_goodsdetail_back = (ImageView) findViewById(R.id.iv_goodsdetail_back);
        iv_goodesdetail_delete = (ImageView) findViewById(R.id.iv_goodsdetail_delete);
        iv_goodesdetail_add = (ImageView) findViewById(R.id.iv_goodsdetail_add);
        scrollView_goods = (ScrollView) findViewById(R.id.scrollView_goods);
        tv_goodsdetail_amount2 = (TextView) findViewById(R.id.tv_goodsdetail_amount2);
        tv_shangpingdetail_comment2 = (TextView) findViewById(R.id.tv_shangpingdetail_comment2);
        tv_shangpingdetail_comment3 = (TextView) findViewById(R.id.tv_shangpingdetail_comment3);
        simpleDraweeView = (ImageView) findViewById(R.id.sv_goodsdetail);
        rb_detail_comment = (RatingBar) findViewById(R.id.rb_detail_comment);
    }


    @Override
    public void initListener() {
        relative_goodsdetail_chose.setOnClickListener(this);
        relative_goodsdetail_bus.setOnClickListener(this);
        relative_goodsdetail_bus_title.setOnClickListener(this);
        iv_goodesdetail_delete.setOnClickListener(this);
        iv_goodesdetail_add.setOnClickListener(this);
        relative_goodsdetail_buy.setOnClickListener(this);
        relative_goodsdetail_comment.setOnClickListener(this);
        relative_goodsdetail_morecomment.setOnClickListener(this);
        relative_goodsdetail_canshu.setOnClickListener(this);
        iv_goodsdetail_back.setOnClickListener(this);

    }

    @Override
    public void init() {
        loadpager_goodsdetail.setLoadVisable();
        scrollView_goods.setOverScrollMode(View.OVER_SCROLL_NEVER);
        iGoodsDetailPresenter = new IGoodsDetailPresenterImpl(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (iGoodsDetailPresenter != null) {
            iGoodsDetailPresenter.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_goodsdetail_bus: //加入购物车
                iGoodsDetailPresenter.addBus();
                break;
            case R.id.relative_goodsdetail_buy://立即购买
                iGoodsDetailPresenter.addBuy();
                break;
            case R.id.relative_goodsdetail_bus_title://开启购物车
                UtilMethod.isLogin(this,BusActivity.class);
                break;
            case R.id.iv_goodsdetail_add://加
                iGoodsDetailPresenter.add();
                break;
            case R.id.iv_goodsdetail_delete://减
                iGoodsDetailPresenter.sub();
                break;
            case R.id.relative_goodsdetail_comment://评论
                iGoodsDetailPresenter.comment();
                break;
            case R.id.relative_goodsdetail_morecomment://更多评论
                iGoodsDetailPresenter.comment();
                break;
            case R.id.relative_goodsdetail_canshu://参数
               // UtilMethod.startIntent(this, ParamsActivity.class);
                iGoodsDetailPresenter.canshu();


                break;
            case R.id.iv_goodsdetail_back://关闭选择商品详情
                backActivity();
                break;

        }
    }

    @Override
    public void dismiss() {
        UtilMethod.backgroundAlpha(1f, this);
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_goodsdetail;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public TextView getTextViewAmount() {
        return tv_goodsdetail_amount;
    }


    @Override
    public TextView getTextViewBus() {
        return tv_goodsdetail_bus;
    }


    @Override
    public RecyclerView getRecyclyView() {
        return rv_goodsdetail;
    }

    @Override
    public TextView getTextViewMore() {
        return tv_goodsdetail_morecomment;
    }

    @Override
    public RelativeLayout getRelativeGoods() {
        return relative_goodsdetail;
    }

    @Override
    public ScrollView getScrollview() {
        return scrollView_goods;
    }

    @Override
    public TextView getTextViewAmount2() {
        return tv_goodsdetail_amount2;
    }

    @Override
    public ImageView getSimpleDraw() {
        return simpleDraweeView;
    }

    @Override
    public TextView getTextViewName() {
        return tv_name;
    }

    @Override
    public TextView getTextViewCount() {
        return tv_count;
    }

    @Override
    public TextView getTextViewPrice() {
        return tv_price;
    }

    @Override
    public TextView getTextViewComment() {
        return tv_shangpingdetail_comment3;
    }

    @Override
    public TextView getTextViewCommentStar() {
        return tv_shangpingdetail_comment2;
    }

    @Override
    public RatingBar getRabting() {
        return rb_detail_comment;
    }

    @Override
    public TextView getTextViewSum() {
        return tv_goodsdetail_sum;
    }


}
