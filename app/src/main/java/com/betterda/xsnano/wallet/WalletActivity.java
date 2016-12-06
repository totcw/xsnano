package com.betterda.xsnano.wallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.adapter.MyAdapter;
import com.betterda.xsnano.javabean.Wallet;
import com.betterda.xsnano.util.CacheUtils;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.util.GetNetUtil;
import com.betterda.xsnano.util.GsonParse;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.view.LoadingPager;
import com.betterda.xsnano.view.NormalTopBar;
import com.betterda.xsnano.view.ViewPagerIndicator;
import com.betterda.xsnano.wallet.fragment.Allfragment;
import com.betterda.xsnano.wallet.fragment.Allfragment2;
import com.betterda.xsnano.wallet.fragment.Allfragment3;

import org.xutils.http.RequestParams;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * 钱包
 * Created by Administrator on 2016/6/1.
 */
public class WalletActivity extends BaseActivity implements View.OnClickListener {
    private LoadingPager loadpager_wallet;
    private NormalTopBar topBar;
    private ViewPager vp_wallet;
    private RelativeLayout relative_wallet_jinbi, relative_wallet_yinbi;
    private TextView tv_wallet_jinbi, tv_wallet_yinbi;
    private ViewPagerIndicator wallet_indicator;
    private List<String> mDatas; //viewpager指示器的数据
    private Fragment allfragment,payfragment,bringfragment;
    private List<Fragment> fragmentList;
    private LinearLayout linear_wallet;
    private int icon;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_wallet);
        topBar = (NormalTopBar) findViewById(R.id.topbar_wallet);
        vp_wallet = (ViewPager) findViewById(R.id.vp_wallet);
        relative_wallet_jinbi = (RelativeLayout) findViewById(R.id.relative_wallet_jinbi);
        relative_wallet_yinbi = (RelativeLayout) findViewById(R.id.relative_wallet_yinbi);
        tv_wallet_jinbi = (TextView) findViewById(R.id.tv_wallet_jinbi);
        tv_wallet_yinbi = (TextView) findViewById(R.id.tv_wallet_yinbi);
        wallet_indicator = (ViewPagerIndicator) findViewById(R.id.wallet_indicator);
        loadpager_wallet = (LoadingPager) findViewById(R.id.loadpager_wallet);
        linear_wallet = (LinearLayout) findViewById(R.id.linear_wallet);
    }

    @Override
    public void initListener() {
        super.initListener();
        topBar.setOnBackListener(this);
        relative_wallet_jinbi.setOnClickListener(this);
    }

    @Override
    public void init() {
        topBar.setTitle("我的钱包");


        mDatas = new ArrayList<>();
        mDatas.add("全部");
        mDatas.add("收入");
        mDatas.add("支出");

        fragmentList = new ArrayList<>();
        allfragment = new Allfragment();
        payfragment = new Allfragment2();
        bringfragment = new Allfragment3();
        fragmentList.add(allfragment);
        fragmentList.add(payfragment);
        fragmentList.add(bringfragment);

        //设置Tab上的标题
        wallet_indicator.setTabItemTitles(mDatas);
        vp_wallet.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        //设置关联的ViewPager
        wallet_indicator.setViewPager(vp_wallet, 0);



        if (loadpager_wallet != null && loadpager_wallet != null) {
            loadpager_wallet.setonEmptyClickListener(this);
            loadpager_wallet.setonErrorClickListener(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadpager_wallet.setLoadVisable();
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams(Constants.URL__WALLET);
        params.addBodyParameter("account", CacheUtils.getString(this,"number",""));
        GetNetUtil.getData(GetNetUtil.POST, params, new GetNetUtil.GetDataCallBack() {
            @Override
            public void onSuccess(String s) {
                Wallet object = GsonParse.getObject(UtilMethod.getString(s), Wallet.class);
                if (object != null) {
                    int golden = object.getGolden();
                    icon = object.getIcon();
                    if (tv_wallet_jinbi != null) {
                        tv_wallet_jinbi.setText(golden+"");
                    }
                    if (tv_wallet_yinbi != null) {
                        tv_wallet_yinbi.setText(icon +"");
                    }
                }

                loadpager_wallet.hide();
                linear_wallet.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                loadpager_wallet.setErrorVisable();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
                backActivity();
                break;
            case R.id.relative_wallet_jinbi://我的金币
                UtilMethod.startIntentParams(getmActivity(), JinBiChangeAcitivty.class, "icon",icon+"");
                break;
            case R.id.relative_wallet_yinbi://我的银币

                break;
            case R.id.frame_error:  //加载错误时的点击事件

                getData();

                break;
            case R.id.frame_empty://数据为空的点击事件
                getData();
                break;
        }
    }



}
