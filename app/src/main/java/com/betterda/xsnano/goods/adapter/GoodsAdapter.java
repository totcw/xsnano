package com.betterda.xsnano.goods.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.goods.presenter.IGoodsDetailPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/17.
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder>{
    private IGoodsDetailPresenter goodsDetailPresenter;
    private Context context;

    public GoodsAdapter(IGoodsDetailPresenter goodsDetailPresenter, Context context) {
        this.goodsDetailPresenter = goodsDetailPresenter;
        this.context = context;
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsHolder holder = new GoodsHolder(View.inflate(context, R.layout.item_recycleview_comment, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        goodsDetailPresenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (goodsDetailPresenter.getList() != null) {
            return goodsDetailPresenter.getList().size();
        } else {
            return  0;
        }
    }

    public class GoodsHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sv;
        public TextView tv_name,tv_time,tv_content;
        public View view;

        public GoodsHolder(View itemView) {
            super(itemView);
            view = itemView;
            sv = (SimpleDraweeView) itemView.findViewById(R.id.sv_comment);
            tv_name = (TextView) itemView.findViewById(R.id.tv_comment_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_comment_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
        }
    }
}
