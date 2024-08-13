package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    CustDao custDao;

    @GetMapping("/add")
    public String signup(){
        return "registerForm";
    }

    @PostMapping("/add")
    public String signup(@ModelAttribute @Valid CustDto custDto,
                         BindingResult result, Model m, HttpServletRequest request){

        try {
            if(result.hasErrors()){
                System.out.println(result);
                return "registerForm";
            }

            HttpSession session = request.getSession();
            /* 이메일 인증번호 전송된것과 고객이 입력한것과 비교해서 일치하면 회원가입 */

            System.out.println("회원정보: " + custDto);

            custDao.insert(custDto);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }


}
