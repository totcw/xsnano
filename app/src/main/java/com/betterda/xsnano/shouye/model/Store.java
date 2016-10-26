package com.betterda.xsnano.shouye.model;

/**
 * 商家列表的店铺javabean
 * Created by Administrator on 2016/5/12.
 */
public class Store {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String id;//店铺的主键id
    private String url;//店铺的小图标
    private String name; //店铺的名字
    private float rate;//评价等级
    private int amount;//月销售量
    private String address;//地址
    private int comment;//评论条数
    private String type;//店铺的类型 ，维修店
    private String servie;//商家对应的一个服务
    private String price; //服务的价格
    private String productId;//服务的id
    private String distance;//距离
    private boolean isNotData;//是否是空数据
    private String is_since;//是否是自提

    public String getIs_since() {
        return is_since;
    }

    public void setIs_since(String is_since) {
        this.is_since = is_since;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isNotData() {
        return isNotData;
    }

    public void setIsNotData(boolean isNotData) {
        this.isNotData = isNotData;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServie() {
        return servie;
    }

    public void setServie(String servie) {
        this.servie = servie;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", amount=" + amount +
                ", address='" + address + '\'' +
                ", comment=" + comment +
                ", type='" + type + '\'' +
                ", servie='" + servie + '\'' +
                ", price='" + price + '\'' +
                ", productId='" + productId + '\'' +
                ", distance='" + distance + '\'' +
                ", isNotData=" + isNotData +
                '}';
    }
}
