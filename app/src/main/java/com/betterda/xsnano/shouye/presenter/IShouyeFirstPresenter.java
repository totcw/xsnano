package com.betterda.xsnano.shouye.presenter;

import android.view.View;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.shouye.model.LunBoTu;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface IShouyeFirstPresenter extends IPresenter{
    /**
     * 获取轮播图的数据
     * @return
     */
    public List<LunBoTu> getStringList();

    /**
     * 创建轮播图的圆点
     */
    public void cratePoint();

    /**
     * 创建轮播图的handler
     */
    public void createHandler();

    /**
     * 创建轮播图的图片
     */
    public View ctreaImageView(int position);

    /**
     * fragment销毁执行的方法
     */
    public void onDestroy();
}
