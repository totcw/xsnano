package com.betterda.xsnano.javabean;

import java.util.List;

/**
 * 缓存类
 * Created by Administrator on 2016/8/22.
 */
public class Cache {


    /**
     * sort_code :
     * sort_name : 默认
     */

    private List<SortMapItemsBean> sortMapItems;
    /**
     * id :
     * shop_catalog_name : 全部
     * create_date :
     */

    private List<TShopCatalogItemsBean> tShopCatalogItems;
    /**
     * createName : 管理员
     * createBy : admin
     * createDate : Jul 6, 2016 11:49:06 AM
     * shopServiceName : 全部
     * status : Y
     * remark : 全部
     * id : 1
     */

    private List<TShopServiceItemsBean> tShopServiceItems;

    public List<SortMapItemsBean> getSortMapItems() {
        return sortMapItems;
    }

    public void setSortMapItems(List<SortMapItemsBean> sortMapItems) {
        this.sortMapItems = sortMapItems;
    }

    public List<TShopCatalogItemsBean> getTShopCatalogItems() {
        return tShopCatalogItems;
    }

    public void setTShopCatalogItems(List<TShopCatalogItemsBean> tShopCatalogItems) {
        this.tShopCatalogItems = tShopCatalogItems;
    }

    public List<TShopServiceItemsBean> getTShopServiceItems() {
        return tShopServiceItems;
    }

    public void setTShopServiceItems(List<TShopServiceItemsBean> tShopServiceItems) {
        this.tShopServiceItems = tShopServiceItems;
    }

    public static class SortMapItemsBean {
        private String sort_code;
        private String sort_name;

        public String getSort_code() {
            return sort_code;
        }

        public void setSort_code(String sort_code) {
            this.sort_code = sort_code;
        }

        public String getSort_name() {
            return sort_name;
        }

        public void setSort_name(String sort_name) {
            this.sort_name = sort_name;
        }
    }

    public static class TShopCatalogItemsBean {
        private String id;
        private String shop_catalog_name;
        private String create_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopCatalogName() {
            return shop_catalog_name;
        }

        public void setShopCatalogName(String shopCatalogName) {
            this.shop_catalog_name = shopCatalogName;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
    }

    public static class TShopServiceItemsBean {
        private String createName;
        private String createBy;
        private String createDate;
        private String shopServiceName;
        private String status;
        private String remark;
        private String id;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
