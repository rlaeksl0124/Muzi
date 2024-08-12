package com.Toy2.Notice.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NoticeDto {
    private Long notice_no;
    private String n_title;
    private String n_contents;
    private String c_email = "admin";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date n_createDate = new Date();
    private int n_ViewCnt;
    private String n_state;
    private String frst_reg_id = "admin";
    private Date frst_reg_dt;
    private String last_mod_id = "admin";
    private Date last_mod_dt;
    public NoticeDto() {}

    public Long getNotice_no() {
        return notice_no;
    }

    public void setNotice_no(Long notice_no) {
        this.notice_no = notice_no;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_contents() {
        return n_contents;
    }

    public void setN_contents(String n_contents) {
        this.n_contents = n_contents;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public Date getN_createDate() {
        return n_createDate;
    }

    public void setN_createDate(Date n_createDate) {
        this.n_createDate = n_createDate;
    }

    public int getN_ViewCnt() {
        return n_ViewCnt;
    }

    public void setN_ViewCnt(int n_ViewCnt) {
        this.n_ViewCnt = n_ViewCnt;
    }

    public String getN_state() {
        return n_state;
    }

    public void setN_state(String n_state) {
        this.n_state = n_state;
    }

    public String getFrst_reg_id() {
        return frst_reg_id;
    }

    public void setFrst_reg_id(String frst_reg_id) {
        this.frst_reg_id = frst_reg_id;
    }

    public Date getFrst_reg_dt() {
        return frst_reg_dt;
    }

    public void setFrst_reg_dt(Date frst_reg_dt) {
        this.frst_reg_dt = frst_reg_dt;
    }

    public String getLast_mod_id() {
        return last_mod_id;
    }

    public void setLast_mod_id(String last_mod_id) {
        this.last_mod_id = last_mod_id;
    }

    public Date getLast_mod_dt() {
        return last_mod_dt;
    }

    public void setLast_mod_dt(Date last_mod_dt) {
        this.last_mod_dt = last_mod_dt;
    }

    @Override
    public String toString() {
        return "NoticeDto{" +
                "notice_No=" + notice_no +
                ", n_title='" + n_title + '\'' +
                ", n_contents='" + n_contents + '\'' +
                ", n_writer='" + c_email + '\'' +
                ", n_createDate=" + n_createDate +
                ", n_ViewCnt=" + n_ViewCnt +
                ", n_state='" + n_state + '\'' +
                ", frst_reg_id='" + frst_reg_id + '\'' +
                ", frst_reg_dt=" + frst_reg_dt +
                ", last_mod_id='" + last_mod_id + '\'' +
                ", last_mod_dt=" + last_mod_dt +
                '}';
    }
}
