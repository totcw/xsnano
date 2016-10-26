package com.betterda.xsnano.wode;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.fragment.BaseFragment;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.view.IndicatorView;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.wode.presenter.IMyPresenter;
import com.betterda.xsnano.wode.presenter.IMyPresenterImpl;
import com.betterda.xsnano.wode.view.IMyView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 我的界面
 * Created by Administrator on 2016/4/21.
 */
public class MyFragment extends BaseFragment implements IMyView, View.OnClickListener {
    private IMyPresenter iMyPresenter;
    private RelativeLayout relative_setting, relative_tuikuan, relative_jiayouka,
            relative_bus, relative_address, relative_kefu, relative_fankui, relative_touxiang, relative_my_all;
    private SimpleDraweeView sv_touxiang;
    private TextView tv_number, tv_youhui, tv_car;
    private LinearLayout linear_youhui, linear_car;
    private IndicatorView idv_pay, idv_send, idv_bring, idv_take, idv_comment;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        relative_setting = (RelativeLayout) view.findViewById(R.id.relative_my_setting);
        relative_tuikuan = (RelativeLayout) view.findViewById(R.id.relative_my_tuikuan);
        relative_jiayouka = (RelativeLayout) view.findViewById(R.id.relative_my_jiayouka);
        relative_bus = (RelativeLayout) view.findViewById(R.id.relative_my_bus);
        relative_address = (RelativeLayout) view.findViewById(R.id.relative_my_address);
        relative_kefu = (RelativeLayout) view.findViewById(R.id.relative_my_kefu);
        relative_fankui = (RelativeLayout) view.findViewById(R.id.relative_my_fankui);
        relative_touxiang = (RelativeLayout) view.findViewById(R.id.relative_my_touxiang);
        relative_my_all = (RelativeLayout) view.findViewById(R.id.relative_my_all);
        sv_touxiang = (SimpleDraweeView) view.findViewById(R.id.sv_touxiang);
        tv_number = (TextView) view.findViewById(R.id.tv_my_number);
        tv_youhui = (TextView) view.findViewById(R.id.tv_my_youhui);
        tv_car = (TextView) view.findViewById(R.id.tv_my_car);
        linear_youhui = (LinearLayout) view.findViewById(R.id.linear_my_youhui);
        linear_car = (LinearLayout) view.findViewById(R.id.linear_my_car);
        idv_pay = (IndicatorView) view.findViewById(R.id.idv_pay);
        idv_send = (IndicatorView) view.findViewById(R.id.idv_send);
        idv_bring = (IndicatorView) view.findViewById(R.id.idv_bring);
        idv_take = (IndicatorView) view.findViewById(R.id.idv_take);
        idv_comment = (IndicatorView) view.findViewById(R.id.idv_comment);
        return view;
    }

    @Override
    public void initListenr() {
        relative_setting.setOnClickListener(this);
        relative_tuikuan.setOnClickListener(this);
        relative_jiayouka.setOnClickListener(this);
        relative_bus.setOnClickListener(this);
        relative_address.setOnClickListener(this);
        relative_kefu.setOnClickListener(this);
        relative_fankui.setOnClickListener(this);
        relative_touxiang.setOnClickListener(this);
        relative_my_all.setOnClickListener(this);
        linear_youhui.setOnClickListener(this);
        linear_car.setOnClickListener(this);
        idv_pay.setOnClickListener(this);
        idv_send.setOnClickListener(this);
        idv_bring.setOnClickListener(this);
        idv_take.setOnClickListener(this);
        idv_comment.setOnClickListener(this);
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public void initData() {
        super.initData();
        iMyPresenter = new IMyPresenterImpl(this);


        idv_pay.setIvBackground(R.mipmap.dpay, R.mipmap.dpay);
        idv_send.setIvBackground(R.mipmap.send, R.mipmap.send);
        idv_bring.setIvBackground(R.mipmap.bring, R.mipmap.bring);
        idv_take.setIvBackground(R.mipmap.take, R.mipmap.take);
        idv_comment.setIvBackground(R.mipmap.dcomment,R.mipmap.dcomment);
        idv_pay.setLineBackground(0xff909090, 0xff909090);
        idv_send.setLineBackground(0xff909090, 0xff909090);
        idv_bring.setLineBackground(0xff909090, 0xff909090);
        idv_take.setLineBackground(0xff909090, 0xff909090);
        idv_comment.setLineBackground(0xff909090,0xff909090);
        idv_pay.setTitle("待发货");
        idv_send.setTitle("待付款");
        idv_bring.setTitle("待收货");
        idv_take.setTitle("待提货");
        idv_comment.setTitle("待评价");

    }

    @Override
    public void onStart() {
        super.onStart();
        if (iMyPresenter != null) {
            iMyPresenter.start();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_my_setting:
                iMyPresenter.setting();
                break;
            case R.id.relative_my_tuikuan:
                iMyPresenter.tuikuan();
                break;
            case R.id.relative_my_touxiang:
                iMyPresenter.touxiang();
                break;
            case R.id.relative_my_jiayouka:
                iMyPresenter.jiayouka();
                break;
            case R.id.relative_my_bus:
                iMyPresenter.bus();
                break;
            case R.id.relative_my_address:
                iMyPresenter.address();
                break;
            case R.id.relative_my_all:
                iMyPresenter.all();
                break;
            case R.id.relative_my_kefu:
                iMyPresenter.kefu();
                break;
            case R.id.relative_my_fankui:
                iMyPresenter.fankui();
                break;
            case R.id.linear_my_youhui:
                iMyPresenter.youhui();
                break;
            case R.id.linear_my_car:
                iMyPresenter.car();
                break;
            case R.id.idv_pay:
                iMyPresenter.pay();
                break;
            case R.id.idv_send:
                iMyPresenter.send();
                break;
            case R.id.idv_bring:
                iMyPresenter.bring();
                break;
            case R.id.idv_take:
                iMyPresenter.take();
                break;
            case R.id.idv_comment:
                iMyPresenter.comment();
                break;
        }
    }

    @Override
    public SimpleDraweeView getSimpleDraweeView() {
        return sv_touxiang;
    }

    @Override
    public TextView getTextViewNumber() {
        return tv_number;
    }
}
