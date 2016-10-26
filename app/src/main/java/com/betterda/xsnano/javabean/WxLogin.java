package com.betterda.xsnano.javabean;

/**
 * 微信登录回调javabean
 * Created by Administrator on 2016/10/13.
 */

public class WxLogin {

    /**
     * access_token : RoghMK8w8MM2ovYIZUV10uUnDXmJiTDzd26LFBOm1x_CERNtZkoFMm_7ppnnbCu9cFz8HXLt57m07H5cU8t8gNvpeVaSrgQPz3GJfRB4AuI
     * expires_in : 7200
     * refresh_token : QW_G2R_9GeqC4LaWi4Nm9PPdyz7XJMFgKB1ymRDyp5T9vlHKSrkK1mU_8Cu-PhNbkE4QUoe5p1txTZMb_4O4N78-r0Lrqx41o4G9TFYX5ZI
     * openid : oQjSgwvSDv7vUtKEIInrPtivvykI
     * scope : snsapi_userinfo
     * unionid : oFCiEw3EghT7qWtRpWJJZXfJChOA
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
