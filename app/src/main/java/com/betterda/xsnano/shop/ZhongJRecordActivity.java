package com.betterda.xsnano.shop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.javabean.Zhongj;
import com.betterda.xsnano.shop.model.ZhongjRecord;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.RecyclerViewStateUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.EndlessRecyclerOnScrollListener;
import com.betterda.xsnano.view.HeaderAndFooterRecyclerViewAdapter;
import com.betterda.xsnano.view.LoadingFooter;
import com.betterda.xsnano.view.NormalTopBar;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class ZhongJRecordActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_record;
    private HeaderAndFooterRecyclerViewAdapter adapter;
    private List<ZhongjRecord> list;
    private int  page=1;
    private String rows = "10";
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_zhongjrecoed);
        topBar = (NormalTopBar) findViewById(R.id.topbar_zhongjrecord);
        rv_record = (RecyclerView) findViewById(R.id.rv_zhongjrecord);
    }

    @Override
    public void init() {
        super.init();
        topBar.setOnBackListener(this);
        topBar.setTitle("开奖记录");
        list = new ArrayList<>();
        rv_record.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HeaderAndFooterRecyclerViewAdapter(new CommonAdapter<ZhongjRecord>(getmActivity(),R.layout.item_recycleview_zhongj,list) {

            @Override
            public void convert(ViewHolder viewHolder, ZhongjRecord zhongjRecord) {
                if (zhongjRecord != null) {
                    viewHolder.setText(R.id.tv_name, zhongjRecord.getName());
                    viewHolder.setText(R.id.tv_content, zhongjRecord.getAccount());
                    viewHolder.setText(R.id._tv_zhongj_time, zhongjRecord.getTime());

                    viewHolder.setOnClickListener(R.id.linear_zhongj, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        }
        );
        rv_record.setAdapter(adapter);
        rv_record.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                //滑到了最后,如果是正常状态才开始加载
                if (LoadingFooter.State.Normal == RecyclerViewStateUtils.getFooterViewState(rv_record)) {

                    RecyclerViewStateUtils.setFooterViewState(getmActivity(), rv_record, Constants.PAGE_SIZE, LoadingFooter.State.Loading, null);

                    page++;
                    getData();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        page = 1;
        getData();
    }



    private void getData() {
        RequestParams params = new RequestParams(Constants.URL_SHOP_zhongj);
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                if (list != null) {
                    if (page == 1) {
                        list.clear();
                    }
                }
                List<Zhongj> listZhongj = GsonParse.getListZhongj(UtilMethod.getString(s));
                if (listZhongj != null) {
                    for (Zhongj zhongj : listZhongj) {
                        if (zhongj != null) {
                            ZhongjRecord zhongjRecord = new ZhongjRecord();
                            zhongjRecord.setName("开奖商品:" + zhongj.getProduct_name());
                            zhongjRecord.setAccount("获奖用户:" + zhongj.getAccount());
                            zhongjRecord.setTime("开奖时间:" + zhongj.getCreate_date());
                            if (list != null) {
                                list.add(zhongjRecord);
                            }
                        }
                    }
                    RecyclerViewStateUtils.setLoad(listZhongj, rv_record, getmActivity());
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                RecyclerViewStateUtils.setFooterViewState(getmActivity(), rv_record,
                        Constants.PAGE_SIZE, LoadingFooter.State.NetWorkError, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
            }
        });


    }

    @Override
    public void onClick(View v) {
        backActivity();
    }
}
