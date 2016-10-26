package com.betterda.xsnano.shouye.model;

import java.util.List;

/**
 * 首页分类的javabean
 * Created by Administrator on 2016/5/13.
 */
public class Category {
    private String categoryName;//类别名
    private List<String> list;//存放一个门店中的内容

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
