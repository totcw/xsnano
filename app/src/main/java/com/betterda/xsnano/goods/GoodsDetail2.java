package com.betterda.xsnano.goods;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class GoodsDetail2 extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private RecyclerView rv_goods;
    private List<String> list;
    private String images;

    @Override

    public void initView() {
        super.initView();
        setContentView(R.layout.activity_goodsdetail2);
        topBar = (NormalTopBar) findViewById(R.id.topbar_goods2);
        rv_goods = (RecyclerView) findViewById(R.id.rv_goodsdetail2);
    }

    @Override
    public void init() {
        super.init();
        topBar.setOnBackListener(this);
        topBar.setTitle("图文详细");
        list = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            images = intent.getStringExtra("images");
        }
        if (images != null) {
            String[] split = images.split(",");
            for (String s : split) {
                list.add(UtilMethod.url(s));
            }
        }


        rv_goods.setLayoutManager(new LinearLayoutManager(this));
        rv_goods.setAdapter(new CommonAdapter<String>(getmActivity(),R.layout.item_recycleview_tuwenxiangxi,list) {
            @Override
            public void convert(ViewHolder viewHolder, String string) {
                if (!TextUtils.isEmpty(string)) {
                    SimpleDraweeView sv = viewHolder.getView(R.id.sv_goodsdetail2);
                    if (sv != null) {
                        sv.setImageURI(Uri.parse(string));
                    }
                }

            }


        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
