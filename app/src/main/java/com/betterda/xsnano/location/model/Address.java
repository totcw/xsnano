package com.betterda.xsnano.location.model;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Administrator on 2016/11/10.
 */

public class Address {
    private String id;

    private String key; //关键字

    private String address; //地址

    private String longitude; //经度
    private String latitude;// 纬度

    private boolean isDelte;//是否要删除

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDelte() {
        return isDelte;
    }

    public void setDelte(boolean delte) {
        isDelte = delte;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
