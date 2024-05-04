package com.example.orderfood.bean;

import com.example.orderfood.dao.OrderDao;

import java.util.List;

public class OrderBean {




    private String s_order_id;

    private String s_order_time;

    private String s_order_user_id;




    private String s_order_user_name;

    private String s_order_user_img;

    private String s_order_business;


    private String s_order_business_name;
    private String s_order_business_img;


    private String s_order_business_id;


    private String s_order_sta;


    private String s_order_address;

    public String getS_order_id() {
        return s_order_id;
    }

    public void setS_order_id(String s_order_id) {
        this.s_order_id = s_order_id;
    }

    public String getS_order_time() {
        return s_order_time;
    }

    public void setS_order_time(String s_order_time) {
        this.s_order_time = s_order_time;
    }

    public String getS_order_user_id() {
        return s_order_user_id;
    }

    public void setS_order_user_id(String s_order_user_id) {
        this.s_order_user_id = s_order_user_id;
    }

    public String getS_order_user_name() {
        return s_order_user_name;
    }

    public void setS_order_user_name(String s_order_user_name) {
        this.s_order_user_name = s_order_user_name;
    }

    public String getS_order_user_img() {
        return s_order_user_img;
    }

    public void setS_order_user_img(String s_order_user_img) {
        this.s_order_user_img = s_order_user_img;
    }

    public String getS_order_business() {
        return s_order_business;
    }

    public void setS_order_business(String s_order_business) {
        this.s_order_business = s_order_business;
    }

    public String getS_order_business_name() {
        return s_order_business_name;
    }

    public void setS_order_business_name(String s_order_business_name) {
        this.s_order_business_name = s_order_business_name;
    }

    public String getS_order_business_img() {
        return s_order_business_img;
    }

    public void setS_order_business_img(String s_order_business_img) {
        this.s_order_business_img = s_order_business_img;
    }

    public String getS_order_business_id() {
        return s_order_business_id;
    }

    public void setS_order_business_id(String s_order_business_id) {
        this.s_order_business_id = s_order_business_id;
    }

    public String getS_order_sta() {
        return s_order_sta;
    }

    public void setS_order_sta(String s_order_sta) {
        this.s_order_sta = s_order_sta;
    }

    public String getS_order_address() {
        return s_order_address;
    }

    public void setS_order_address(String s_order_address) {
        this.s_order_address = s_order_address;
    }

    public List<OrderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetail> detailList) {
        this.detailList = detailList;
    }

    public OrderBean(String s_order_id, String s_order_time, String s_order_user_id, String s_order_user_name, String s_order_user_img, String s_order_business, String s_order_business_name, String s_order_business_img, String s_order_business_id, String s_order_sta, String s_order_address) {
        this.s_order_id = s_order_id;
        this.s_order_time = s_order_time;
        this.s_order_user_id = s_order_user_id;
        this.s_order_user_name = s_order_user_name;
        this.s_order_user_img = s_order_user_img;
        this.s_order_business = s_order_business;
        this.s_order_business_name = s_order_business_name;
        this.s_order_business_img = s_order_business_img;
        this.s_order_business_id = s_order_business_id;
        this.s_order_sta = s_order_sta;
        this.s_order_address = s_order_address;

        this.detailList=OrderDao.getAllOrderDetailByBusiness(s_order_business_id);
    }

    //这个是存储所有的订单内容不对应的详情内容
    private List<OrderDetail> detailList;

}