package com.betterda.xsnano.javabean;

/**
 * 民生获取流水号的返回参数类
 * Created by Administrator on 2016/4/18.
 */
public class RespContent {
    private String merId;  //商户号
    private String orderId; //订单号
    private String txnAmt;  //支付金额
    private String txnTime; //时间
    private String queryId; //交易查询流水号
    private String tn; //交易流水号,用户调用支付控件的时候使用
    private String respCode; //响应吗 00表示成功
    private String respMsg; //应答信息,返回失败的原因

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

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
