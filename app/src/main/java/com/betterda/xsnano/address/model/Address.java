package com.betterda.xsnano.address.model;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Address {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;//姓名
    private String number;//手机号
    private String address2;//省丶市
    private String address3;//详细地址

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    private boolean isMoren;//是否是默认

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


    public boolean isMoren() {
        return isMoren;
    }

    public void setIsMoren(boolean isMoren) {
        this.isMoren = isMoren;
    }
}
