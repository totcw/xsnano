package com.betterda.xsnano.sale.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/23.
 */
public class SaleGoods {
    private String url; //商品图片的url
    private String name; //商品名
    private float price;//商品价格
    private Date endTime;//商品结束时间
    private long timemillons;//时间差
    private String time;//显示的时间

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getTimemillons() {
        return timemillons;
    }

    public void setTimemillons(long timemillons) {
        this.timemillons = timemillons;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
