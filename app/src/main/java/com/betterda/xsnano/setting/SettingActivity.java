package com.betterda.xsnano.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.findpwd.FindPwdActivity;
import com.betterda.xsnano.information.InformationActivity;
import com.betterda.xsnano.login.LoginActivity;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.NormalTopBar;

/**
 * 设置界面
 * Created by lyf
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private NormalTopBar topBar;
    private LinearLayout linear_setting_ziliao, linear_setting_editpwd, linear_setting_us;
    private Button btn_exit;
   // private MyBroadcastReciver reciver;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_setting);
        topBar = (NormalTopBar) findViewById(R.id.topbar_setting);
        linear_setting_ziliao = (LinearLayout) findViewById(R.id.linear_setting_ziliao);
        linear_setting_editpwd = (LinearLayout) findViewById(R.id.linear_setting_editpwd);
        linear_setting_us = (LinearLayout) findViewById(R.id.linear_setting_us);
        btn_exit = (Button) findViewById(R.id.btn_setting_exit);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
        linear_setting_ziliao.setOnClickListener(this);
        linear_setting_editpwd.setOnClickListener(this);
        linear_setting_us.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        topBar.setTitle("设置");
    //    reciver = new MyBroadcastReciver();
     //   registerReceiver(reciver, new IntentFilter("com.berterda.xsnano.setting"));
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
                backActivity();
                break;
            case R.id.btn_setting_exit: //退出登录
                exitLogin();
                break;
            case R.id.linear_setting_editpwd://修改密码
                UtilMethod.isLogin(this, FindPwdActivity.class);
                break;
            case R.id.linear_setting_us://关于我们

                break;
            case R.id.linear_setting_ziliao://个人资料
                UtilMethod.isLogin(this,InformationActivity.class);
                break;
        }
    }

    private void exitLogin() {
        CacheUtils.putBoolean(this, "islogin", false);
        CacheUtils.putString(this,"number","");
        UtilMethod.startIntent(this, LoginActivity.class);
        finish();
    }

/*
    class MyBroadcastReciver extends BroadcastReceiver{

        *//**
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         *//*
        @Override
        public void onReceive(Context context, Intent intent) {
            SettingActivity.this.finish();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(reciver);
    }
}
