package com.example.orderfood.bean;

/**
 * 详情
 */
public class OrderDetail {


    private String s_order_business_id;

    private String s_food_id;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "s_order_business_id='" + s_order_business_id + '\'' +
                ", s_food_id='" + s_food_id + '\'' +
                ", s_food_name='" + s_food_name + '\'' +
                ", s_food_des='" + s_food_des + '\'' +
                ", s_food_price='" + s_food_price + '\'' +
                ", s_food_img='" + s_food_img + '\'' +
                ", s_food_time='" + s_food_time + '\'' +
                ", s_food_num='" + s_food_num + '\'' +
                '}';
    }

    public OrderDetail(String s_order_business_id, String s_food_id, String s_food_name, String s_food_des, String s_food_price, String s_food_img, String s_food_time, String s_food_num) {
        this.s_order_business_id = s_order_business_id;
        this.s_food_id = s_food_id;
        this.s_food_name = s_food_name;
        this.s_food_des = s_food_des;
        this.s_food_price = s_food_price;
        this.s_food_img = s_food_img;
        this.s_food_time = s_food_time;
        this.s_food_num = s_food_num;
    }

    public OrderDetail() {
    }

    public String getS_order_business_id() {
        return s_order_business_id;
    }

    public void setS_order_business_id(String s_order_business_id) {
        this.s_order_business_id = s_order_business_id;
    }

    public String getS_food_id() {
        return s_food_id;
    }

    public void setS_food_id(String s_food_id) {
        this.s_food_id = s_food_id;
    }

    public String getS_food_name() {
        return s_food_name;
    }

    public void setS_food_name(String s_food_name) {
        this.s_food_name = s_food_name;
    }

    public String getS_food_des() {
        return s_food_des;
    }

    public void setS_food_des(String s_food_des) {
        this.s_food_des = s_food_des;
    }

    public String getS_food_price() {
        return s_food_price;
    }

    public void setS_food_price(String s_food_price) {
        this.s_food_price = s_food_price;
    }

    public String getS_food_img() {
        return s_food_img;
    }

    public void setS_food_img(String s_food_img) {
        this.s_food_img = s_food_img;
    }

    public String getS_food_time() {
        return s_food_time;
    }

    public void setS_food_time(String s_food_time) {
        this.s_food_time = s_food_time;
    }

    public String getS_food_num() {
        return s_food_num;
    }

    public void setS_food_num(String s_food_num) {
        this.s_food_num = s_food_num;
    }

    private String s_food_name;

    private String s_food_des;

    private String s_food_price;


    private String s_food_img;


    private String s_food_time;



    private String s_food_num;





}
