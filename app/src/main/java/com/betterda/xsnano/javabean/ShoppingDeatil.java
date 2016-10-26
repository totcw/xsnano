package com.betterda.xsnano.javabean;

import java.util.List;

/**
 * 商品详细
 * Created by Administrator on 2016/6/30.
 */
public class ShoppingDeatil {

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
     * commentEntity : {"id":"40288101556671f10155667c7cdc0001","create_date":"Jun 19, 2016 12:00:00 AM","product_id":"1","account":"admin","nick_name":"admin","order_id":"201606191027","order_detail_id":"201606191027","content":"测试评论","star":5,"user_img":""}
     * commentReplyItems : [{"id":"40288101556671f10155667c7d090002","createName":"管理员","createBy":"admin","createDate":"Jun 19, 2016 12:00:00 AM","updateName":"","updateBy":"","commentId":"40288101556671f10155667c7cdc0001","account":"test","nickName":"test","content":"test评论内容","status":"","userImg":""}]
     */

    private List<Comment4ApiItemsBean> comment4ApiItems;

    private ShopcartEntityBean shopcartEntity;

    private double productTotalStar;//评分

    private  int totalComment; //评论总条数

    public ShopcartEntityBean getShopcartEntity() {
        return shopcartEntity;
    }

    public void setShopcartEntity(ShopcartEntityBean shopcartEntity) {
        this.shopcartEntity = shopcartEntity;
    }

    public double getProductTotalStar() {
        return productTotalStar;
    }

    public void setProductTotalStar(double productTotalStar) {
        this.productTotalStar = productTotalStar;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public List<Comment4ApiItemsBean> getComment4ApiItems() {
        return comment4ApiItems;
    }

    public void setComment4ApiItems(List<Comment4ApiItemsBean> comment4ApiItems) {
        this.comment4ApiItems = comment4ApiItems;
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
        private int payGolden;

        public int getPayGolden() {
            return payGolden;
        }

        public void setPayGolden(int payGolden) {
            this.payGolden = payGolden;
        }

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

    public static class Comment4ApiItemsBean {
        /**
         * id : 40288101556671f10155667c7cdc0001
         * create_date : Jun 19, 2016 12:00:00 AM
         * product_id : 1
         * account : admin
         * nick_name : admin
         * order_id : 201606191027
         * order_detail_id : 201606191027
         * content : 测试评论
         * star : 5
         * user_img :
         */

        private CommentEntityBean commentEntity;
        /**
         * id : 40288101556671f10155667c7d090002
         * createName : 管理员
         * createBy : admin
         * createDate : Jun 19, 2016 12:00:00 AM
         * updateName :
         * updateBy :
         * commentId : 40288101556671f10155667c7cdc0001
         * account : test
         * nickName : test
         * content : test评论内容
         * status :
         * userImg :
         */

        private List<CommentReplyItemsBean> commentReplyItems;

        public CommentEntityBean getCommentEntity() {
            return commentEntity;
        }

        public void setCommentEntity(CommentEntityBean commentEntity) {
            this.commentEntity = commentEntity;
        }

        public List<CommentReplyItemsBean> getCommentReplyItems() {
            return commentReplyItems;
        }

        public void setCommentReplyItems(List<CommentReplyItemsBean> commentReplyItems) {
            this.commentReplyItems = commentReplyItems;
        }

        public static class CommentEntityBean {
            private String id;
            private String create_date;
            private String product_id;
            private String account;
            private String nick_name;
            private String order_id;
            private String order_detail_id;
            private String content;
            private int star;
            private String user_img;

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

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_detail_id() {
                return order_detail_id;
            }

            public void setOrder_detail_id(String order_detail_id) {
                this.order_detail_id = order_detail_id;
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

            public String getUser_img() {
                return user_img;
            }

            public void setUser_img(String user_img) {
                this.user_img = user_img;
            }
        }

        public static class CommentReplyItemsBean {
            private String id;
            private String createName;
            private String createBy;
            private String createDate;
            private String updateName;
            private String updateBy;
            private String commentId;
            private String account;
            private String nickName;
            private String content;
            private String status;
            private String userImg;

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

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUserImg() {
                return userImg;
            }

            public void setUserImg(String userImg) {
                this.userImg = userImg;
            }
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
