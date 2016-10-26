package com.betterda.xsnano.bus.model;

import java.util.IdentityHashMap;

/**
 * 购物车商品的类
 * Created by Administrator on 2016/5/17.
 */
public class Bus {
    private String id;//商品的id
    private boolean isChosed; //是否选择
    private String url; //图片的地址
    private String name;//商品名字
    private float money;//价格
    private int amount;//选择的数量
    private String state;//订单的状态
    private int goldCount;//商品对应的可抵消的金币个数
    private String shopId;//店铺id
    private String isComment="N";//是否评价

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChosed() {
        return isChosed;
    }

    public void setIsChosed(boolean isChosed) {
        this.isChosed = isChosed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }
}
