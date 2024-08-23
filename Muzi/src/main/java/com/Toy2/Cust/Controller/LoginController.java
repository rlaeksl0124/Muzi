package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    CustDao custDao;

    /* 회원가입 */

    /* 미구현목록 리스트 */
    /* 패스워드는 암호화처리 */
    /* 장기 미로그인 유저 휴먼처리 → 상태코드 변경 */
    /* 고객이 탈퇴할경우 실제로 DB삭제 X → 상태코드만 변경 */
    /* 찜내역 */


    /* 로그인폼 보여주기 */
    @GetMapping("/login")
    public String login(){
        return "Cust/login";
    }

    /* 로그아웃 처리 */
    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    /* 로그인 POST */
    @PostMapping("/login")
    public String login(String c_email, String c_pwd, String toURL, HttpServletRequest request, RedirectAttributes rattr){
        try {
            /* 로그인실패: 로그인체크를통해 email과 pwd를 받아서 DB에 있는 정보와 일치한지 확인 */
            /* 메시지 출력후 login 페이지로 redirect */
            if(!loginCheck(c_email, c_pwd)){
                rattr.addFlashAttribute("loginFail", "msg");
                return "redirect:/login";
            }

            /* 로그인성공시 */
            /* 세션생성 */
            HttpSession session = request.getSession();
            session.setAttribute("c_email", c_email);

            /* 마지막 로그인일자를 업데이트하는 Dao 호출 */
            custDao.updateLogin(c_email);

            /* 로그인 성공시 이전 로그인이전 URL로 이동 */
            toURL = toURL==null || toURL.equals("") ? "/" : toURL;

        } catch(Exception e){
            e.printStackTrace();

        }
        return "redirect:"+toURL;
    }

    /* 로그인체크메서드 - 아이디가 null 인지, pwd가 일치하는지 확인 */
    private boolean loginCheck(String c_email, String c_pwd){
        CustDto cust = null;
        try {
            /* email로 DB 고객 조회 */
            cust = custDao.selectEmail(c_email);
        } catch(Exception e){
            e.printStackTrace();
            /* DB에 고객이 없을경우 */
        }

        /* 고객이 null이 아니면서 비밀번호가 일치한지 비교후 return */
        return cust!=null && cust.getC_pwd().equals(c_pwd);
    }

}
