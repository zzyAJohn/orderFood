package com.example.orderfood.bean;

public class FoodBean {





    //alt+ ins
    private String s_food_id;


    private String s_business_id;

    private String s_food_name;

    private String s_food_des;

    private String s_food_price;

    private String s_food_img;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public FoodBean(String s_food_id, String s_business_id, String s_food_name, String s_food_des, String s_food_price, String s_food_img, String num) {
        this.s_food_id = s_food_id;
        this.s_business_id = s_business_id;
        this.s_food_name = s_food_name;
        this.s_food_des = s_food_des;
        this.s_food_price = s_food_price;
        this.s_food_img = s_food_img;
        this.num = num;
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "s_food_id='" + s_food_id + '\'' +
                ", s_business_id='" + s_business_id + '\'' +
                ", s_food_name='" + s_food_name + '\'' +
                ", s_food_des='" + s_food_des + '\'' +
                ", s_food_price='" + s_food_price + '\'' +
                ", s_food_img='" + s_food_img + '\'' +
                '}';
    }

    public String getS_food_id() {
        return s_food_id;
    }

    public void setS_food_id(String s_food_id) {
        this.s_food_id = s_food_id;
    }

    public String getS_business_id() {
        return s_business_id;
    }

    public void setS_business_id(String s_business_id) {
        this.s_business_id = s_business_id;
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

    public FoodBean() {
    }

    public FoodBean(String s_food_id, String s_business_id, String s_food_name, String s_food_des, String s_food_price, String s_food_img) {
        this.s_food_id = s_food_id;
        this.s_business_id = s_business_id;
        this.s_food_name = s_food_name;
        this.s_food_des = s_food_des;
        this.s_food_price = s_food_price;
        this.s_food_img = s_food_img;
    }
}
