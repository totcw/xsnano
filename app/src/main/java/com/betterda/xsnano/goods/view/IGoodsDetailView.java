package com.betterda.xsnano.goods.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betterda.xsnano.BaseView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IGoodsDetailView extends BaseView {



    TextView getTextViewAmount();



    TextView getTextViewBus();



    void closePopupWindow();

    RecyclerView getRecyclyView();

    TextView getTextViewMore();

    RelativeLayout getRelativeGoods();

    ScrollView getScrollview();

    TextView getTextViewAmount2();

    ImageView getSimpleDraw();
    //商品名
    TextView getTextViewName();
    //销售量
    TextView getTextViewCount();
    //商品价格
    TextView getTextViewPrice();

    TextView getTextViewComment();

    TextView getTextViewCommentStar();

    RatingBar getRabting();

    TextView getTextViewSum();
}
