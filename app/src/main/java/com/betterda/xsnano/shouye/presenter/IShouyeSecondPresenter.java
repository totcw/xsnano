package com.betterda.xsnano.shouye.presenter;

import android.app.Activity;
import android.view.View;

/**
 * 负责与首页第二块区域的交互
 * Created by Administrator on 2016/4/26.
 */
public interface IShouyeSecondPresenter {

    /**
     * 轮胎
     */
    void luntai();

    /**
     * 加油卡
     */
    void jyz();

    /**
     * 汽车美容
     */
    void qcmr();

    /**
     * 添加剂
     */
    void tjq();

    /**
     * 雨刮
     */
    void yg();

    /**
     * 洗车
     */
    void xiche();

    /**
     * 保养
     */
    void baoyang();

    /**
     * 机油
     */
    void jiyou();

    /**
     * 销毁时调用的方法
     */
    void onDestroy();

    void luntaiservice();
    void meirongservice();
    void yanghuservice();
    void xicheservice();
}
