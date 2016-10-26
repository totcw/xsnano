package com.betterda.xsnano.javabean;

/**
 * 购物车返回的商品类
 * Created by Administrator on 2016/6/27.
 */
public class BusDetails {
    private String product_id; //商品id
    private float sale_price;//商品价格
    private int count;//商品数量
    private String product_name;//商品名字
    private String picture;//商品图片
    private int pay_golden;//可抵消的金币个数
    private String shop_id;//店铺id

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public int getPay_golden() {
        return pay_golden;
    }

    public void setPay_golden(int pay_golden) {
        this.pay_golden = pay_golden;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
