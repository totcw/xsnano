package com.betterda.xsnano.javabean;

import java.util.List;

/**
 * 订单详情
 * Created by Administrator on 2016/6/30.
 */
public class OrderListDetail {

    /**
     * id : 4028810155a052570155a053dad20003
     * create_date : Jun 30, 2016 4:01:35 PM
     * order_num : 864394102081710
     * account : 15160700380
     * pay_status : N
     * status : init
     * order_type : 2
     * carry : 20
     * amount : 100.0
     * fee : 0
     * ptotal : 100.0
     * express_name : 20
     * golden : 0
     * contact_user : 546
     * contact_phone : 1232
     * contact_address : 大方法爱动
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
        private String create_date;
        private String order_num;
        private String account;
        private String pay_status;
        private String status;
        private String order_type;
        private String carry; //配送方式
        private String amount;  //订单的金额
        private String fee; //运费
        private String ptotal;
        private String express_name;
        private int golden;
        private String contact_user;
        private String contact_phone;
        private String contact_address;
        private String is_need_invoice;//是否需要发票
        private String is_customer_service;//是否售后过  Y N

        public String getIs_need_invoice() {
            return is_need_invoice;
        }

        public void setIs_need_invoice(String is_need_invoice) {
            this.is_need_invoice = is_need_invoice;
        }

        public String getIs_customer_service() {
            return is_customer_service;
        }

        public void setIs_customer_service(String is_customer_service) {
            this.is_customer_service = is_customer_service;
        }

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

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
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

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public int getGolden() {
            return golden;
        }

        public void setGolden(int golden) {
            this.golden = golden;
        }

        public String getContact_user() {
            return contact_user;
        }

        public void setContact_user(String contact_user) {
            this.contact_user = contact_user;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getContact_address() {
            return contact_address;
        }

        public void setContact_address(String contact_address) {
            this.contact_address = contact_address;
        }
    }

    public static class DetailsBean {
        private String order_no;
        private int number;
        private String id;  //商品id
        private String little_picture;
        private String name;
        private String sale_price;

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
