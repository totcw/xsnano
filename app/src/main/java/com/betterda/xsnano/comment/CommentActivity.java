package com.betterda.xsnano.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.comment.presenter.ICommentPresenter;
import com.betterda.xsnano.comment.presenter.ICommentPresenterImpl;
import com.betterda.xsnano.comment.view.ICommentView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 用品对应的所有评论
 * Created by Administrator on 2016/5/17.
 */
public class CommentActivity extends BaseActivity implements ICommentView, View.OnClickListener {
    private NormalTopBar topBar;
    private LoadingPager loadpager_comment;
    private RecyclerView rv_comment;
    private ICommentPresenter commentPresenter;
    private LinearLayout linear_comment;


    @Override
    public void initView() {
        setContentView(R.layout.activity_comment);
        topBar = (NormalTopBar) findViewById(R.id.topbar_comment);
        loadpager_comment = (LoadingPager) findViewById(R.id.loadpager_comment);
        rv_comment = (RecyclerView) findViewById(R.id.rv_comment);
        linear_comment = (LinearLayout) findViewById(R.id.linear_comment);
    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("用户评论");
        loadpager_comment.setLoadVisable();

        commentPresenter = new ICommentPresenterImpl(this);
        commentPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadpager_comment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
        }
    }

    @Override
    public RecyclerView getRecycleview() {
        return rv_comment;
    }
}
