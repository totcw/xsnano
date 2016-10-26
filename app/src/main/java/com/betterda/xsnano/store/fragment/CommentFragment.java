package com.betterda.xsnano.store.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.store.presenter.IShangCommentPresenterImpl;
import com.betterda.xsnano.store.presenter.IShangPCommentPresenter;
import com.betterda.xsnano.store.view.IShangPingView;
import com.betterda.xsnano.store.view.IShangpCommentView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.StoreRecycleView;

/**
 * 店铺评论
 * Created by Administrator on 2016/4/21.
 */
public class CommentFragment extends BaseFragment implements IShangpCommentView{
    private LoadingPager loadpager_comment;
    public StoreRecycleView rv_fragment_comment;
    private TextView tv_sum,tv_service_comment,tv_yonghu_comment;
    private RatingBar rb_commnet_service,rb_comment_youhu;
    private IShangPCommentPresenter iShangPCommentPresenter;
    private LinearLayout linear_comment;
    private String id;
    private double score,score2;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_comment,null);
        loadpager_comment = (LoadingPager) view.findViewById(R.id.loadpager_fragment_comment);
        rv_fragment_comment = (StoreRecycleView) view.findViewById(R.id.rv_fragment_comment);
        tv_sum = (TextView) view.findViewById(R.id.tv_fragment_comment_sum);
        tv_service_comment = (TextView) view.findViewById(R.id.tv_fragment_comment_amout);
        tv_yonghu_comment = (TextView) view.findViewById(R.id.tv_fragment_comment_shangpin);
        rb_commnet_service = (RatingBar) view.findViewById(R.id.rb_fragment_comment_service);
        rb_comment_youhu = (RatingBar) view.findViewById(R.id.rb_fragment_comment_shangpin);
        linear_comment = (LinearLayout) view.findViewById(R.id.linear_fragment_comment);
        return view;
    }

    @Override
    public void initData() {
        loadpager_comment.setLoadVisable();
        Bundle arguments = getArguments();
        if (null != arguments) {
            id = arguments.getString("id");
            score = arguments.getDouble("score");
            score2 = arguments.getDouble("servicesore");


        }
        rv_fragment_comment.setOverScrollMode(View.OVER_SCROLL_NEVER);
        iShangPCommentPresenter = new IShangCommentPresenterImpl(this,id,score,score2);
        iShangPCommentPresenter.start();

    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_comment;
    }

    @Override
    public StoreRecycleView getRecycleView() {
        return rv_fragment_comment;
    }

    @Override
    public TextView getTextViewSum() {
        return tv_sum;
    }

    @Override
    public TextView getTextViewService() {
        return tv_service_comment;
    }

    @Override
    public TextView getTextViewyonghu() {
        return tv_yonghu_comment;
    }

    @Override
    public RatingBar getRatingBaryonghu() {
        return rb_comment_youhu;
    }

    @Override
    public RatingBar getRatingBarService() {
        return rb_commnet_service;
    }

    @Override
    public LinearLayout getLinearLayout() {
        return linear_comment;
    }
}
