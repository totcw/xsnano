package com.betterda.xsnano.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.betterda.xsnano.search.presenter.ISearchPresenter;

/**
 *搜索的适配器
 * Created by Administrator on 2016/5/11.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private ISearchPresenter iSearchPresenter;

    public SearchAdapter(ISearchPresenter iSearchPresenter) {
        this.iSearchPresenter = iSearchPresenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = iSearchPresenter.getView();
        if (null != view) {
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (iSearchPresenter != null) {
            iSearchPresenter.setData(holder,position);
        }

    }

    @Override
    public int getItemCount() {
        if (null != iSearchPresenter.getList()) {
            return iSearchPresenter.getList().size();
        } else {

            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
