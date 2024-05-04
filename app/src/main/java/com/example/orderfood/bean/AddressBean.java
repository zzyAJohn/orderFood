package com.example.orderfood.bean;

public class AddressBean {

   private String s_id;

    public AddressBean(String s_id, String s_user_id, String s_user_name, String s_user_address, String s_phone) {
        this.s_id = s_id;
        this.s_user_id = s_user_id;
        this.s_user_name = s_user_name;
        this.s_user_address = s_user_address;
        this.s_phone = s_phone;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_user_id() {
        return s_user_id;
    }

    public void setS_user_id(String s_user_id) {
        this.s_user_id = s_user_id;
    }

    public String getS_user_name() {
        return s_user_name;
    }

    public void setS_user_name(String s_user_name) {
        this.s_user_name = s_user_name;
    }

    public String getS_user_address() {
        return s_user_address;
    }

    public void setS_user_address(String s_user_address) {
        this.s_user_address = s_user_address;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    private String s_user_id;
    private String s_user_name;
    private String s_user_address;
    private String s_phone;

}
