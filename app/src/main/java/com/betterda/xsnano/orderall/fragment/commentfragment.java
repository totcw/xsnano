package com.betterda.xsnano.orderall.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.orderall.presenter.ICommentFragmentPresenter;
import com.betterda.xsnano.orderall.presenter.ICommentFragmentPresenterImpl;
import com.betterda.xsnano.orderall.view.ICommentFragmentView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * 待评价的fragment
 * Created by Administrator on 2016/5/27.
 */
public class commentfragment extends BaseFragment implements ICommentFragmentView {
    private LoadingPager loadpager_comment;
    private RecyclerView rv_comment;
    private ICommentFragmentPresenter iCommentFragmentPresenter;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_dcomment, null);
        loadpager_comment = (LoadingPager) view.findViewById(R.id.loadpager_fragment_dcomment);
        rv_comment = (RecyclerView) view.findViewById(R.id.rv_fragment_dcomment);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        iCommentFragmentPresenter = new ICommentFragmentPresenterImpl(this);
        iCommentFragmentPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_comment;
    }

    @Override
    public RecyclerView getRecycleView() {
        return rv_comment;
    }
}
