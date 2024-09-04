package com.Toy2.Cust.Service;

public interface CustService {
    /* 이메일 중복체크 */
    boolean emailDumpCheck(String c_email) throws Exception;

    /* 랜덤 난수 생성 */
    int emailRandom() throws Exception;

    /* 이메일 setter 설정 */
    void setMailSend(String setFrom, String toEmail, String title, String content) throws Exception;

    /* 이메일 양식 */
    String mailsend(String c_email) throws Exception;
}


