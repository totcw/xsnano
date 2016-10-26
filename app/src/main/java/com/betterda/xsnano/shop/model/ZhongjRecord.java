package com.betterda.xsnano.shop.model;

/**
 * Created by Administrator on 2016/7/24.
 */
public class ZhongjRecord {
    private String name; //商品名
    private  String account; //中奖人
    private String time; //开奖时间
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
