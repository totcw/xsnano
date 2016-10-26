package com.betterda.xsnano.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.bus.presenter.IBusPresenter;
import com.betterda.xsnano.order.presenter.IComfirmOrderPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ComfirmOrderAdapter extends RecyclerView.Adapter<ComfirmOrderAdapter.OrderHolder>{
    private IComfirmOrderPresenter comfirmOrderPresenter;
    private Context context;

    public ComfirmOrderAdapter(IComfirmOrderPresenter comfirmOrderPresenter, Context context) {
        this.comfirmOrderPresenter = comfirmOrderPresenter;
        this.context = context;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderHolder holder = new OrderHolder(View.inflate(context, R.layout.item_recycleview_comfirmorder,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        comfirmOrderPresenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (comfirmOrderPresenter.getList() != null) {
            return comfirmOrderPresenter.getList().size();
        } else {
            return 0;
        }

    }

    public class OrderHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sv;
        public TextView tv_name,tv_amount,tv_price;

        public OrderHolder(View itemView) {
            super(itemView);
            sv = (SimpleDraweeView) itemView.findViewById(R.id.sv_item_order);
            tv_name = (TextView) itemView.findViewById(R.id.tv_item_order_name);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_item_order_amount);
            tv_price = (TextView) itemView.findViewById(R.id.tv_item_order_price);

        }
    }
}
