package com.betterda.xsnano.comment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.bus.model.Bus;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

import org.xutils.http.RequestParams;

/**
 * 添加评论的 页面
 * Created by Administrator on 2016/6/1.
 */
public class AddCommentActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topbar;
    private RatingBar rb1,rb3;
    private EditText et_content;
    private Button btn_commit;
    private String orderid,productid;//订单id和商品id
    private float rate1 ,rate2;
    private String shopId; //店铺id

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addcomment);
        topbar = (NormalTopBar) findViewById(R.id.topbar_addcomment);
        rb1 = (RatingBar) findViewById(R.id.rb_addcomment_comment);

        rb3 = (RatingBar) findViewById(R.id.rb_addcomment_comment3);
        et_content = (EditText) findViewById(R.id.et_addcomment_comment);
        btn_commit = (Button) findViewById(R.id.btn_addcomment_commit);
    }

    @Override
    public void initListener() {
        super.initListener();
        topbar.setOnBackListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        topbar.setTitle("用户评价");

        Intent intent = getIntent();
        if (null != intent) {
            orderid = intent.getStringExtra("orderid");
            productid = intent.getStringExtra("productid");
            shopId = intent.getStringExtra("shopId");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.btn_addcomment_commit:

                commit();
                break;

        }
    }

    private void commit() {

        String content = et_content.getText().toString().trim();
        rate1 = rb1.getRating();
        rate2 = rb3.getRating();
        if (TextUtils.isEmpty(content)) {
            UtilMethod.Toast(this,"请输入评论的内容");
            return;
        }
        if (rate1 < 1) {
            UtilMethod.Toast(this,"请对商品进行评分");
            return;
        }
        if (rate1 < 1) {
            UtilMethod.Toast(this,"请对服务进行评分");
            return;
        }


        RequestParams params = new RequestParams(Constants.URL__COMMENT_ADD);

        params.addBodyParameter("productId", productid);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        params.addBodyParameter("orderId", orderid);
        params.addBodyParameter("shopId", shopId);
        params.addBodyParameter("content", content);
        params.addBodyParameter("serivceStar", rate1+"");
        params.addBodyParameter("productStar", rate2+"");
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {
                            AddCommentActivity.this.finish();
                        }
                        UtilMethod.Toast(AddCommentActivity.this,resultMsg);
                    }
                });



            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                UtilMethod.Toast(AddCommentActivity.this,"评价失败");
            }
        });
    }
}
