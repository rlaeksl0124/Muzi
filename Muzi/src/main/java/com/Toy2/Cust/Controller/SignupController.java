package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import com.Toy2.Cust.Service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    CustDao custDao;
    @Autowired
    CustService custService;

    /* 회원가입폼 보여주기 */
    @GetMapping("/add")
    public String signup(){
        return "registerForm";
    }


    /* 고객 회원가입 POST */
    @PostMapping("/add")
    public String signup(@ModelAttribute @Valid CustDto custDto,
                         BindingResult result, HttpSession session) {

        /* custDto에 Valid 검증 에러가 있을경우 회원가입폼으로 이동 */
        try {
            if(result.hasErrors()){
                System.out.println(result);
                return "registerForm";
            }

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
    public ResponseEntity<Boolean> emailCheck(String c_email, RedirectAttributes rattr) {
        try {
            /* email 중복 체크 */
            boolean result = custService.emailDumpCheck(c_email);

            /* 상태 저장 */
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "이미 사용중인 이메일입니다");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    /* 이메일인증요청 */
    @GetMapping("/mailAuth")
    public ResponseEntity<String> mailAuth(String email) {
        try {
            System.out.println("이메일 인증요청");
            System.out.println("인증 이메일 : "+email);
            /* 이메일 인증번호 생성 및 메일전송하는 service 호출 */
            String authCode = custService.mailsend(email);
            return ResponseEntity.ok(authCode);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 인증 실패");
        }

    }

}
