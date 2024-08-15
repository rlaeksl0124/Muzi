package com.Toy2.Faq.Domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class FaqCateDto {
    private Integer cate_no;
    private String cate_mid;
    private String cate_small;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDateTime first_reg_date;
    private String first_reg_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDateTime last_mod_date;
    private String last_mod_id;

    // 기본 생성자
    public FaqCateDto(){}

    // 생성자
    public FaqCateDto(Integer cate_no, String cate_mid, String cate_small){
        this.cate_no = cate_no;
        this.cate_mid = cate_mid;
        this.cate_small = cate_small;
        this.first_reg_date = LocalDateTime.now();
        this.first_reg_id = "admin@";
        this.last_mod_date = LocalDateTime.now();
        this.last_mod_id = "admin@";
    }

    public Integer getCate_no() {
        return cate_no;
    }

    public String getCate_mid() {
        return cate_mid;
    }

    public String getCate_small() {
        return cate_small;
    }

    public String getCateName(){
        return cate_mid + " " + cate_small;
    }

    public LocalDateTime getFirst_reg_date() {
        return first_reg_date;
    }

    public String getFirst_reg_id() {
        return first_reg_id;
    }

    public LocalDateTime getLast_mod_date() {
        return last_mod_date;
    }

    public String getLast_mod_id() {
        return last_mod_id;
    }

    public void setCate_no(Integer cate_no) {
        this.cate_no = cate_no;
    }

    public void setCate_mid(String cate_mid) {
        this.cate_mid = cate_mid;
    }

    public void setCate_small(String cate_small) {
        this.cate_small = cate_small;
    }

    public void setFirst_reg_date(LocalDateTime first_reg_date) {
        this.first_reg_date = first_reg_date;
    }

    public void setFirst_reg_id(String first_reg_id) {
        this.first_reg_id = first_reg_id;
    }

    public void setLast_mod_date(LocalDateTime last_mod_date) {
        this.last_mod_date = last_mod_date;
    }

    public void setLast_mod_id(String last_mod_id) {
        this.last_mod_id = last_mod_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaqCateDto that = (FaqCateDto) o;
        return Objects.equals(cate_no, that.cate_no) &&
                Objects.equals(cate_mid, that.cate_mid) &&
                Objects.equals(cate_small, that.cate_small);
    }

    @Override
    public String toString() {
        return "FaqCateDto{" +
                "cate_no=" + cate_no +
                ", cate_mid='" + cate_mid + '\'' +
                ", cate_small='" + cate_small + '\'' +
                ", first_reg_date=" + first_reg_date +
                ", first_reg_id='" + first_reg_id + '\'' +
                ", last_mod_date=" + last_mod_date +
                ", last_mod_id='" + last_mod_id + '\'' +
                '}';
    }
}
