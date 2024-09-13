package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import com.Toy2.Cust.Service.CustService;
import com.Toy2.Cust.Service.CustServiceImpl;
import com.Toy2.Cust.Service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Configuration
@EnableScheduling
@Controller
@CrossOrigin("http://localhost:5001")
public class LoginController {
    @Autowired
    CustDao custDao;

    @Autowired
    CustService custService;

    @Autowired
    PasswordService passwordService;

    /* 회원가입 */

    /* 미구현목록 리스트 */
    /* 고객이 탈퇴할경우 실제로 DB삭제 X → 상태코드만 변경 */
    /* 찜내역 */


    /* 로그인폼 보여주기 */
    @GetMapping("/login")
    public String login(){
        return "Cust/login";
    }

    /* 로그아웃 처리 */
    @GetMapping("/logout")
    public String logOut(HttpSession session) throws Exception {
        session.invalidate();
//        redisTemplate.delete("c_email");
        return "redirect:/";
    }

    /* 로그인 POST */
    @PostMapping("/login")
    public String login(String c_email, String c_pwd, String toURL, HttpServletRequest request, RedirectAttributes rattr){
        try {
            /* 로그인실패: 로그인체크를통해 email과 pwd를 받아서 DB에 있는 정보와 일치한지 확인 */
            /* 관리자 로그인시 일치하지않으면 메시지 출력후 login 페이지로 redirect */
            if("admin".equals(c_email) || "admin2".equals(c_email)){
                if(!adminLogin(c_pwd)){
                    rattr.addFlashAttribute("msg", "관리자 비밀번호가 일치하지 않습니다.");
                    return "redirect:/login";
                }
            } else {
                /* 일반 로그인시 일치하지않으면 메시지 출력후 login 페이지로 redirect */
                if(!loginCheck(c_email, c_pwd, rattr)){
                    rattr.addFlashAttribute("msg", "고객의 정보가 일치하지 않습니다. 다시 시도해주세요");
                    return "redirect:/login";
                }
            }

            /* 로그인성공시 */
            /* 세션생성 */
            HttpSession session = request.getSession();
            session.setAttribute("c_email", c_email);
            session.setMaxInactiveInterval(30 * 60);

            /* 마지막 로그인일자를 업데이트하는 Dao 호출 */
            custDao.updateLogin(c_email);


            /* 로그인 성공시 이전 로그인이전 URL로 이동 */
            toURL = toURL==null || toURL.equals("") ? "/" : toURL;

        } catch(Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "알수없는 오류가 발생했습니다. 다시 시도해주세요");
        }
        return "redirect:"+toURL;
    }

    /* 로그인체크메서드 - 아이디가 null 인지, pwd가 일치하는지 확인 */
    private boolean loginCheck(String c_email, String c_pwd, RedirectAttributes rattr){
        CustDto cust = null;
        try {
            /* email로 DB 고객 조회 */
            cust = custDao.selectEmail(c_email);

            /* 고객을 조회할수없을경우 */
            if(cust == null){
                rattr.addFlashAttribute("msg", "유효하지않은 고객입니다.");
                return false;
            }

            /* 고객이 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호가 일치한지 확인 */
            boolean isPwdMatch = passwordService.checkPassword(c_pwd, cust.getC_pwd());

            /* 고객이 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호가 일치하지 않을경우 */
            if(!isPwdMatch){
                rattr.addFlashAttribute("msg", "비밀번호가 유효하지않습니다.");

                try {
                    /* 로그인 실패 count */
                    custService.failedLoginCnt(cust);
                } catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            /* 비밀번호가 일치할경우 로그인실패 count 초기화 */
            custService.resetFailedCnt(c_email);

            return true;
        } catch(Exception e){
            e.printStackTrace();
            /* DB에 고객이 없을경우 */
            rattr.addFlashAttribute("msg", "로그인실패");
            return false;
        }
    }


    /* admin 계정 로그인 */
    @Value("#{confidential['admin.password']}, #{confidential['admin2.password']}")
    private String adminPwd;
    private boolean adminLogin(String c_pwd){

        try {
            /* admin 계정을 select */
            CustDto admin = custDao.selectEmail("admin");
            CustDto admin2 = custDao.selectEmail("admin2");

            /* admin의 DB 정보에있는 pwd가 암호회되지 않았다면 암호화하는 로직 */
            if(!admin.getC_pwd().startsWith("$2a$")){
                String rawPwd = adminPwd;
                String hashPwd = passwordService.encodePassword(rawPwd);
                admin.setC_pwd(hashPwd);
                custDao.updateCust(admin);
            }

            /* admin의 DB 정보에있는 pwd가 암호회되지 않았다면 암호화하는 로직 */
            if(!admin2.getC_pwd().startsWith("$2a$")){
                String rawPwd = adminPwd;
                String hashPwd = passwordService.encodePassword(rawPwd);
                admin2.setC_pwd(hashPwd);
                custDao.updateCust(admin2);
            }

            /* 암호화된 pwd가 DB와 일치한지 확인 */
            boolean isAdminMatch = passwordService.checkPassword(c_pwd, admin.getC_pwd());
            return isAdminMatch;

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /* 1년이상 장기 미로그인 휴먼상태 상태코드 "H" 로전환하는 스케줄러 */
    /* cron = 초 분 시 매일 매월 요일, → 매주 월요일 자정에 실행됨 */
    @Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul")
    public void lockActiveUser(){
        try {
            int result = custService.updateNotLoginUserStatusForAll();
            if(result > 0){
                System.out.println("1년이상 로그인하지않은 계정을 휴먼상태로 변경");
            } else {
                System.out.println("휴먼상태로 변경할 계정이 없습니다.");
            }

        } catch (Exception e){
            System.out.println("휴먼계정처리 에러발생 "+e.getMessage());
        }
    }


}