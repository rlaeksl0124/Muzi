package com.Toy2.Notice.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class InqDto {
    private int inq_no;
    private String inq_admin;
    private int cate_no;
    private String c_email;
    private String inq_title;
    private String inq_content;
    private String inq_att_file;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime inq_date;
    private char inq_status;
    private int inq_view_cnt;
    private char inq_sms;
    private char inq_email;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime first_reg_date;
    private String first_reg_id;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime last_mod_date;
    private String last_mod_id;

    // 기본 생성자
    public InqDto(){}

    // 생성자
    public InqDto(int cate_no, String c_email, String inq_title, String inq_content, String inq_att_file,
                  char inq_sms, char inq_email) {
        this.cate_no = cate_no;
        this.c_email = c_email;
        this.inq_title = inq_title;
        this.inq_content = inq_content;
        this.inq_att_file = inq_att_file;
        this.inq_sms = inq_sms;
        this.inq_email = inq_email;

        // DEFAULT 값들
        this.inq_admin = "admin";
        this.inq_date = LocalDateTime.now();
        this.inq_status = 'Y';
        this.inq_view_cnt = 0;
        this.first_reg_date = LocalDateTime.now();
        this.first_reg_id = "test";
        this.last_mod_date = LocalDateTime.now();
        this.last_mod_id = "test";
    }

    // Getter & Setter
    public int getInq_no() {
        return inq_no;
    }

    public void setInq_no(int inq_no) {
        this.inq_no = inq_no;
    }

    public String getInq_admin() {
        return inq_admin;
    }

    public void setInq_admin(String inq_admin) {
        this.inq_admin = inq_admin;
    }

    public int getCate_no() {
        return cate_no;
    }

    public void setCate_no(int cate_no) {
        this.cate_no = cate_no;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getInq_title() {
        return inq_title;
    }

    public void setInq_title(String inq_title) {
        this.inq_title = inq_title;
    }

    public String getInq_content() {
        return inq_content;
    }

    public void setInq_content(String inq_content) {
        this.inq_content = inq_content;
    }

    public String getInq_att_file() {
        return inq_att_file;
    }

    public void setInq_att_file(String inq_att_file) {
        this.inq_att_file = inq_att_file;
    }

    public LocalDateTime getInq_date() {
        return inq_date;
    }

    public void setInq_date(LocalDateTime inq_date) {
        this.inq_date = inq_date;
    }

    public char getInq_status() {
        return inq_status;
    }

    public void setInq_status(char inq_status) {
        this.inq_status = inq_status;
    }

    public int getInq_view_cnt() {
        return inq_view_cnt;
    }

    public void setInq_view_cnt(int inq_view_cnt) {
        this.inq_view_cnt = inq_view_cnt;
    }

    public char getInq_sms() {
        return inq_sms;
    }

    public void setInq_sms(char inq_sms) {
        this.inq_sms = inq_sms;
    }

    public char getInq_email() {
        return inq_email;
    }

    public void setInq_email(char inq_email) {
        this.inq_email = inq_email;
    }

    public LocalDateTime getFirst_reg_date() {
        return first_reg_date;
    }

    public void setFirst_reg_date(LocalDateTime first_reg_date) {
        this.first_reg_date = first_reg_date;
    }

    public String getFirst_reg_id() {
        return first_reg_id;
    }

    public void setFirst_reg_id(String first_reg_id) {
        this.first_reg_id = first_reg_id;
    }

    public LocalDateTime getLast_mod_date() {
        return last_mod_date;
    }

    public void setLast_mod_date(LocalDateTime last_mod_date) {
        this.last_mod_date = last_mod_date;
    }

    public String getLast_mod_id() {
        return last_mod_id;
    }

    public void setLast_mod_id(String last_mod_id) {
        this.last_mod_id = last_mod_id;
    }


    // toString()
    @Override
    public String toString() {
        return "InqDto{" +
                "inq_no=" + inq_no +
                ", inq_admin='" + inq_admin + '\'' +
                ", cate_no=" + cate_no +
                ", c_email='" + c_email + '\'' +
                ", inq_title='" + inq_title + '\'' +
                ", inq_content='" + inq_content + '\'' +
                ", inq_att_file='" + inq_att_file + '\'' +
                ", inq_date=" + inq_date +
                ", inq_status=" + inq_status +
                ", inq_view_cnt=" + inq_view_cnt +
                ", inq_sms=" + inq_sms +
                ", inq_email=" + inq_email +
                ", first_reg_date=" + first_reg_date +
                ", first_reg_id='" + first_reg_id + '\'' +
                ", last_mod_date=" + last_mod_date +
                ", last_mod_id='" + last_mod_id + '\'' +
                '}';
    }

    // equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InqDto inqDto = (InqDto) o;
        return inq_no == inqDto.inq_no &&
                cate_no == inqDto.cate_no &&
                Objects.equals(c_email, inqDto.c_email) &&
                Objects.equals(inq_title, inqDto.inq_title);
    }
}
