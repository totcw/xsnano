package com.betterda.xsnano.youhui.model;

/**
 * Created by lyf
 */
public class YouHui {
    private String name;//优惠券的编号
    private String fanwei; //使用范围
    private String time; //使用时间
    private float money; //金额


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFanwei() {
        return fanwei;
    }

    public void setFanwei(String fanwei) {
        this.fanwei = fanwei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private float price; //达到使用的金额


}
