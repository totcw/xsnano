package com.betterda.xsnano.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.home.HomeActivity;
import com.betterda.xsnano.login.presenter.ILoginPresenter;
import com.betterda.xsnano.login.presenter.ILoginPresenterImpl;
import com.betterda.xsnano.login.view.ILoginView;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.tencent.tauth.Tencent;

/**
 * 登录界面
 * Created by Administrator on 2016/4/28.
 */
public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
    private NormalTopBar topBar;
    private ILoginPresenter iLoginPresenter;
    private EditText et_login_number, et_login_pwd;
    private Button btn_login;
    private TextView tv_pwd, tv_register;
    private RelativeLayout relative_wx,relative_weibo,relative_qq;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        topBar = (NormalTopBar) findViewById(R.id.topbar_login);
        et_login_number = (EditText) findViewById(R.id.et_login_number);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_pwd = (TextView) findViewById(R.id.tv_login_pwd);
        tv_register = (TextView) findViewById(R.id.tv_login_register);
        relative_wx = (RelativeLayout) findViewById(R.id.relative_login_wx);
        relative_weibo = (RelativeLayout) findViewById(R.id.relative_login_weibo);
        relative_qq = (RelativeLayout) findViewById(R.id.relative_login_QQ);

    }

    @Override
    public void initListener() {
        btn_login.setOnClickListener(this);
        tv_pwd.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        relative_wx.setOnClickListener(this);
        relative_weibo.setOnClickListener(this);
        relative_qq.setOnClickListener(this);
    }

    @Override

    public void init() {
        topBar.setBackVisibility(false);
        topBar.setTitle("登录");

        iLoginPresenter = new ILoginPresenterImpl(this);
        iLoginPresenter.start();

        et_login_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iLoginPresenter.number(s);
            }
        });

        et_login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iLoginPresenter.pwd(s);
            }
        });
    }





    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.btn_login:
                iLoginPresenter.login();
                break;
            case R.id.tv_login_pwd:
                iLoginPresenter.editPwd();
                break;
            case R.id.tv_login_register:
                iLoginPresenter.register();
                break;
            case R.id.relative_login_wx:
                iLoginPresenter.wxLogin();
                break;
            case R.id.relative_login_weibo://微博登录
                iLoginPresenter.weiboLogin();
                break;
            case R.id.relative_login_QQ://QQ登录
                iLoginPresenter.QQlogin();
                break;

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iLoginPresenter.weiboCallBack(requestCode, resultCode, data);
        iLoginPresenter.QqCallBack(requestCode, resultCode, data);


    }

    @Override
    public void onBackPressed() {
        //重新后退键 重新回到主页
     //   UtilMethod.startIntent(this, HomeActivity.class);
        finish();
        super.onBackPressed();
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }




    @Override
    public Button getBtnLogin() {
        return btn_login;
    }

    @Override
    public EditText getEditLogin() {
        return et_login_number;
    }

    @Override
    public EditText getEditLoginPwd() {
        return et_login_pwd;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iLoginPresenter.onDestroy();
    }
}
