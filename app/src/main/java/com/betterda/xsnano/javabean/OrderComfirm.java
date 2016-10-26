package com.betterda.xsnano.javabean;

import com.betterda.xsnano.bus.model.Bus;

import java.util.List;

/**
 * 订单确认
 * Created by Administrator on 2016/6/27.
 */
public class OrderComfirm {
    private String account;//用户
    private String orderId;//订单id
    private String shopId;//店铺id
    private String time;//订单时间
    private String name;//收货人姓名
    private String number;//收货人电话
    private String address;//收货人地址
    private List<Bus> busList;//商品列表
    private String freight ="0";//运费  0表示免费
    private String  bill;//是否需要发票   Y N
    private String type;//配送方式  10 自提 20块
    private float money;//订单的实际支付金额  扣除金币的个数
    private float salePrice;//订单的总价
    private int goldCount;//抵消的金币个数
    private String orderType; //10代表金币充值，20代表现金充值，30代表常规订单

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Bus> getBusList() {
        return busList;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
