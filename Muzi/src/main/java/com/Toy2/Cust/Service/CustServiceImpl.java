package com.Toy2.Cust.Service;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class CustServiceImpl implements CustService, PasswordService {
    @Autowired
    private CustDao custDao;
    StringRedisTemplate redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    /* 회원가입폼 이메일 중복검사 */
    @Override
    public boolean emailDumpCheck(String c_email) throws Exception {
        CustDto custDto = custDao.selectEmail(c_email);
        return custDto == null;
    }

    /*--------------------------- 회원가입 이메일 인증 시작 ---------------------------*/
    /* 난수 인증번호 생성 */
    @Override
    public int emailRandom() {
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
/*---------------------------- 회원가입 이메일 인증 끝 ----------------------------*/

/*----------------------------- 비밀번호 암호화 시작 -----------------------------*/

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /* 원시 비밀번호를 암호화 한다 */
    @Override
    public String encodePassword(String rawPassword) throws Exception{
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

    /*----------------------------- 비밀번호 암호화 끝 -----------------------------*/


    /* 1년이상 미접속 고객 휴먼처리 , 상태코드:H */
    @Override
    public int updateNotLoginUserStatusForAll() throws Exception {
        return custDao.updateNotLoginUserStatusForAll();
    }

    /* 로그인실패횟수 증가*/
    @Override
    public void failedLoginCnt(CustDto custDto) throws Exception {

        System.out.println("현재 실패 횟수 (DB 조회): " + custDto.getFailed_attempts());

        /* 로스인 실패시 횟수 1증가 */
        int failCnt = custDto.getFailed_attempts();
        failCnt++;

        /* setter 저장 */
        custDto.setFailed_attempts(failCnt);
        System.out.println("로그인 실패 후 실패 횟수: " + failCnt);

        /* 실패횟수가 5 이상이면 계정Lock */
        if(failCnt >= 5){
            lockAccount(custDto);
            System.out.println("계정잠금");
        }

        custDao.updateCust(custDto);
    }

    /* 계정 Lock */
    public void lockAccount(CustDto custDto) throws Exception {
        /* 상태코드를 H로 바꾸고 update 실행 */
        custDto.setC_stat_cd("H");
        custDao.updateCust(custDto);
    }

    /* 고객이 로그인할경우 로그인실패횟수 Reset */
    @Override
    public void resetFailedCnt(String c_email) throws Exception {
        /* 고객을 select */
        CustDto custDto = custDao.selectEmail(c_email);
        /* 고객을 조회할수없을경우 */
        if(custDto != null){
            custDto.setFailed_attempts(0);
            custDao.updateCust(custDto);
        }
    }
}
