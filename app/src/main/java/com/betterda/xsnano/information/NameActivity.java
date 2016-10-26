package com.betterda.xsnano.information;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

/**
 * Created by lyf
 */
public class NameActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private EditText et_name;
    private Button btn_editname_name;
    private int amount;
    @Override
    public void initView() {
        setContentView(R.layout.activity_name);
        topBar = (NormalTopBar) findViewById(R.id.topbar_name);
        et_name = (EditText) findViewById(R.id.et_name_name);
        btn_editname_name = (Button) findViewById(R.id.btn_editname_name);
    }

    @Override
    public void initListener() {
        super.initListener();
        btn_editname_name.setOnClickListener(this);
        topBar.setOnBackListener(this);
    }

    @Override
    public void init() {
        super.init();
        topBar.setTitle("修改昵称");
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //start表示在输入这次的数之前有几个数了
                amount = start + after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.btn_editname_name:
                String name = et_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    UtilMethod.Toast(this,"昵称不能为空");
                    return;
                }
                if (amount > 8) {
                    UtilMethod.Toast(this,"昵称不能超过8个字");
                    return;
                }
                //检测只能含有英文,数字,中文
                boolean is = name.matches("^[a-z0-9A-Z\\u4e00-\\u9fa5]+$");
                if (!is) {
                    UtilMethod.Toast(this,"含有非法字符");
                    return;
                }

               getData(name);
                break;
        }
    }

    private void getData(final String name) {
        final RequestParams params = new RequestParams(Constants.URL_INFORMATION_UPDATE);
        params.addBodyParameter("account",CacheUtils.getString(this,"number",""));
        params.addBodyParameter("nickName",et_name.getText().toString().trim());

        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {

                String substring = UtilMethod.getString(s);
                GsonParse.parser(substring, new ParserGsonInterface() {
                    @Override
                    public void onSuccess(String result, String resultMsg) {
                        if ("true".equals(result)) {
                            UtilMethod.Toast(NameActivity.this, "修改成功");
                            String number = CacheUtils.getString(NameActivity.this, "number", "");
                            if (!TextUtils.isEmpty(number)) {
                                //将昵称存放到本地
                                CacheUtils.putString(NameActivity.this, number + "name", name);
                                finish();
                            }

                        } else {
                            UtilMethod.Toast(NameActivity.this,"修改失败");
                        }
                    }
                });


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                UtilMethod.Toast(NameActivity.this,"修改失败");
            }
        });
    }
}
