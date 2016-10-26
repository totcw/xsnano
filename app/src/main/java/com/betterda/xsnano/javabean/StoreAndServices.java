package com.betterda.xsnano.javabean;

import java.util.List;

/**
 *服务范围
 * Created by Administrator on 2016/6/30.
 */
public class StoreAndServices {


    /**
     * id : 402881e755993ed30155994215230002
     * createName : 管理员
     * createBy : admin
     * createDate : Jun 29, 2016 7:04:52 AM
     * updateName : 管理员
     * updateBy : admin
     * updateDate : Jun 29, 2016 7:08:22 AM
     * shopName : 厦门仙岳店
     * shopCatalogId : 2
     * longitude : 100
     * dimension : 100
     * score : 5
     * saleCount : 10
     * address : 厦门市思明区仙岳路
     * status : 1
     * smallPicture :
     * bigPicture :
     * regionId : 40283f815522daeb015522e08b720001
     * servicesId : 2
     * servicesScore : 5.0
     * productsScore : 5.0
     * remark : 测试
     */

    private ShopEntityBean shopEntity;
    /**
     * serviceEntity : {"shopServiceName":"保养服务","status":"Y","pid":"1","remark":"保养服务","TPShopServiceEntity":{"TShopServiceEntitys":[]},"TShopServiceEntitys":[],"id":"2"}
     * products : [{"id":"1","createName":"","createBy":"","updateName":"管理员","updateBy":"admin","updateDate":"Jun 30, 2016 10:50:57 AM","catalogId":"2","code":"1001","name":"洗车服务","price":"100","salePrice":"100","score":5,"isNew":"Y","isSale":"Y","unit":"次","sellCount":10,"stock":1000,"searchKey":"","title":"洗车服务","keywords":"","images":"","introduce":"","bigPicture":"upload/files/20160630105049yQnM6i4w.png","littlePicture":"","orgCode":"","spec":"","shopId":"402881e755993ed30155994215230002","serviceId":"2","description":"","productHtml":""}]
     */

    private List<ParamsBean> params;
    /**
     * id : 1
     * account : admin
     * content : 测试评论
     * star : 5
     * status : 2
     */

    private List<CommentsBean> comments;

    public ShopEntityBean getShopEntity() {
        return shopEntity;
    }

    public void setShopEntity(ShopEntityBean shopEntity) {
        this.shopEntity = shopEntity;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
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
        private String smallPicture;
        private String bigPicture;
        private String regionId;
        private String servicesId;
        private double servicesScore;
        private double productsScore;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class ParamsBean {
        /**
         * shopServiceName : 保养服务
         * status : Y
         * pid : 1
         * remark : 保养服务
         * TPShopServiceEntity : {"TShopServiceEntitys":[]}
         * TShopServiceEntitys : []
         * id : 2
         */

        private ServiceEntityBean serviceEntity;
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

        private List<ProductsBean> products;

        public ServiceEntityBean getServiceEntity() {
            return serviceEntity;
        }

        public void setServiceEntity(ServiceEntityBean serviceEntity) {
            this.serviceEntity = serviceEntity;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ServiceEntityBean {
            private String shopServiceName;
            private String status;
            private String pid;
            private String remark;
            private TPShopServiceEntityBean TPShopServiceEntity;
            private String id;
            private List<?> TShopServiceEntitys;

            public String getShopServiceName() {
                return shopServiceName;
            }

            public void setShopServiceName(String shopServiceName) {
                this.shopServiceName = shopServiceName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public TPShopServiceEntityBean getTPShopServiceEntity() {
                return TPShopServiceEntity;
            }

            public void setTPShopServiceEntity(TPShopServiceEntityBean TPShopServiceEntity) {
                this.TPShopServiceEntity = TPShopServiceEntity;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<?> getTShopServiceEntitys() {
                return TShopServiceEntitys;
            }

            public void setTShopServiceEntitys(List<?> TShopServiceEntitys) {
                this.TShopServiceEntitys = TShopServiceEntitys;
            }

            public static class TPShopServiceEntityBean {
                private List<?> TShopServiceEntitys;

                public List<?> getTShopServiceEntitys() {
                    return TShopServiceEntitys;
                }

                public void setTShopServiceEntitys(List<?> TShopServiceEntitys) {
                    this.TShopServiceEntitys = TShopServiceEntitys;
                }
            }
        }

        public static class ProductsBean {
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
            private double score;
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

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
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
    }

    public static class CommentsBean {
        private String id;
        private String account;
        private String content;
        private int star;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
