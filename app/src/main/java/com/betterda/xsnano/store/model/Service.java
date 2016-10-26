package com.betterda.xsnano.store.model;

import java.util.List;

/**
 * 服务类
 * Created by Administrator on 2016/5/25.
 */
public class Service {
    private String name;//服务的名称
    private List<Business> list;//包含业务的容器

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Business> getList() {
        return list;
    }

    public void setList(List<Business> list) {
        this.list = list;
    }
}
