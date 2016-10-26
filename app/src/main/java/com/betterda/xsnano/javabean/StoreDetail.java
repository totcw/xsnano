package com.betterda.xsnano.javabean;

/**
 * 店铺详细
 * Created by lyf on 2016/7/1.
 */
public class StoreDetail {


    /**
     * id : 402881e955b3158b0155b31859a50001
     * createName : 管理员
     * createBy : admin
     * createDate : Jul 4, 2016 7:29:25 AM
     * updateName : 管理员
     * updateBy : admin
     * updateDate : Jul 4, 2016 7:30:52 AM
     * shopName : 厦门市仙岳店
     * shopCatalogId : 1,
     * longitude : 100
     * dimension : 200.168
     * score : 5
     * saleCount : 5
     * address : 厦门市仙岳路
     * status : 20
     * bigPicture :
     * smallPicture :
     * regionId : 40283f815522daeb015522e08b720001
     * servicesId : 2,3
     * servicesScore : 5.0
     * productsScore : 5.0
     * orgCode :
     * telPhone :
     * saleTime :
     * isPick : Y
     * approveStatus : 20
     * contactUser :
     * contactPhone :
     * account :
     * password :
     * province : 16
     * city : 40283f815522daeb015522e08b720001
     * area : 402881e955ae1e8c0155ae23a8250002
     * shopAcreage : 80
     * employeeNum : 100
     * businessAge : 10
     * mainBrand :
     * cooperationProject :
     * selfService :
     * others :
     * gold : 1
     * icon : 1
     * cash : 100
     * remark :
     */

    private ShopEntityBean shopEntity;
    /**
     * id : 4028810155a076810155a0776cb80000
     * createName :
     * createBy :
     * updateName : 管理员
     * updateBy : admin
     * updateDate : Jul 2, 2016 7:21:32 AM
     * account : 15160700380
     * totalcount : 1
     * nickName : 家里
     */

    private ShopcartEntityBean shopcartEntity;

    public ShopEntityBean getShopEntity() {
        return shopEntity;
    }

    public void setShopEntity(ShopEntityBean shopEntity) {
        this.shopEntity = shopEntity;
    }

    public ShopcartEntityBean getShopcartEntity() {
        return shopcartEntity;
    }

    public void setShopcartEntity(ShopcartEntityBean shopcartEntity) {
        this.shopcartEntity = shopcartEntity;
    }

    public static class ShopEntityBean {
        private String id;
        private String createName;
        private String createBy;
        private String createDate;
        private String updateName;
        private String updateBy;
        private String updateDate;
        private String shopName;
        private String shopCatalogId;
        private String longitude;
        private String dimension;
        private String score;
        private int saleCount;
        private String address;
        private String status;
        private String bigPicture;
        private String smallPicture;
        private String regionId;
        private String servicesId;
        private double servicesScore;
        private double productsScore;
        private String orgCode;
        private String telPhone;
        private String saleTime;
        private String isPick;
        private String approveStatus;
        private String contactUser;
        private String contactPhone;
        private String account;
        private String password;
        private String province;
        private String city;
        private String area;
        private String shopAcreage;
        private String employeeNum;
        private String businessAge;
        private String mainBrand;
        private String cooperationProject;
        private String selfService;
        private String others;
        private int gold;
        private int icon;
        private int cash;
        private String remark;

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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopCatalogId() {
            return shopCatalogId;
        }

        public void setShopCatalogId(String shopCatalogId) {
            this.shopCatalogId = shopCatalogId;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getSaleCount() {
            return saleCount;
        }

        public void setSaleCount(int saleCount) {
            this.saleCount = saleCount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBigPicture() {
            return bigPicture;
        }

        public void setBigPicture(String bigPicture) {
            this.bigPicture = bigPicture;
        }

        public String getSmallPicture() {
            return smallPicture;
        }

        public void setSmallPicture(String smallPicture) {
            this.smallPicture = smallPicture;
        }

        public String getRegionId() {
            return regionId;
        }

        public void setRegionId(String regionId) {
            this.regionId = regionId;
        }

        public String getServicesId() {
            return servicesId;
        }

        public void setServicesId(String servicesId) {
            this.servicesId = servicesId;
        }

        public double getServicesScore() {
            return servicesScore;
        }

        public void setServicesScore(double servicesScore) {
            this.servicesScore = servicesScore;
        }

        public double getProductsScore() {
            return productsScore;
        }

        public void setProductsScore(double productsScore) {
            this.productsScore = productsScore;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public String getSaleTime() {
            return saleTime;
        }

        public void setSaleTime(String saleTime) {
            this.saleTime = saleTime;
        }

        public String getIsPick() {
            return isPick;
        }

        public void setIsPick(String isPick) {
            this.isPick = isPick;
        }

        public String getApproveStatus() {
            return approveStatus;
        }

        public void setApproveStatus(String approveStatus) {
            this.approveStatus = approveStatus;
        }

        public String getContactUser() {
            return contactUser;
        }

        public void setContactUser(String contactUser) {
            this.contactUser = contactUser;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getShopAcreage() {
            return shopAcreage;
        }

        public void setShopAcreage(String shopAcreage) {
            this.shopAcreage = shopAcreage;
        }

        public String getEmployeeNum() {
            return employeeNum;
        }

        public void setEmployeeNum(String employeeNum) {
            this.employeeNum = employeeNum;
        }

        public String getBusinessAge() {
            return businessAge;
        }

        public void setBusinessAge(String businessAge) {
            this.businessAge = businessAge;
        }

        public String getMainBrand() {
            return mainBrand;
        }

        public void setMainBrand(String mainBrand) {
            this.mainBrand = mainBrand;
        }

        public String getCooperationProject() {
            return cooperationProject;
        }

        public void setCooperationProject(String cooperationProject) {
            this.cooperationProject = cooperationProject;
        }

        public String getSelfService() {
            return selfService;
        }

        public void setSelfService(String selfService) {
            this.selfService = selfService;
        }

        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
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

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class ShopcartEntityBean {
        private String id;
        private String createName;
        private String createBy;
        private String updateName;
        private String updateBy;
        private String updateDate;
        private String account;
        private int totalcount;
        private String nickName;

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

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
