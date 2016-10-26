package com.betterda.xsnano.shop.model;

/**
 * 金币兑换
 * Created by lyf on 2016/7/1.
 */
public class GoldChange {
    private String url;//图片路径
    private String name;//商品名
    private String productid;//商品id
    private int needGold;//所需金币


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

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getNeedGold() {
        return needGold;
    }

    public void setNeedGold(int needGold) {
        this.needGold = needGold;
    }


}
