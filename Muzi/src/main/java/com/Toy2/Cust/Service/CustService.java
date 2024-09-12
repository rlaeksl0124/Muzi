package com.Toy2.Cust.Service;

import com.Toy2.Cust.Domain.CustDto;

public interface CustService {
    /* 이메일 중복체크 */
    boolean emailDumpCheck(String c_email) throws Exception;

    /* 랜덤 난수 생성 */
    int emailRandom() throws Exception;

    /* 이메일 setter 설정 */
    void setMailSend(String setFrom, String toEmail, String title, String content) throws Exception;

    /* 이메일 양식 */
    String mailsend(String c_email) throws Exception;

    /* 1년이상 미접속 고객 휴먼처리 , 상태코드:H */
    int updateNotLoginUserStatusForAll() throws Exception;

    /* 로그인 실패시 count : 5회이상일경우 계정상태 H 변경 */
    void failedLoginCnt(CustDto custDto) throws Exception;

    /* 로그인 성공시 count reset */
    void resetFailedCnt(String c_email) throws Exception;
}


