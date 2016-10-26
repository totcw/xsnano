package com.betterda.xsnano.javabean;

/**
 * 金币夺宝
 * Created by lyf on 2016/7/1.
 */
public class GoldGet {

    /**
     * id : 402881e755999150015599932ef90002
     * createName : 管理员
     * createBy : admin
     * updateName : 管理员
     * updateBy : admin
     * createDate : Jun 29, 2016 8:33:27 AM
     * updateDate : Jun 29, 2016 8:37:17 AM
     * tile : 测试
     * productId : 402881e755993ed30155994bde5a000e
     * productName :
     * smallPicture :
     * bigPicture :
     * needGold : 10
     * alreadyGold : 0
     * regionId : 40283f815522daeb015522e08b720001
     * orgCode :
     * extFiled :
     * indianaCount : 10
     * alreadyCount : 0
     * status : 20
     */

    private String id;
    private String createName;
    private String createBy;
    private String updateName;
    private String updateBy;
    private String createDate;
    private String updateDate;
    private String tile;
    private String productId;//商品id
    private String productName;//商品名
    private String smallPicture;
    private String bigPicture;//大图片
    private int needGold;  //所需金币个数
    private int alreadyGold;  //已投金币个数
    private String regionId;
    private String orgCode;
    private String extFiled;
    private int indianaCount;
    private int alreadyCount;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public int getNeedGold() {
        return needGold;
    }

    public void setNeedGold(int needGold) {
        this.needGold = needGold;
    }

    public int getAlreadyGold() {
        return alreadyGold;
    }

    public void setAlreadyGold(int alreadyGold) {
        this.alreadyGold = alreadyGold;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getExtFiled() {
        return extFiled;
    }

    public void setExtFiled(String extFiled) {
        this.extFiled = extFiled;
    }

    public int getIndianaCount() {
        return indianaCount;
    }

    public void setIndianaCount(int indianaCount) {
        this.indianaCount = indianaCount;
    }

    public int getAlreadyCount() {
        return alreadyCount;
    }

    public void setAlreadyCount(int alreadyCount) {
        this.alreadyCount = alreadyCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
