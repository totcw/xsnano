package com.betterda.xsnano.javabean;

/**
 * 店铺列表
 * Created by Administrator on 2016/6/30.
 */
public class StoreList {


    /**
     * id : 1
     * createName :
     * createBy :
     * updateName : 管理员
     * updateBy : admin
     * updateDate : Jun 30, 2016 10:50:57 AM
     * catalogId : 2
     * code : 1001
     * name : 洗车服务
     * price : 100
     * salePrice : 100
     * score : 5
     * isNew : Y
     * isSale : Y
     * unit : 次
     * sellCount : 10
     * stock : 1000
     * searchKey :
     * title : 洗车服务
     * keywords :
     * images :
     * introduce :
     * bigPicture : upload/files/20160630105049yQnM6i4w.png
     * littlePicture :
     * orgCode :
     * spec :
     * shopId : 402881e755993ed30155994215230002
     * serviceId : 2
     * description :
     * productHtml :
     */

    private ProductBean product;
    /**
     * id : 402881e755993ed30155994215230002
     * create_name : 管理员
     * create_by : admin
     * create_date : Jun 29, 2016 7:04:52 AM
     * update_name : 管理员
     * update_by : admin
     * update_date : Jun 29, 2016 7:08:22 AM
     * shop_name : 厦门仙岳店
     * shop_catalog_id : 2
     * longitude : 100
     * dimension : 100
     * score : 5
     * sale_count : 10
     * address : 厦门市思明区仙岳路
     * status : 1
     * small_picture :
     * big_picture :
     * region_id : 40283f815522daeb015522e08b720001
     * services_id : 2
     * services_score : 5.0
     * products_score : 5.0
     * remark : 测试
     */

    private ShopInfoBean shopInfo;
    /**
     * product : {"id":"1","createName":"","createBy":"","updateName":"管理员","updateBy":"admin","updateDate":"Jun 30, 2016 10:50:57 AM","catalogId":"2","code":"1001","name":"洗车服务","price":"100","salePrice":"100","score":5,"isNew":"Y","isSale":"Y","unit":"次","sellCount":10,"stock":1000,"searchKey":"","title":"洗车服务","keywords":"","images":"","introduce":"","bigPicture":"upload/files/20160630105049yQnM6i4w.png","littlePicture":"","orgCode":"","spec":"","shopId":"402881e755993ed30155994215230002","serviceId":"2","description":"","productHtml":""}
     * shopInfo : {"id":"402881e755993ed30155994215230002","create_name":"管理员","create_by":"admin","create_date":"Jun 29, 2016 7:04:52 AM","update_name":"管理员","update_by":"admin","update_date":"Jun 29, 2016 7:08:22 AM","shop_name":"厦门仙岳店","shop_catalog_id":"2","longitude":"100","dimension":"100","score":"5","sale_count":10,"address":"厦门市思明区仙岳路","status":"1","small_picture":"","big_picture":"","region_id":"40283f815522daeb015522e08b720001","services_id":"2","services_score":5,"products_score":5,"remark":"测试"}
     * totalCommentCount : 1
     */

    private String totalCommentCount;//评论数
    private String totalSellCount;//销售数量

    public String getTotalSellCount() {
        return totalSellCount;
    }

    public void setTotalSellCount(String totalSellCount) {
        this.totalSellCount = totalSellCount;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public ShopInfoBean getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoBean shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getTotalCommentCount() {
        return totalCommentCount;
    }

    public void setTotalCommentCount(String totalCommentCount) {
        this.totalCommentCount = totalCommentCount;
    }

    public static class ProductBean {
        private String id;
        private String createName;
        private String createBy;
        private String updateName;
        private String updateBy;
        private String updateDate;
        private String catalogId;
        private String code;
        private String name;
        private String price;
        private String salePrice;
        private int score;
        private String isNew;
        private String isSale;
        private String unit;
        private int sellCount;
        private int stock;
        private String searchKey;
        private String title;
        private String keywords;
        private String images;
        private String introduce;
        private String bigPicture;
        private String littlePicture;
        private String orgCode;
        private String spec;
        private String shopId;
        private String serviceId;
        private String description;
        private String productHtml;

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

        public String getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getIsNew() {
            return isNew;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public String getIsSale() {
            return isSale;
        }

        public void setIsSale(String isSale) {
            this.isSale = isSale;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getSellCount() {
            return sellCount;
        }

        public void setSellCount(int sellCount) {
            this.sellCount = sellCount;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getBigPicture() {
            return bigPicture;
        }

        public void setBigPicture(String bigPicture) {
            this.bigPicture = bigPicture;
        }

        public String getLittlePicture() {
            return littlePicture;
        }

        public void setLittlePicture(String littlePicture) {
            this.littlePicture = littlePicture;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProductHtml() {
            return productHtml;
        }

        public void setProductHtml(String productHtml) {
            this.productHtml = productHtml;
        }
    }

    public static class ShopInfoBean {
        private String id;
        private String create_name;
        private String create_by;
        private String create_date;
        private String update_name;
        private String update_by;
        private String update_date;
        private String shop_name;
        private String shop_catalog_id;
        private String longitude;
        private String dimension;
        private String score;
        private int sale_count;
        private String address;
        private String status;
        private String small_picture;
        private String big_picture;
        private String region_id;
        private String services_id;
        private double services_score;
        private double products_score;
        private String remark;
        private String distance;
        private String is_since;//是否是自提

        public String getIs_since() {
            return is_since;
        }

        public void setIs_since(String is_since) {
            this.is_since = is_since;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_name() {
            return create_name;
        }

        public void setCreate_name(String create_name) {
            this.create_name = create_name;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUpdate_name() {
            return update_name;
        }

        public void setUpdate_name(String update_name) {
            this.update_name = update_name;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_catalog_id() {
            return shop_catalog_id;
        }

        public void setShop_catalog_id(String shop_catalog_id) {
            this.shop_catalog_id = shop_catalog_id;
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

        public int getSale_count() {
            return sale_count;
        }

        public void setSale_count(int sale_count) {
            this.sale_count = sale_count;
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

        public String getSmall_picture() {
            return small_picture;
        }

        public void setSmall_picture(String small_picture) {
            this.small_picture = small_picture;
        }

        public String getBig_picture() {
            return big_picture;
        }

        public void setBig_picture(String big_picture) {
            this.big_picture = big_picture;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getServices_id() {
            return services_id;
        }

        public void setServices_id(String services_id) {
            this.services_id = services_id;
        }

        public double getServices_score() {
            return services_score;
        }

        public void setServices_score(double services_score) {
            this.services_score = services_score;
        }

        public double getProducts_score() {
            return products_score;
        }

        public void setProducts_score(double products_score) {
            this.products_score = products_score;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
