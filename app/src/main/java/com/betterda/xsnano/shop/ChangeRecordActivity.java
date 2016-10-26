package com.betterda.xsnano.shop;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.javabean.OrderList;
import com.betterda.xsnano.javabean.ShopChange;
import com.betterda.xsnano.shop.model.ChangeRecord;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ChangeRecordActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_record;
    private LoadingPager loadpager_record;
    private List<ChangeRecord> list;
    private HeaderAndFooterRecyclerViewAdapter adapter;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_changerecord);
        topBar = (NormalTopBar) findViewById(R.id.topbar_changerecord);
        rv_record = (RecyclerView) findViewById(R.id.rv_changerecord);
        loadpager_record = (LoadingPager) findViewById(R.id.loadpager_changerecord);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        super.init();
        topBar.setTitle("商品兑换记录");

        list = new ArrayList<>();
        rv_record.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<ChangeRecord>(this, R.layout.item_recycleview_changerecord, list) {

            @Override
            public void convert(ViewHolder viewHolder, ChangeRecord changeRecord) {
                if (changeRecord != null) {
                    viewHolder.setText(R.id.tv_item_changrecord_name, changeRecord.getName());
                    viewHolder.setText(R.id.tv_item_changrecord_time, "兑换时间: " + changeRecord.getTime());
                    viewHolder.setText(R.id.tv_item_changrecord_price, "￥ " + changeRecord.getPrice());
                    SimpleDraweeView view = viewHolder.getView(R.id.sv_item_changrecord);
                    if (view != null) {
                        if (!TextUtils.isEmpty(changeRecord.getUrl())) {
                            view.setImageURI(Uri.parse(changeRecord.getUrl()));
                        }
                    }
                }
            }
        });
        rv_record.setAdapter(adapter);

        getData();

    }


    private void getData() {
        loadpager_record.setLoadVisable();
        RequestParams params = new RequestParams(Constants.URL_SHOP_CHANGE_JILU);
        params.addBodyParameter("account", CacheUtils.getString(getmActivity(), "number", ""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                List<ShopChange> listShopChange = GsonParse.getListShopChange(UtilMethod.getString(s));
                if (listShopChange != null) {
                    for (ShopChange change : listShopChange) {
                        if (change != null) {
                            ChangeRecord changeRecord = new ChangeRecord();
                            changeRecord.setTime(change.getExchange_date());
                            try {
                                String s1 = String.valueOf(change.getAgold());

                                if (s1.startsWith("-")) {
                                    String substring = s1.substring(1);
                                    changeRecord.setPrice(Float.parseFloat(substring));
                                } else {
                                    changeRecord.setPrice(Float.parseFloat(s1));
                                }

                            } catch (Exception e) {

                            }
                            changeRecord.setName(change.getName());
                            changeRecord.setUrl(UtilMethod.url(change.getLittle_picture()));
                            if (list != null) {
                                list.add(changeRecord);
                            }
                        }
                    }
                }


                if (loadpager_record != null) {
                    if (list != null) {
                        if (list.size() == 0) {
                            loadpager_record.setEmptyVisable();
                        } else {
                            loadpager_record.hide();
                        }
                    } else {
                        loadpager_record.setEmptyVisable();
                    }
                }


                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (loadpager_record != null) {
                    loadpager_record.setErrorVisable();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
