package com.betterda.xsnano;

/**
 * Presenter基类
 * Created by Administrator on 2016/5/12.
 */
public interface IPresenter {
    /**
     * 开始获取数据之类的
     */
    public void start();

    /**
     * 销毁的方法,当销毁的时候调用,在这里将view设置为null,防止内存泄漏
     */
  //  void onDestroy();
}
