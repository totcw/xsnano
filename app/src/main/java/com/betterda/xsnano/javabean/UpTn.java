package com.betterda.xsnano.javabean;

/**
 * 民生银联流水号获取的参数类
 * Created by Administrator on 2016/4/15.
 */
public class UpTn {

    private String backUrl; //通知url
    private String merId;  //商户号
    private String orderId; //订单号
    private String txnAmt;  //支付金额
    private String txnTime; //时间

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
}
