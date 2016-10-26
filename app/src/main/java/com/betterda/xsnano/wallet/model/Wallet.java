package com.betterda.xsnano.wallet.model;

/**
 * Created by Administrator on 2016/6/1.
 */
public class Wallet {

    private String name;
    private String type; //类型
    private String time;
    private int money;
    private boolean isAdd;
    private String extFiled;

    public String getExtFiled() {
        return extFiled;
    }

    public void setExtFiled(String extFiled) {
        this.extFiled = extFiled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
}
