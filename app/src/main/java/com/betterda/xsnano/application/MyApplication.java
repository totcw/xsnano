package com.betterda.xsnano.application;

import android.app.Activity;
import android.app.Application;
import android.media.midi.MidiManager;

import com.baidu.mapapi.SDKInitializer;
import com.betterda.xsnano.bus.model.Bus;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;

/**
 * Created by lyf
 */
public class MyApplication extends Application {

    private List<Activity> list;
    private List<Bus> busList;
    private String shopid;//商品详细对应的店铺id
    private String productid;//商品详细对应的商品id

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化短信验证
        SMSSDK.initSDK(this, "149b00d31c354", "936250d48aebf6154bc737a95cc1781d");

        //初始化xutils3
        x.Ext.init(this);
        //  x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        if (null == list) {
            list = new ArrayList<>();
        }

        if (null == busList) {
            busList = new ArrayList<>();
        }
        //内存泄漏检测

      //  LeakCanary.install(this);

        //Fresco初始化
        Fresco.initialize(getApplicationContext());
        //百度地图
        SDKInitializer.initialize(getApplicationContext());
        //捕获异常
       CrashHandler.getInstance().init(getApplicationContext());
    }

    /**
     * 将activity添加到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (null != list) {
            list.add(activity);
        }

    }

    /**
     * 退出程序
     */
    public void exitProgress() {

        if (null != list) {

            for (Activity activity : list) {
                activity.finish();
            }
            //将容器清空
            list.clear();


        }


    }

    /**
     * 当activity销毁时调用该方法,防止内存泄漏
     *
     * @param activity
     */
    public void removeAcitivty(Activity activity) {
        if (null != list && activity != null) {

            list.remove(activity);
        }
    }


    public List<Bus> getBusList() {
        return busList;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
