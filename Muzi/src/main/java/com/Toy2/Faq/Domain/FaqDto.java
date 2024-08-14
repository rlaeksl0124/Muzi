package com.Toy2.Faq.Domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Valid
public class FaqDto {

    /* 필드 선언 */
    private Integer faq_no;           /* FAQ 게시글 번호도 int로 받음 */
    private Integer cate_no;
    private String categoryName;            // 카테고리를 이름으로 보기 위해 추가함
    private Integer faq_order;
    private char is_top;
    private String faq_title;
    private String faq_content;
    private String faq_writer;
    private String faq_closing;
    private String faq_att_file;
    private char faq_show;
    @DateTimeFormat(pattern = "yyyy-NMM-dd") private LocalDateTime faq_reg_date;
    private int faq_view_cnt;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDateTime first_reg_date;
    private String first_reg_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDateTime last_mod_date;
    private String last_mod_id;
    private String faq_admin;       /* 해당 FAQ 게시글을 등록한 담당자 */

    /* 기본 생성자 */
    public FaqDto() {}


    /* NOT NULL 채우는 생성자 */
    /*  (cate_no, faq_order, is_top, faq_title, faq_content) */
    public FaqDto(Integer cate_no, Integer faq_order, char is_top, String faq_title, String faq_content){
        this.cate_no = cate_no;
        this.faq_order = faq_order;
        this.is_top = is_top;
        this.faq_title = faq_title;
        this.faq_content = faq_content;

        /* DEFAULT 값 추가 */
        this.faq_writer = "test";
        this.faq_closing = "*답변이 충분하지 않으셨다면 1:1 상담을 이용해주세요.";
        this.faq_att_file = null;           // NULL로 설정
        this.faq_show = 'N';
        this.faq_reg_date = LocalDateTime.now();
        this.faq_view_cnt = 0;
        this.first_reg_date = LocalDateTime.now();
        this.first_reg_id = "test";
        this.last_mod_date = LocalDateTime.now();
        this.last_mod_id = "test";
        this.faq_admin = "admin";
    }

    /* Getter & Setter */
    public Integer getFaq_no() {
        return faq_no;
    }

    public void setFaq_no(Integer faq_no) {
        this.faq_no = faq_no;
    }

    public Integer getCate_no() {
        return cate_no;
    }

    public void setCate_no(Integer cate_no) {
        this.cate_no = cate_no;
    }

    // 카테고리 이름 Getter & Setter
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setFaq_view_cnt(int faq_view_cnt) {
        this.faq_view_cnt = faq_view_cnt;
    }

    public Integer getFaq_order() {
        return faq_order;
    }

    public void setFaq_order(Integer faq_order) {
        this.faq_order = faq_order;
    }

    public char getIs_top() {
        return is_top;
    }

    public void setIs_top(char is_top) {
        this.is_top = is_top;
    }

    public String getFaq_title() {
        return faq_title;
    }

    public void setFaq_title(String faq_title) {
        this.faq_title = faq_title;
    }

    public String getFaq_content() {
        return faq_content;
    }

    public void setFaq_content(String faq_content) {
        this.faq_content = faq_content;
    }

    public String getFaq_writer() {
        return faq_writer;
    }

    public void setFaq_writer(String faq_writer) {
        this.faq_writer = faq_writer;
    }

    public String getFaq_closing() {
        return faq_closing;
    }

    public void setFaq_closing(String faq_closing) {
        this.faq_closing = faq_closing;
    }

    public String getFaq_att_file() {
        return faq_att_file;
    }

    public void setFaq_att_file(String faq_att_file) {
        this.faq_att_file = faq_att_file;
    }

    public char getFaq_show() {
        return faq_show;
    }

    public void setFaq_show(char faq_show) {
        this.faq_show = faq_show;
    }

    public LocalDateTime getFaq_reg_date() {
        return faq_reg_date;
    }

    public void setFaq_reg_date(LocalDateTime faq_reg_date) {
        this.faq_reg_date = faq_reg_date;
    }

    public int getFaq_view_cnt() {
        return faq_view_cnt;
    }

    public void setView_cnt(int faq_view_cnt) {
        this.faq_view_cnt = faq_view_cnt;
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

    public String getFaq_admin() {
        return faq_admin;
    }

    public void setFaq_admin(String faq_admin) {
        this.faq_admin = faq_admin;
    }


    /* toString() */
    @Override
    public String toString() {
        return "FaqDto{" +
                "faq_no=" + faq_no +
                ", cate_no=" + cate_no +
                ", categoryName='" + categoryName + '\'' +
                ", faq_order=" + faq_order +
                ", is_top=" + is_top +
                ", faq_title='" + faq_title + '\'' +
                ", faq_content='" + faq_content + '\'' +
                ", faq_writer='" + faq_writer + '\'' +
                ", faq_closing='" + faq_closing + '\'' +
                ", faq_att_file='" + faq_att_file + '\'' +
                ", faq_show=" + faq_show +
                ", faq_reg_date=" + faq_reg_date +
                ", faq_view_cnt=" + faq_view_cnt +
                ", first_reg_date=" + first_reg_date +
                ", first_reg_id='" + first_reg_id + '\'' +
                ", last_mod_date=" + last_mod_date +
                ", last_mod_id='" + last_mod_id + '\'' +
                ", faq_admin='" + faq_admin + '\'' +
                '}';
    }


    /* equals() */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaqDto faqDto = (FaqDto) o;
        return faq_order == faqDto.faq_order &&
                Objects.equals(faq_no, faqDto.faq_no) &&
                Objects.equals(cate_no, faqDto.cate_no) &&
                Objects.equals(faq_title, faqDto.faq_title) &&
                Objects.equals(faq_content, faqDto.faq_content);
    }

    public String getFormattedRegDate(){
        if (faq_reg_date != null){          // faq_reg_date null이 아닌 경우
            // 등록일을 "yyyy-MM-dd" 형식으로 포맷하여 문자열로 변환
            return faq_reg_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";          // faq_reg_date이 null인 경우 빈 문자열 반환
    }
 }
