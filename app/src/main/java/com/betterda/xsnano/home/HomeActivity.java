package com.betterda.xsnano.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.PopupWindow;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.home.adapter.MyAdapter;
import com.betterda.xsnano.home.presenter.IHomePresenter;
import com.betterda.xsnano.home.view.IHomeView;
import com.betterda.xsnano.shop.ShangChengFragment;
import com.betterda.xsnano.shouye.ShouYeFragment2;
import com.betterda.xsnano.view.IndicatorView;
import com.betterda.xsnano.wode.MyFragment;

import java.util.List;

/**
 * home的view只负责view的相关处理,不于model交互
 */
public class HomeActivity extends BaseActivity implements IHomeView, View.OnClickListener {
    private ViewPager vpager_main; //viewpager
    private IndicatorView shouye, shangcheng, my;//底部标题
    private List<IndicatorView> indicatorViewList; //存放底部标题的容器
    private MyAdapter adapter; //viewpager的适配器
    private FragmentManager fm;
    private IHomePresenter iHomePresenter;
    private ShouYeFragment2 shouyeFragment;
    private ShangChengFragment shangchengFragment;
    private MyFragment myFragment;


    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        shouye = (IndicatorView) findViewById(R.id.idv_shouye);
        shangcheng = (IndicatorView) findViewById(R.id.idv_shangcheng);
        my = (IndicatorView) findViewById(R.id.idv_my);
        vpager_main = (ViewPager) findViewById(R.id.vpager_main);


    }

    @Override
    public void initListener() {
        super.initListener();
        shouye.setOnClickListener(this);
        shangcheng.setOnClickListener(this);
        my.setOnClickListener(this);
    }

    @Override
    public void init() {
        super.init();
        /**
         * 设置底部布局的属性
         */
        shouye.setIvBackground(R.mipmap.shoukuan_normal, R.mipmap.shoukuan_pressed);
        shangcheng.setIvBackground(R.mipmap.zhangdan_normal, R.mipmap.zhangdan_pressed);
        my.setIvBackground(R.mipmap.wode_normal, R.mipmap.wode_pressed);
        shouye.setLineBackground(0xff909090, 0xffa62424);
        shangcheng.setLineBackground(0xff909090, 0xffa62424);
        my.setLineBackground(0xff909090, 0xffa62424);
        shouye.setTitle("首页");
        shangcheng.setTitle("金币商城");
        my.setTitle("我的");


        shouye.setTabSelected(true);

        //创建fm管理者
        fm = getSupportFragmentManager();

        shouyeFragment = new ShouYeFragment2();
        shangchengFragment = new ShangChengFragment();
        myFragment = new MyFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (!HomeActivity.this.isFinishing()) {
            fragmentTransaction.add(R.id.frame, shouyeFragment).add(R.id.frame, shangchengFragment)
                    .add(R.id.frame, myFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        show(shouyeFragment);
        shouye.setSelected(true);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idv_shouye:
                if (null == shouyeFragment) {
                    shouyeFragment = new ShouYeFragment2();
                }
                show(shouyeFragment);
                change(shouye);
                break;
            case R.id.idv_shangcheng:
                if (null == shangchengFragment) {
                    shangchengFragment = new ShangChengFragment();

                }
                show(shangchengFragment);
                change(shangcheng);
                break;
            case R.id.idv_my:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }

                show(myFragment);
                change(my);
                break;
        }


    }

    private void replace(Fragment fragment) {
        if (fm != null) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment).commitAllowingStateLoss();
        }
    }

    private void show(Fragment fragment) {
        if (null != fm) {
            FragmentTransaction fragmentTransaction2 = fm.beginTransaction();
            if (null != fragmentTransaction2 && HomeActivity.this != null) {

                if (!HomeActivity.this.isFinishing()) {
                    fragmentTransaction2.hide(shangchengFragment);
                    fragmentTransaction2.hide(myFragment);
                    fragmentTransaction2.hide(shouyeFragment);
                    fragmentTransaction2.show(fragment);
                    fragmentTransaction2.commitAllowingStateLoss();
                }
            }
        }
    }


    public void change(IndicatorView idv) {
        /**
         * 设置收款为选中,其它为默认
         */
        if (null != shouye && null != shangcheng && null != my && null != idv) {


            shouye.setTabSelected(false);
            shangcheng.setTabSelected(false);
            my.setTabSelected(false);
            idv.setTabSelected(true);


            //设置选中 用来判断推出
            shouye.setSelected(false);
            shangcheng.setSelected(false);
            my.setSelected(false);
            idv.setSelected(true);


        }

    }

    /**
     * 跳转到金币商城
     */
    public void changeShangcheng() {
        if (shangcheng != null) {
            change(shangcheng);
        }
        if (shangchengFragment == null) {
            shangchengFragment = new ShangChengFragment();
        }
        shangchengFragment.item = 0;
        show(shangchengFragment);

    }

    /**
     * 跳转到金币兑换
     */
    public void changeJinbi() {
        if (shangcheng != null) {
            change(shangcheng);
        }

        if (shangchengFragment == null) {
            shangchengFragment = new ShangChengFragment();
        }
        shangchengFragment.item = 1;

        show(shangchengFragment);

    }

    public ViewPager getVpager_main() {
        return null;
    }


    @Override
    public void onBackPressed() {

        if (null != shouye) {

            if (shouye.isSelected()) {
                boolean b = back();
                if (b) {

                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }

    }


    public boolean back() {
        if (shouyeFragment != null) {

            PopupWindow popupWindow = shouyeFragment.getPopupWindow();
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            }
        }
        return false;
    }

}
