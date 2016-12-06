package com.betterda.xsnano.wallet;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.interfac.ParserGsonInterface;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ShapeLoadingDialog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 金币兑换
 * Created by lyf on 2016/6/14.
 */
@ContentView(R.layout.activity_jinbichange)
public class JinBiChangeAcitivty extends BaseActivity {
    @ViewInject(R.id.topbar_jinbichang)
    private NormalTopBar topBar;
    @ViewInject(R.id.tv_jinbichange_money)
    private TextView tv_jinbi;
    @ViewInject(R.id.tv_jinbichange_all)
    private TextView tv_all;
    @ViewInject(R.id.et_jinbichange_money)
    private EditText et_money;
    @ViewInject(R.id.btn_myjinbichange_change)
    private Button btn_change;
    @ViewInject(R.id.iv_chongzhi_delete)
    private ImageView iv_delete;

    private int amount; //可兑换的数量
    private String amount2;  //兑换的数量
    private ShapeLoadingDialog dialog;
    @Event(R.id.bar_back)
    private void onBackClick(View view) {
        backActivity();
    }
    @Event(R.id.btn_myjinbichange_change)
    private void onCommitClick(View view) {
        if (btn_change.isSelected()) {
            try {
                int count = Integer.parseInt(amount2);
                if (count * 2 > amount) {
                    UtilMethod.Toast(this, "银币数量不足");
                    return;
                }
                UtilMethod.showDialog(this,dialog);
                getData(amount2);
            } catch (Exception e) {
                UtilMethod.dissmissDialog(this,dialog);
                UtilMethod.Toast(this,"兑换失败");
            }

        }
    }

    private void getData(String money) {
        RequestParams params = new RequestParams(Constants.URL_GOLD_CHANGE);
        String account = CacheUtils.getString(getmActivity(), "number", "");

        params.addBodyParameter("account",account);
        params.addBodyParameter("goldCount",money);
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                System.out.println(s);
                UtilMethod.dissmissDialog(getmActivity(), dialog);
                GsonParse.parser(UtilMethod.getString(s), new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {
                            JinBiChangeAcitivty.this.finish();
                        }

                        UtilMethod.Toast(JinBiChangeAcitivty.this, resultMsg);
                    }
                });
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                UtilMethod.dissmissDialog(getmActivity(), dialog);
                UtilMethod.Toast(JinBiChangeAcitivty.this, "兑换失败");
            }
        });
    }

    @Event(R.id.tv_jinbichange_all)
    private void onAllClick(View view) {
            UtilMethod.showDialog(this,dialog);
            getData(String.valueOf(amount));


    }
    @Event(R.id.iv_chongzhi_delete)
    private void onDeleteClick(View view) {
        et_money.setText("");
    }
    @Override
    public void init() {
        super.init();
        topBar.setTitle("金币兑换");

        Intent intent = getIntent();
        if (null != intent) {
            amount = Integer.parseInt(intent.getStringExtra("icon"));
            tv_jinbi.setText(amount + "");
        }
        dialog = UtilMethod.createDialog(this, "");

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //如果第一个输入的0就直接不能在输入了
                if ("0".equals(s.toString())) {

                    et_money.setEnabled(false);
                } else {
                    et_money.setEnabled(true);
                }


                amount2 = s.toString();
                if (!TextUtils.isEmpty(amount2)) {
                    btn_change.setSelected(true);
                } else {
                    btn_change.setSelected(false);
                }
            }
        });
    }


}
