package com.Toy2.Cust.Domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;

public class CustDto {
    @NotNull(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "유효한 이메일 주소를 입력하세요.")
    private String c_email;
    private String c_stat_cd;
    @NotNull(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "패스워드는 8~15자 사이이며, 숫자, 문자, 특수문자를 포함해야 합니다.")
    private String c_pwd;
    @NotNull(message = "이름은 필수 항목입니다.")
    private String c_name;
    private String c_nick;

    @NotNull(message = "생일은 필수 항목입니다.")
    private String c_birth;
    @NotNull(message = "성별은 필수 항목입니다.")
    private String c_gnd;
    @NotNull(message = "휴대전화를 입력해주세요.")
    @Pattern(regexp = "^\\d{11}$", message = "11자리의 숫자만 입력가능합니다.")
    private String c_phn;
    private String c_zip;
    private String c_road_a;
    private String c_det_a;
    private char c_admin;
    private char sms_agr;
    private char email_agr;
    private Date reg_date;
    private Date login_dt;
    private int tot_amt;
    private Date frst_reg_dt;
    private String frst_reg_id;
    private Date last_mod_dt;
    private String last_mod_id;

    public CustDto() {
    }

    /* 고객 생성 */
    public static CustDto custDto(String c_email) {
        return new CustDto(c_email, "1234", "bbb", "bnick", "19980223", "W", "01027449853", "02001", "Gangnam-gu, Seoul", "8", 'N');
    }

    public CustDto(String c_email, String c_pwd, String c_name, String c_nick, String c_birth, String c_gnd, String c_phn, String c_zip, String c_road_a, String c_det_a, char c_admin) {
        this.c_email = c_email;
        this.c_pwd = c_pwd;
        this.c_name = c_name;
        this.c_nick = c_nick;
        this.c_birth = c_birth;
        this.c_gnd = c_gnd;
        this.c_phn = c_phn;
        this.c_zip = c_zip;
        this.c_road_a = c_road_a;
        this.c_det_a = c_det_a;
        this.c_admin = c_admin;
    }

    public CustDto(String c_email, String c_stat_cd, String c_pwd, String c_name, String c_nick, String c_birth, String c_gnd, String c_phn, String c_zip, String c_road_a, String c_det_a, char c_admin, char sms_agr, char email_agr, Date reg_date, Date login_dt, int tot_amt, Date frst_reg_dt, String frst_reg_id, Date last_mod_dt, String last_mod_id) {
        this.c_email = c_email;
        this.c_stat_cd = c_stat_cd;
        this.c_pwd = c_pwd;
        this.c_name = c_name;
        this.c_nick = c_nick;
        this.c_birth = c_birth;
        this.c_gnd = c_gnd;
        this.c_phn = c_phn;
        this.c_zip = c_zip;
        this.c_road_a = c_road_a;
        this.c_det_a = c_det_a;
        this.c_admin = c_admin;
        this.sms_agr = sms_agr;
        this.email_agr = email_agr;
        this.reg_date = reg_date;
        this.login_dt = login_dt;
        this.tot_amt = tot_amt;
        this.frst_reg_dt = frst_reg_dt;
        this.frst_reg_id = frst_reg_id;
        this.last_mod_dt = last_mod_dt;
        this.last_mod_id = last_mod_id;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }

    public String getC_stat_cd() {
        return c_stat_cd;
    }

    public void setC_stat_cd(String c_stat_cd) {
        this.c_stat_cd = c_stat_cd;
    }

    public String getC_pwd() {
        return c_pwd;
    }

    public void setC_pwd(String c_pwd) {
        this.c_pwd = c_pwd;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_nick() {
        return c_nick;
    }

    public void setC_nick(String c_nick) {
        this.c_nick = c_nick;
    }

    public String getC_birth() {
        return c_birth;
    }

    public void setC_birth(String c_birth) {
        this.c_birth = c_birth;
    }

    public String getC_gnd() {
        return c_gnd;
    }

    public void setC_gnd(String c_gnd) {
        this.c_gnd = c_gnd;
    }

    public String getC_phn() {
        return c_phn;
    }

    public void setC_phn(String c_phn) {
        this.c_phn = c_phn;
    }

    public String getC_zip() {
        return c_zip;
    }

    public void setC_zip(String c_zip) {
        this.c_zip = c_zip;
    }

    public String getC_road_a() {
        return c_road_a;
    }

    public void setC_road_a(String c_road_a) {
        this.c_road_a = c_road_a;
    }


    public String getC_det_a() {
        return c_det_a;
    }

    public void setC_det_a(String c_det_a) {
        this.c_det_a = c_det_a;
    }

    public char getC_admin() {
        return c_admin;
    }

    public void setC_admin(char c_admin) {
        this.c_admin = c_admin;
    }

    public char getSms_agr() {
        return sms_agr;
    }

    public void setSms_agr(char sms_agr) {
        this.sms_agr = sms_agr;
    }

    public char getEmail_agr() {
        return email_agr;
    }

    public void setEmail_agr(char email_agr) {
        this.email_agr = email_agr;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Date getLogin_dt() {
        return login_dt;
    }

    public void setLogin_dt(Date login_dt) {
        this.login_dt = login_dt;
    }

    public int getTot_amt() {
        return tot_amt;
    }

    public void setTot_amt(int tot_amt) {
        this.tot_amt = tot_amt;
    }

    public Date getFrst_reg_dt() {
        return frst_reg_dt;
    }

    public void setFrst_reg_dt(Date frst_reg_dt) {
        this.frst_reg_dt = frst_reg_dt;
    }

    public String getFrst_reg_id() {
        return frst_reg_id;
    }

    public void setFrst_reg_id(String frst_reg_id) {
        this.frst_reg_id = frst_reg_id;
    }

    public Date getLast_mod_dt() {
        return last_mod_dt;
    }

    public void setLast_mod_dt(Date last_mod_dt) {
        this.last_mod_dt = last_mod_dt;
    }

    public String getLast_mod_id() {
        return last_mod_id;
    }

    public void setLast_mod_id(String last_mod_id) {
        this.last_mod_id = last_mod_id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustDto custDto = (CustDto) object;
        return c_gnd == custDto.c_gnd && c_admin == custDto.c_admin && sms_agr == custDto.sms_agr && email_agr == custDto.email_agr && tot_amt == custDto.tot_amt && Objects.equals(c_email, custDto.c_email) && Objects.equals(c_stat_cd, custDto.c_stat_cd) && Objects.equals(c_pwd, custDto.c_pwd) && Objects.equals(c_name, custDto.c_name) && Objects.equals(c_nick, custDto.c_nick) && Objects.equals(c_birth, custDto.c_birth) && Objects.equals(c_phn, custDto.c_phn) && Objects.equals(c_zip, custDto.c_zip) && Objects.equals(c_road_a, custDto.c_road_a) && Objects.equals(c_det_a, custDto.c_det_a) && Objects.equals(reg_date, custDto.reg_date) && Objects.equals(login_dt, custDto.login_dt) && Objects.equals(frst_reg_dt, custDto.frst_reg_dt) && Objects.equals(frst_reg_id, custDto.frst_reg_id) && Objects.equals(last_mod_dt, custDto.last_mod_dt) && Objects.equals(last_mod_id, custDto.last_mod_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c_email, c_stat_cd, c_pwd, c_name, c_nick, c_birth, c_gnd, c_phn, c_zip, c_road_a, c_det_a, c_admin, sms_agr, email_agr, reg_date, login_dt, tot_amt, frst_reg_dt, frst_reg_id, last_mod_dt, last_mod_id);
    }

    @Override
    public String toString() {
        return "CustDto{" +
                "c_email='" + c_email + '\'' +
                ", c_stat_cd='" + c_stat_cd + '\'' +
                ", c_pwd='" + c_pwd + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_nick='" + c_nick + '\'' +
                ", c_birth='" + c_birth + '\'' +
                ", c_gnd=" + c_gnd +
                ", c_phn='" + c_phn + '\'' +
                ", c_zip='" + c_zip + '\'' +
                ", c_road_a='" + c_road_a + '\'' +
                ", c_det_a='" + c_det_a + '\'' +
                ", c_admin=" + c_admin +
                ", sms_agr=" + sms_agr +
                ", email_agr=" + email_agr +
                ", reg_date=" + reg_date +
                ", login_dt=" + login_dt +
                ", tot_amt=" + tot_amt +
                ", frst_reg_dt=" + frst_reg_dt +
                ", frst_reg_id='" + frst_reg_id + '\'' +
                ", last_mod_dt=" + last_mod_dt +
                ", last_mod_id='" + last_mod_id + '\'' +
                '}';
    }
}