package com.Toy2.Cust.Service;

public interface PasswordService {

    /* 원시 비밀번호를 암호화 한다 */
    String encodePassword(String rawPassword) throws Exception;

    /* 원시 비밀번호와 암호화된 비밀번호와 일치하는지 확인 */
    boolean checkPassword(String rawPassword, String encodePassword) throws Exception;

    /* 주어진 암호화된 비밀번호가 최신 암호화강도를 사용하고있는지 확인 */
    boolean needsUpgrade(String encodedPassword) throws Exception;

}
