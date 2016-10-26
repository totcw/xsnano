package com.betterda.xsnano.findpwd;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.findpwd.presenter.IFindPwdPrensenterImpl;
import com.betterda.xsnano.findpwd.presenter.IFindPwdPresenter;
import com.betterda.xsnano.findpwd.view.IFindPwdView;
import com.betterda.xsnano.register.presenter.IRegisterPresenter;
import com.betterda.xsnano.register.presenter.IRegisterPresenterImpl;
import com.betterda.xsnano.register.view.IRegisterView;
import com.betterda.xsnano.view.CountDown;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 找回密码页面
 * Created by Administrator on 2016/5/25.
 */
public class FindPwdActivity extends BaseActivity implements IFindPwdView, View.OnClickListener, CountDown.onSelectListener {
    private NormalTopBar topBar;
    private CountDown countDown;
    private EditText et_number,et_yzm,et_pwd;
    private Button btn_register;
    private IFindPwdPresenter iFindPwdPresenter;
    @Override
    public void initView() {
        setContentView(R.layout.activity_findpwd);
        topBar = (NormalTopBar) findViewById(R.id.topbar_findpwd);
        countDown = (CountDown) findViewById(R.id.countdown_findpwd);
        et_number = (EditText) findViewById(R.id.et_findpwd_number);
        et_yzm = (EditText) findViewById(R.id.et_findpwd_yzm);
        et_pwd = (EditText) findViewById(R.id.et_findpwd_pwd);
        btn_register = (Button) findViewById(R.id.btn_findpwd);
    }

    @Override
    public void initListener() {
        topBar.setOnBackListener(this);
        btn_register.setOnClickListener(this);
        countDown.setOnClickListener(this);
        //给输入框设置动态监听
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iFindPwdPresenter.number(s);
            }
        });

        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iFindPwdPresenter.pwd(s);
            }
        });

        et_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iFindPwdPresenter.yzm(s);
            }
        });

    }

    @Override
    public void init() {
        topBar.setTitle("找回密码");

        //设置倒计时的回调接口
        countDown.setListener(this);

        iFindPwdPresenter = new IFindPwdPrensenterImpl(this);
        iFindPwdPresenter.start();
    }

    @Override
    public LoadingPager getLoadPager() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                finish();
                break;
            case R.id.btn_findpwd:
                iFindPwdPresenter.findpwd();
                break;
            case R.id.countdown_findpwd:
                iFindPwdPresenter.countDown();
                break;
        }
    }

    @Override
    public Button getBtnRegister() {
        return btn_register;
    }

    @Override
    public CountDown getCountDown() {
        return countDown;
    }

    /**
     * 倒计时的回调方法
     * @param countDown
     */
    @Override
    public void setSelect(CountDown countDown) {
        iFindPwdPresenter.setSelect(countDown);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.ondestroy();
            countDown = null;
        }
    }
}
