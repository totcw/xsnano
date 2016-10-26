package com.betterda.xsnano.tianjiaji.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.tianjiaji.presenter.ITianJiaJiPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Administrator on 2016/5/16.
 */
public class TianJiaJiAdapter extends RecyclerView.Adapter<TianJiaJiAdapter.TianJiaJiHolder> {
    private ITianJiaJiPresenter iTianJiaJiPresenter;
    private Context context;

    public TianJiaJiAdapter(ITianJiaJiPresenter iTianJiaJiPresenter, Context context) {
        this.iTianJiaJiPresenter = iTianJiaJiPresenter;
        this.context = context;
    }

    @Override
    public TianJiaJiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TianJiaJiHolder holder = new TianJiaJiHolder(View.inflate(context, R.layout.item_recycleview_tianjiaji, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(TianJiaJiHolder holder, int position) {
        iTianJiaJiPresenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (null != iTianJiaJiPresenter.getList()) {
            return iTianJiaJiPresenter.getList().size();
        } else {
            return 0;
        }
    }

    public class TianJiaJiHolder extends RecyclerView.ViewHolder {
        public View view;
        public SimpleDraweeView simpleDraweeView;
        public TextView tv_name, tv_money, tv_amount, tv_comment;

        public TianJiaJiHolder(View itemView) {
            super(itemView);
            view = itemView;
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.iv_item_tianjiaji);
            tv_name = (TextView) itemView.findViewById(R.id.tv_tianjiaji_name);
            tv_money = (TextView) itemView.findViewById(R.id.tv_tianjiaji_money);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_tianjiaji_amount);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_tianjiaji_comment);
        }
    }
}
