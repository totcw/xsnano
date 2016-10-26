package com.betterda.xsnano.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.search.adapter.SearchAdapter;
import com.betterda.xsnano.search.presenter.ISearchPresenter;
import com.betterda.xsnano.search.presenter.ISearchPresenterImpl;
import com.betterda.xsnano.search.view.ISearchView;
import com.betterda.xsnano.view.LoadingPager;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

/**
 * 搜索的页面
 * Created by Administrator on 2016/5/3.
 */
public class SearchActivity extends BaseActivity implements ISearchView, View.OnClickListener {
    private ISearchPresenter iSearchPresenter;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private LoadingPager loadingPager;
    private ImageView iv_back,iv_search_delete,iv_search;
    private EditText et_search;

    @Override
    public void initView() {
        setContentView(R.layout.search_title);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        loadingPager = (LoadingPager) findViewById(R.id.loadpager_search);
        iv_back = (ImageView) findViewById(R.id.iv_search_back);
        iv_search_delete = (ImageView) findViewById(R.id.iv_search_delete);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        et_search = (EditText) findViewById(R.id.et_title_search);
    }

    @Override
    public void initListener() {
        super.initListener();
        iv_back.setOnClickListener(this);
        iv_search_delete.setOnClickListener(this);
        iv_search.setOnClickListener(this);
    }

    @Override
    public void init() {

        iSearchPresenter = new ISearchPresenterImpl(this);
      /*  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        adapter = new SearchAdapter(iSearchPresenter);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
            case R.id.iv_search_delete:
                iSearchPresenter.delete();
                break;
            case R.id.iv_search:
                iSearchPresenter.search();
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public EditText getEditText() {
        return et_search;
    }

    @Override
    public LoadingPager getLoadPager() {
        return loadingPager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
