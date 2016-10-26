package com.betterda.xsnano.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import com.betterda.xsnano.R;
import com.betterda.xsnano.acitivity.BaseActivity;
import com.betterda.xsnano.home.adapter.MyAdapter;
import com.betterda.xsnano.home.presenter.IHomePresenter;
import com.betterda.xsnano.home.presenter.IHomePresenterImpl;
import com.betterda.xsnano.home.view.IHomeView;
import com.betterda.xsnano.shop.ShangChengFragment;
import com.betterda.xsnano.shouye.ShouYeFragment2;
import com.betterda.xsnano.util.Constants;
import com.betterda.xsnano.view.IndicatorView;
import com.betterda.xsnano.wode.MyFragment;

import java.util.ArrayList;
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
        //将底部布局添加到容器中
       /* if (null == indicatorViewList) {
            indicatorViewList = new ArrayList<>();
        }
        indicatorViewList.add(shouye);
        indicatorViewList.add(shangcheng);
        indicatorViewList.add(my);*/

        //创建fm管理者
        fm = getSupportFragmentManager();

        //创建IhomePresenter的实现类
    /*    iHomePresenter = new IHomePresenterImpl();

        if (null == adapter) {
            adapter = new MyAdapter(fm, iHomePresenter);
        }*/


        //设置viewpager的适配器
        //   vpager_main.setAdapter(adapter);
        //   vpager_main.setOnPageChangeListener(this);
        //设置预加载个数 最少一个 也是就本页在加一页总共2页
        //   vpager_main.setOffscreenPageLimit(1);

      /*  ViewTreeObserver vto = shouye.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @Override
            public void onGlobalLayout() {
                //移除上一次监听，避免重复监听
                shouye.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //在这里调用getHeight（）获得控件的高度
                int Height = shouye.getHeight();

                Constants.IDV_HEIGHT = Height;

            }
        });*/
        shouyeFragment = new ShouYeFragment2();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (!HomeActivity.this.isFinishing()) {
            fragmentTransaction.replace(R.id.frame, shouyeFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

        shouye.setSelected(true);

    }


   /* @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != indicatorViewList) {

            change(indicatorViewList.get(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idv_shouye:
                // hide(shouyeFragment);
                if (null == shouyeFragment) {
                    shouyeFragment = new ShouYeFragment2();
                }
                replace(shouyeFragment);
                change(shouye);
                break;
            case R.id.idv_shangcheng:
                //  hide(shangchengFragment);
                if (null == shangchengFragment) {
                    shangchengFragment = new ShangChengFragment();

                }
                replace(shangchengFragment);
                change(shangcheng);
                break;
            case R.id.idv_my:
                //  hide(myFragment);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }
                replace(myFragment);
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

    private void hide(Fragment fragment) {
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

        shangchengFragment = new ShangChengFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("item", 0);
        shangchengFragment.setArguments(bundle);

        replace(shangchengFragment);

    }

    /**
     * 跳转到金币兑换
     */
    public void changeJinbi() {
        if (shangcheng != null) {
            change(shangcheng);
        }

        shangchengFragment = new ShangChengFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("item", 1);
        shangchengFragment.setArguments(bundle);

        replace(shangchengFragment);

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
