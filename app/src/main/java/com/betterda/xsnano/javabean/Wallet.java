package com.betterda.xsnano.javabean;

/**
 * 我的钱包
 * Created by Administrator on 2016/7/4.
 */
public class Wallet {

    /**
     * id : 4028810155674dd30155674e991b0000
     * nickName : 第三方
     * account : 15160700380
     * password : 1
     * golden : 100
     * icon : 10
     * photo : upload/photo.png
     */

    private String id;
    private String nickName;
    private String account;
    private String password;
    private int golden;
    private int icon;
    private String photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGolden() {
        return golden;
    }

    public void setGolden(int golden) {
        this.golden = golden;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
