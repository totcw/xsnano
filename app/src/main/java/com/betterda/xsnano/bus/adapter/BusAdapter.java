package com.betterda.xsnano.bus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.presenter.IBusPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/17.
 */
public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusHolder>{
    private IBusPresenter busPresenter;
    private Context context;

    public BusAdapter(IBusPresenter busPresenter, Context context) {
        this.busPresenter = busPresenter;
        this.context = context;
    }

    @Override
    public BusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BusHolder holder = new BusHolder(View.inflate(context, R.layout.item_recycleview_bus,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(BusHolder holder, int position) {
        busPresenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (busPresenter.getList() != null) {
            return busPresenter.getList().size();
        } else {
            return 0;
        }

    }

    public class BusHolder extends RecyclerView.ViewHolder{
        public View view;
        public ImageView iv,iv_add,iv_sub;
        public SimpleDraweeView sv;
        public TextView tv_name,tv_amount,tv_price;
        public RelativeLayout relative_item_bus;
        public BusHolder(View itemView) {
            super(itemView);
            view = itemView;
            relative_item_bus = (RelativeLayout) itemView.findViewById(R.id.relative_item_bus);
            iv = (ImageView) itemView.findViewById(R.id.iv_item_bus);
            iv_add = (ImageView) itemView.findViewById(R.id.iv_item_bus_add);
            iv_sub = (ImageView) itemView.findViewById(R.id.iv_item_bus_sub);
            sv = (SimpleDraweeView) itemView.findViewById(R.id.sv_item_bus);
            tv_name = (TextView) itemView.findViewById(R.id.tv_item_bus);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_item_bus_amount);
            tv_price = (TextView) itemView.findViewById(R.id.tv_item_bus_price);

        }
    }
}
