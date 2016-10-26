package com.betterda.xsnano.javabean;

/**
 * 金币明细
 * Created by Administrator on 2016/7/7.
 */
public class WalletList {


    /**
     * id : ff80808155f798c50155fb924b000010
     * create_date : 2016-07-18 09:07:17
     * cash : 0.0
     * gold : -1
     * icon : 0
     * card_stock : 0
     * product_id : 402881ec55bf74ed0155bf7d5f940001
     * status : 20
     * ext_filed : 金币夺宝-投注金币,金币个数：1
     * account : 15160700380
     * is_buyer : Y
     */

    private String id;
    private String create_date;
    private double cash;
    private int gold;
    private int icon;
    private int card_stock;
    private String product_id;
    private String status;
    private String ext_filed;
    private String account;
    private String is_buyer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCard_stock() {
        return card_stock;
    }

    public void setCard_stock(int card_stock) {
        this.card_stock = card_stock;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExt_filed() {
        return ext_filed;
    }

    public void setExt_filed(String ext_filed) {
        this.ext_filed = ext_filed;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIs_buyer() {
        return is_buyer;
    }

    public void setIs_buyer(String is_buyer) {
        this.is_buyer = is_buyer;
    }
}
