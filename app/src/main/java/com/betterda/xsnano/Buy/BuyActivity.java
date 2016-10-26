package com.betterda.xsnano.Buy;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.betterda.xsnano.Buy.presenter.IBuyPresenter;
import com.betterda.xsnano.Buy.presenter.IBuyPresenterImpl;
import com.betterda.xsnano.Buy.view.IBuyView;
import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.ScrollYScrollView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/4/26.
 */
public class BuyActivity extends BaseActivity implements IBuyView, ScrollYScrollView.OnScrollListener, View.OnClickListener {

    private LinearLayout linearLayout;
    /**
     * 自定义的MyScrollView
     */
    private ScrollYScrollView myScrollView;
    /**
     * 在MyScrollView里面的购买布局
     */
    private LinearLayout mBuyLayout;
    /**
     * 位于顶部的购买布局
     */
    private LinearLayout mTopBuyLayout;

    private IBuyPresenter iBuyPresenter;

    private RecyclerView recyclerView;
    private List<String> list;
    @Override
    public void initView() {
        setContentView(R.layout.activity_buy);
        myScrollView = (ScrollYScrollView) findViewById(R.id.scrollView);
        mBuyLayout = (LinearLayout) findViewById(R.id.buy);
        mTopBuyLayout = (LinearLayout) findViewById(R.id.top_buy_layout);
        linearLayout = (LinearLayout) findViewById(R.id.parent_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_buy);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }


    }

    @Override
    public void initListener() {
        myScrollView.setOnScrollListener(this);
        mBuyLayout.setOnClickListener(this);
        mTopBuyLayout.setOnClickListener(this);
    }

    @Override
    public void init() {
        iBuyPresenter = new IBuyPresenterImpl(this);
        iBuyPresenter.show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_homelistview, list) {

            @Override
            public void convert(final ViewHolder viewHolder, String s) {
                viewHolder.setOnClickListener(R.id.linear_homelistvew, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    UtilMethod.Toast(BuyActivity.this, String.valueOf(viewHolder.getPosition()));
                    }
                });
            }
        });
        myScrollView.post(new Runnable() {
            //让scrollview跳转到顶部，必须放在runnable()方法中
            @Override
            public void run() {
                myScrollView.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        iBuyPresenter.scroll(scrollY);
    }

    @Override
    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    @Override
    public LinearLayout getBuyLayout() {
        return mBuyLayout;
    }

    @Override
    public LinearLayout getTopBuyLayout() {
        return mTopBuyLayout;
    }


    @Override
    public void scroll() {
        //这一步很重要，使得上面的购买布局和下面的购买布局重合,因为一开始srocllview没滑动,不会执行onscroll方法
        onScroll(myScrollView.getScrollY());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy:
                UtilMethod.Toast(this,"buy");
                break;
            case R.id.top_buy_layout:
              myScrollView.smoothto();
                break;
        }
    }
}
