package com.betterda.xsnano.javabean;

import java.util.List;

/**
 * 解析返回的商品评论
 * Created by Administrator on 2016/6/29.
 */
public class CommentP {
    /**
     * id : 40288101556671f10155667c7cdc0001
     * create_date : Jun 19, 2016 12:00:00 AM
     * product_id : 402881f055437ba70155437cd9450001
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
