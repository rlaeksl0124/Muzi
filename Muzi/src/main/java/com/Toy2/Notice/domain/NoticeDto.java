package com.Toy2.Notice.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class NoticeDto {
    private Long noticeNum;
    private String title;
    private String contents;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    private int ViewCnt;
    private String state;
    private String frst_reg_id;
    private Date frst_reg_dt;
    private String last_mod_id;
    private Date last_mod_dt;
    public NoticeDto() {}
    public Long getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(Long noticeNum) {
        this.noticeNum = noticeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getViewCnt() {
        return ViewCnt;
    }

    public void setViewCnt(int viewCnt) {
        ViewCnt = viewCnt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
                "noticeNum=" + noticeNum +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", ViewCnt=" + ViewCnt +
                ", state='" + state + '\'' +
                ", frst_reg_id='" + frst_reg_id + '\'' +
                ", frst_reg_dt=" + frst_reg_dt +
                ", last_mod_id='" + last_mod_id + '\'' +
                ", last_mod_dt=" + last_mod_dt +
                '}';
    }
}
