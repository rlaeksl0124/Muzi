package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import com.Toy2.Cust.Service.CustService;
import com.Toy2.Cust.Service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    CustDao custDao;
    @Autowired
    CustService custService;
    @Autowired
    PasswordService passwordService;

    /* 회원가입폼 보여주기 */
    @GetMapping("/add")
    public String signup(){
        return "Cust/registerForm";
    }


    /* 고객 회원가입 POST */
    @PostMapping("/add")
    public String signup(@ModelAttribute @Valid CustDto custDto,
                         BindingResult result, HttpSession session) {

        /* custDto에 Valid 검증 에러가 있을경우 회원가입폼으로 이동 */
        try {
            if(result.hasErrors()){
                System.out.println(result);
                return "Cust/registerForm";
            }

            /* 고객의 비밀번호를 가져와서 */
            String rawPwd = custDto.getC_pwd();
            /* 패스워드 암호화 */
            String encryptedPwd = passwordService.encodePassword(rawPwd);
            /* 고객객체에 저장 */
            custDto.setC_pwd(encryptedPwd);

            /* 세션추가 */
            session.setAttribute("c_email", custDto.getC_email());

            System.out.println("회원정보: " + custDto);

            /* 고객정보 DB에 insert */
            /* 회원가입 메시지 출력 */
            custDao.insert(custDto);
        }catch (Exception e){
            e.printStackTrace();
            /* 회원가입에 insert가 실패했을경우 ? */
        }
        return "redirect:/";
    }

    /* 회원가입 이메일 중복검사 */
    @GetMapping("/emailDumpCheck")
    public ResponseEntity<Boolean> emailCheck(String c_email) {
        try {
            /* email 중복 체크 */
            boolean result = custService.emailDumpCheck(c_email);

            /* 상태 저장 */
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


    private final Map<String, String> authCodeStorage = new HashMap<>();
    /* 이메일인증요청 */
    @GetMapping(value = "/mailAuth",produces ="text/plain;charset=UTF-8")
    public ResponseEntity<String> mailAuth(String email) {
        try {
            System.out.println("이메일 인증요청");
            System.out.println("인증 이메일 : "+email);

            /* 이메일 인증번호 생성 및 메일전송하는 service 호출 */
            String newAuthCode = custService.mailsend(email);
            System.out.println("AuthCode:"+newAuthCode);

            /* 고객의 email을 키로, value는 생성된 인증번호를 저장 */
            authCodeStorage.put(email, newAuthCode);

            /* HHTP 상태 코드와 함께 텍스트 응답을 보낸다 */
            return ResponseEntity.ok().body("인증번호가 이메일로 발송되었습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 인증 실패");
        }
    }

    /* 인증번호 검증 : POST 요청으로 전송된 데이터를 받아 서버에 저장된 인증번호와 비교 */
    @PostMapping(value = "/verifyAuthCode", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> verifyAuthCode(@RequestBody Map<String, String> requestBody){
        try {
            /* Map 형태의 email과 고객이 입력한 authCode를 전달 받는다 */
            String email = requestBody.get("email");
            String authCode = requestBody.get("authCode");

            /* 맵에 저장되있는 생성된 인증번호를 가져온다 */
            String storedCode = authCodeStorage.get(email);

            System.out.println("email: "+ email);
            System.out.println("authCode :" + authCode);
            System.out.println("storedCode :" + storedCode);

            /* 생성된 인증번호가 null 이거나 생성된 인증번호와 고객이 입력한 인증번호가 일치하지 않을경우 400에러 */
            if (storedCode == null || (!storedCode.equals(authCode))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증실패: 잘못된 인증번호입니다 다시 입력해주세요");
            }

            return ResponseEntity.ok().body("인증 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증과정에서 오류가 발생하였습니다");
        }
    }

}
