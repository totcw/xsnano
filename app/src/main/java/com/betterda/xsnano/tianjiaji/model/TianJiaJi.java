package com.betterda.xsnano.tianjiaji.model;

/**
 * Created by Administrator on 2016/5/16.
 */
public class TianJiaJi {
    private String url; //图片地址
    private String name;//商品名
    private String money;//价格
    private String amount;//已出售数量
    private String comment;//评论数

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
