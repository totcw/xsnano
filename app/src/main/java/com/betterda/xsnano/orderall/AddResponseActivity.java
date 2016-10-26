package com.betterda.xsnano.orderall;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * 添加售后原因
 * Created by Administrator on 2016/6/1.
 */
public class AddResponseActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topbar;

    private EditText et_content;
    private Button btn_commit;
    private String orderid;


    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addresponse);
        topbar = (NormalTopBar) findViewById(R.id.topbar_addresponse);
        et_content = (EditText) findViewById(R.id.et_addresponse_comment);
        btn_commit = (Button) findViewById(R.id.btn_addresponse_commit);
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
        topbar.setTitle("售后");
        Intent intent = getIntent();
        if (intent != null) {
            orderid = intent.getStringExtra("orderid");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.btn_addresponse_commit:

                commit();
                break;

        }
    }

    private void commit() {

        String content = et_content.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            UtilMethod.Toast(this,"请输入售后的原因");
            return;
        }

        getData3(content);
    }


    /**
     * 申请售后
     */
    private void getData3(String content) {
        RequestParams params = new RequestParams(Constants.URL__ORDER_TUIKUAN);
        params.addBodyParameter("account", CacheUtils.getString(this, "number", ""));
        params.addBodyParameter("orderNum", orderid);
        params.addBodyParameter("remark",content);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        UtilMethod.Toast(getmActivity(), resultMsg);
                        AddResponseActivity.this.finish();

                    }
                });

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(getmActivity(), "申请售后失败");

            }
        });
    }
}
