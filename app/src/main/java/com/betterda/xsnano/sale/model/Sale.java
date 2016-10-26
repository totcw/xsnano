package com.betterda.xsnano.sale.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Sale {
    private String productId;//商品id
    private String url;//图片的地址
    private String price;//价格
    private String name;//名字
    private long timemillons;//现在到结束的时间差
    private String time;//显示的时间
    private String shopId;//店铺id

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
