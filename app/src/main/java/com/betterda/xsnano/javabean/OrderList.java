package com.betterda.xsnano.javabean;

import java.util.List;

/**
 * 订单列表
 * Created by Administrator on 2016/6/30.
 */
public class OrderList {


    /**
     * id : 4028810155a02e680155a030ebc20003
     * account : 15160700380
     * status : init
     * carry : 20
     * amount : 100.0
     * fee : 0
     * ptotal : 100.0
     * golden : 0
     */

    private OrderBean order;
    /**
     * order_no : 864394102081710
     * number : 1
     * id : 1
     * little_picture :
     * name : 洗车服务
     * sale_price : 100
     */

    private List<DetailsBean> details;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class OrderBean {
        private String id;
        private String account; //用户
        private String status; //订单状态
        private String create_date;//订单时间
        private String order_num; //订单号
        private String pay_status;//支付状态
        private String carry;
        private String amount; //订单金额
        private String fee;
        private String ptotal;
        private int golden;
        private String refund_status;

        public String getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(String refund_status) {
            this.refund_status = refund_status;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCarry() {
            return carry;
        }

        public void setCarry(String carry) {
            this.carry = carry;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getPtotal() {
            return ptotal;
        }

        public void setPtotal(String ptotal) {
            this.ptotal = ptotal;
        }

        public int getGolden() {
            return golden;
        }

        public void setGolden(int golden) {
            this.golden = golden;
        }
    }

    public static class DetailsBean {
        private String order_no;  //订单号
        private int number; //数量
        private String id; //商品id
        private String little_picture; //商品图片
        private String name; //商品名字
        private String sale_price;//价格
        private String is_comment; //是否评价  N 未评价  Y
        private String shop_id; //店铺id

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLittle_picture() {
            return little_picture;
        }

        public void setLittle_picture(String little_picture) {
            this.little_picture = little_picture;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }
    }
}
