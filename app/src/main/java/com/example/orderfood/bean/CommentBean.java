package com.example.orderfood.bean;

public class CommentBean {

    private  String s_comment_id ;
    private  String s_comment_user_id ;
    private  String s_comment_user_name;
    private  String s_comment_user_img;
    private  String s_comment_contenct;
    private  String s_comment_img ;
    private  String s_comment_time ;
    private  String s_comment_business_id;

    public CommentBean(String s_comment_id, String s_comment_user_id, String s_comment_user_name, String s_comment_user_img, String s_comment_contenct, String s_comment_img, String s_comment_time, String s_comment_business_id, String s_comment_score) {
        this.s_comment_id = s_comment_id;
        this.s_comment_user_id = s_comment_user_id;
        this.s_comment_user_name = s_comment_user_name;
        this.s_comment_user_img = s_comment_user_img;
        this.s_comment_contenct = s_comment_contenct;
        this.s_comment_img = s_comment_img;
        this.s_comment_time = s_comment_time;
        this.s_comment_business_id = s_comment_business_id;
        this.s_comment_score = s_comment_score;
    }

    public String getS_comment_id() {
        return s_comment_id;
    }

    public CommentBean() {
    }

    public void setS_comment_id(String s_comment_id) {
        this.s_comment_id = s_comment_id;
    }

    public String getS_comment_user_id() {
        return s_comment_user_id;
    }

    public void setS_comment_user_id(String s_comment_user_id) {
        this.s_comment_user_id = s_comment_user_id;
    }

    public String getS_comment_user_name() {
        return s_comment_user_name;
    }

    public void setS_comment_user_name(String s_comment_user_name) {
        this.s_comment_user_name = s_comment_user_name;
    }

    public String getS_comment_user_img() {
        return s_comment_user_img;
    }

    public void setS_comment_user_img(String s_comment_user_img) {
        this.s_comment_user_img = s_comment_user_img;
    }

    public String getS_comment_contenct() {
        return s_comment_contenct;
    }

    public void setS_comment_contenct(String s_comment_contenct) {
        this.s_comment_contenct = s_comment_contenct;
    }

    public String getS_comment_img() {
        return s_comment_img;
    }

    public void setS_comment_img(String s_comment_img) {
        this.s_comment_img = s_comment_img;
    }

    public String getS_comment_time() {
        return s_comment_time;
    }

    public void setS_comment_time(String s_comment_time) {
        this.s_comment_time = s_comment_time;
    }

    public String getS_comment_business_id() {
        return s_comment_business_id;
    }

    public void setS_comment_business_id(String s_comment_business_id) {
        this.s_comment_business_id = s_comment_business_id;
    }

    public String getS_comment_score() {
        return s_comment_score;
    }

    public void setS_comment_score(String s_comment_score) {
        this.s_comment_score = s_comment_score;
    }

    private  String s_comment_score;
}
