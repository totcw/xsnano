package com.betterda.xsnano.search.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.betterda.xsnano.BaseView;
import com.betterda.xsnano.view.LoadingPager;

/**
 * Created by Administrator on 2016/5/11.
 */
public interface ISearchView extends BaseView{

    EditText getEditText();

    RecyclerView getRecyclerView();
}
