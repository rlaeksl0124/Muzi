package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MyPage {

    @Autowired
    CustDao custDao;

    @GetMapping("/mypage")
    public String mypage(Model m, @SessionAttribute String c_email, HttpSession session) {
        try {
            /* 저장된 세션을 가져온다 */
//            String email = (String) session.getAttribute("c_email");

            /* 저장된 세션의 email을 이용해서 고객의 정보를 조회 */
            CustDto custDto = custDao.selectEmail(c_email);

            /* 모델에 저장 */
            m.addAttribute("custDto", custDto);
        } catch (Exception e){
            e.printStackTrace();
            /* email 의 고객정보를 불러오지 못할때 처리 */
        }

        return "mypage";
    }



}
