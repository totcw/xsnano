package com.betterda.xsnano.shouye.presenter;

import android.content.Intent;

import com.betterda.xsnano.livingpay.BaseLivingActiivty;
import com.betterda.xsnano.shouye.view.IShouyeView;
import com.betterda.xsnano.store.StoreActivity;
import com.betterda.xsnano.util.UtilMethod;
import com.betterda.xsnano.wash.WashActivity;

import java.util.ArrayList;

/**
 * 负责与首页8个热门按钮交互
 * Created by Administrator on 2016/4/26.
 */
public class IShouyeSecondPresenterImpl implements IShouyeSecondPresenter {
    private IShouyeView shouyeView;
    private ArrayList<String> arrayList;
    public IShouyeSecondPresenterImpl(IShouyeView shouyeView) {
        this.shouyeView = shouyeView;
    }

    /**
     * 生活缴费
     */
    @Override
    public void luntai() {
        UtilMethod.startIntent(shouyeView.getmActivity(), BaseLivingActiivty.class);
    }

    private void wash(String title) {
        Intent intentyg = new Intent(shouyeView.getmActivity(), WashActivity.class);
        intentyg.putExtra("title", title);
        intentyg.putExtra("isshop", false);
        shouyeView.getmActivity().startActivity(intentyg);
    }

    @Override
    public void jyz() {
        wash("车保险");
    }

    @Override
    public void qcmr() {
        wash("二手车");
    }

    @Override
    public void tjq() {

        //跳转到总公司
        UtilMethod.startIntent(shouyeView.getmActivity(), StoreActivity.class);

    }

    @Override
    public void yg() {
        wash("油站商店");
    }
/*
    private void tianjiaji(String title,ArrayList<String> list2) {
        Intent intent3 = new Intent(shouyeView.getmActivity(), TianJiaJiActivity.class);
        intent3.putStringArrayListExtra("pinpai", list2);
        intent3.putExtra("title", title);
        shouyeView.getmActivity().startActivity(intent3);
    }*/

    @Override
    public void xiche() {
        wash("车金融");
    }

    @Override
    public void baoyang() {
        wash("车辆租赁");
    }

    @Override
    public void jiyou() {
        wash("4s店");
    }

    @Override
    public void onDestroy() {
        if (null != arrayList) {
            arrayList.clear();
            arrayList = null;
        }
    }

    @Override
    public void luntaiservice() {
        toService("轮胎服务");
    }

    private void toService(String title) {
        Intent intent = new Intent(shouyeView.getmActivity(), WashActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("isshop", true);
        shouyeView.getmActivity().startActivity(intent);
    }

    @Override
    public void meirongservice() {
        toService("美容服务");
    }

    @Override
    public void yanghuservice() {
        toService("养护服务");
    }

    @Override
    public void xicheservice() {
        toService("洗车服务");
    }
}
