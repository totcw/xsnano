package com.betterda.xsnano.shop.model;

/**
 * 商品兑换记录
 * Created by Administrator on 2016/7/20.
 */
public class ChangeRecord {
    private String url; //商品图片
    private String name;//商品名字
    private float price;//商品价格
    private String time;//兑换时间

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
