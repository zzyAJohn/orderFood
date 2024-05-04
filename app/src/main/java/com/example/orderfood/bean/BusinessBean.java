package com.example.orderfood.bean;

public class BusinessBean {

   private String s_id;
    private String s_pwd;
    private String s_name;
    private String s_describe;
    private String s_type;
    private String s_img;

    @Override
    public String toString() {
        return "BusinessBean{" +
                "s_id='" + s_id + '\'' +
                ", s_pwd='" + s_pwd + '\'' +
                ", s_name='" + s_name + '\'' +
                ", s_describe='" + s_describe + '\'' +
                ", s_type='" + s_type + '\'' +
                ", s_img='" + s_img + '\'' +
                '}';
    }

    public BusinessBean() {
    }

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

    public String getS_describe() {
        return s_describe;
    }

    public void setS_describe(String s_describe) {
        this.s_describe = s_describe;
    }

    public String getS_type() {
        return s_type;
    }

    public void setS_type(String s_type) {
        this.s_type = s_type;
    }

    public String getS_img() {
        return s_img;
    }

    public void setS_img(String s_img) {
        this.s_img = s_img;
    }

    public BusinessBean(String s_id, String s_pwd, String s_name, String s_describe, String s_type, String s_img) {
        this.s_id = s_id;
        this.s_pwd = s_pwd;
        this.s_name = s_name;
        this.s_describe = s_describe;
        this.s_type = s_type;
        this.s_img = s_img;
    }
}
