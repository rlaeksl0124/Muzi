package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class EditInfo {
    @Autowired
    CustDao custDao;


    /* 고객정보 수정 form */
    @GetMapping("/editInfo")
    public String userInfo(Model m, HttpServletRequest request){
        try {
            /* 세션을 가져온다 */
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("c_email");

            /* 이메일을이용해서 고객조회 */
            CustDto custDto = custDao.selectEmail(email);

            m.addAttribute("custDto", custDto);
        } catch (Exception e){

        }
        return "editInfo";
    }


    /* 고객정보 수정 */
    @PostMapping("/editInfo")
    public String userInfo(CustDto custDto, HttpServletRequest request) {
        try {
            /* 세션을 가져온다 */
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("c_email");
            CustDto cust = custDao.selectEmail(email);

            /* 고객정보 수정 */
            cust.setC_name(custDto.getC_name());
            cust.setC_phn(custDto.getC_phn());
            cust.setC_pwd(custDto.getC_pwd());
            cust.setC_birth(custDto.getC_birth());
            cust.setC_zip(custDto.getC_zip());
            cust.setC_road_a(custDto.getC_road_a());
            cust.setC_det_a(custDto.getC_det_a());

            custDao.updateCust(cust);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
