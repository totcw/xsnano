package com.betterda.xsnano.javabean;

/**
 * 常用地址
 * Created by Administrator on 2016/11/14.
 */

public class CommonAddress {

    /**
     * create_date : 2016-11-15 14:39:57
     * account : 15160700380
     * longitude : 118.148716
     * latitude : 24.502287
     * district : 湖里区
     * address : 福建省厦门市湖里区双浦路
     */
    private String id;
    private String create_date;
    private String account;
    private String longitude; //经度
    private String latitude;// 纬度
    private String district;
    private String address;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
