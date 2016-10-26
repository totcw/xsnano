package com.betterda.xsnano.shouye.presenter;

import android.view.View;

import com.betterda.xsnano.IPresenter;
import com.betterda.xsnano.shouye.model.Category;
import com.betterda.xsnano.shouye.model.Store;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface IShouyeThreePresenter extends IPresenter{
    /**
     * 统一设置标题
     */
    public void setTile();

    /**
     * 分类的点击事件
     */
    public void clickFirst();
    /**
     * 排序的点击事件
     */
    public void clickSecond();
    /**
     * 筛选的点击事件
     */
    public void clickThree();


    List<String> getCategoryList();

    /**
     * 返回mainlistview的视图
     * @param position
     * @return
     */
    View getMainView(int position, View convertview);

    View getMoreView(int position, View convertview,List<String> morelist);

    /**
     * 处理筛选界面的点击事件
     * @param id
     */
    void click(int id);

    /**
     * 首页数据的容器
     * @return
     */
    public List<Store> getList();

    /**
     * 创建listview的ivew
     * @param position
     * @param convertView
     * @return
     */
    public View getView(int position,View convertView);

    String getSelectSaixuan();


    void onDestroy();

    //设置 Scrollview 是否置顶
    void setTop(boolean istop);
}
