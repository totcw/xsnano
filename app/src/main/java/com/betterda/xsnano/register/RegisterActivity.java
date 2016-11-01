package com.betterda.xsnano.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.register.presenter.IRegisterPresenter;
import com.betterda.xsnano.register.presenter.IRegisterPresenterImpl;
import com.betterda.xsnano.register.view.IRegisterView;
import com.betterda.xsnano.view.CountDown;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 注册页面
 * Created by Administrator on 2016/5/25.
 */
public class RegisterActivity extends BaseActivity implements IRegisterView, View.OnClickListener, CountDown.onSelectListener {
    private NormalTopBar topBar;
    private CountDown countDown;
    private EditText et_number,et_yzm,et_pwd,et_pwd2;
    private Button btn_register;
    private IRegisterPresenter iRegisterPresenter;
    @Override
    public void initView() {
        setContentView(R.layout.activity_register);
        topBar = (NormalTopBar) findViewById(R.id.topbar_register);
        countDown = (CountDown) findViewById(R.id.countdown_register);
        et_number = (EditText) findViewById(R.id.et_register_number);
        et_yzm = (EditText) findViewById(R.id.et_register_yzm);
        et_pwd = (EditText) findViewById(R.id.et_register_pwd);
        et_pwd2 = (EditText) findViewById(R.id.et_register_pwd2);
        btn_register = (Button) findViewById(R.id.btn_register);
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
                iRegisterPresenter.number(s);
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
                iRegisterPresenter.pwd(s);
            }
        });
        et_pwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                iRegisterPresenter.pwd2(s);
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
                iRegisterPresenter.yzm(s);
            }
        });

    }

    @Override
    public void init() {
        topBar.setTitle("注册");

        //设置倒计时的回调接口
        countDown.setListener(this);

        iRegisterPresenter = new IRegisterPresenterImpl(this);
        iRegisterPresenter.start();
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
            case R.id.btn_register:
                iRegisterPresenter.register();
                break;
            case R.id.countdown_register:
                iRegisterPresenter.countDown();

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
        iRegisterPresenter.setSelect(countDown);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.ondestroy();
            countDown = null;
        }
        if (iRegisterPresenter != null) {
            iRegisterPresenter.ondestroy();
        }

    }
}
