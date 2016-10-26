package com.betterda.xsnano.javabean;

/**
 * 卡券
 * Created by Administrator on 2016/7/7.
 */
public class KaQuan {

    /**
     * id : 1
     * cardStockType : 10
     * cardStockNo : 123
     * status : 10
     * endDate : Jul 15, 2016 8:39:24 PM
     * account : 15160700380
     * writeoffDate : Jun 30, 2016 8:42:30 PM
     */

    private String id;
    private String cardStockType;
    private String cardStockNo;
    private String status;
    private String endDate;
    private String account;
    private String writeoffDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardStockType() {
        return cardStockType;
    }

    public void setCardStockType(String cardStockType) {
        this.cardStockType = cardStockType;
    }

    public String getCardStockNo() {
        return cardStockNo;
    }

    public void setCardStockNo(String cardStockNo) {
        this.cardStockNo = cardStockNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWriteoffDate() {
        return writeoffDate;
    }

    public void setWriteoffDate(String writeoffDate) {
        this.writeoffDate = writeoffDate;
    }
}
