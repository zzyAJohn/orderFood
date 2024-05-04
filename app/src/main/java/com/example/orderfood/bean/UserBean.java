package com.example.orderfood.bean;

public class UserBean {


    private String s_id;

    private String s_pwd;

    private String s_name;

    private String s_sex;

    private String s_address;

    private String s_phone;

    private String s_img;


    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_pwd() {
        return s_pwd;
    }

    public void setS_pwd(String s_pwd) {
        this.s_pwd = s_pwd;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_sex() {
        return s_sex;
    }

    public void setS_sex(String s_sex) {
        this.s_sex = s_sex;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public UserBean(String s_id, String s_pwd, String s_name, String s_sex, String s_address, String s_phone, String s_img) {
        this.s_id = s_id;
        this.s_pwd = s_pwd;
        this.s_name = s_name;
        this.s_sex = s_sex;
        this.s_address = s_address;
        this.s_phone = s_phone;
        this.s_img = s_img;
    }
}
