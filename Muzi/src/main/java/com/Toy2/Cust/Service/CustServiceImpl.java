package com.Toy2.Cust.Service;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class CustServiceImpl implements CustService, PasswordService {
    @Autowired
    private CustDao custDao;

    @Autowired
    private JavaMailSenderImpl mailSender;


    /* 회원가입폼 이메일 중복검사 */
    @Override
    public boolean emailDumpCheck(String c_email) throws Exception {
        CustDto custDto = custDao.selectEmail(c_email);
        return custDto == null;
    }

    /* 난수 인증번호 생성 */
    @Override
    public int emailRandom() throws Exception {
        Random random = new Random();
        /* 100,000 ~ 999,999 까지 랜덤난수 생성 */
        return random.nextInt(900000)+100000;
    }

    /* Mail setter 및 전송 */
    @Override
    public void setMailSend(String setFrom, String toEmail, String title, String content) throws Exception {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");

        helper.setFrom(setFrom);
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true);
        mailSender.send(msg);
    }

    /* 이메일 내용 제목, 문구 */
    @Override
    public String mailsend(String c_email) throws Exception {
        int num = emailRandom();
        String setFrom = ".com";
        String title = "Muzi Authentication code number";
        String content = "고객님이 요청하신 인증번호는 : " + num + " 입니다.";
        setMailSend(setFrom, c_email, title, content);
        return String.valueOf(num);
    }


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /* 원시 비밀번호를 암호화 한다 */
    @Override
    public String encodePassword(String rawPassword, String c_email) throws Exception{
        return passwordEncoder.encode(rawPassword);
    };

    /* 원시 비밀번호와 암호화된 비밀번호와 일치하는지 확인 */
    @Override
    public boolean checkPassword(String rawPassword, String encodePassword) throws Exception {
        return passwordEncoder.matches(rawPassword, encodePassword);
    };

    /* 주어진 암호화된 비밀번호가 최신 암호화강도를 사용하고있는지 확인 */
    @Override
    public boolean needsUpgrade(String encodedPassword) throws Exception {
        return passwordEncoder.upgradeEncoding(encodedPassword);
    };

}
