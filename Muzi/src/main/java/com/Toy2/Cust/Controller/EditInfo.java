package com.Toy2.Cust.Controller;

import com.Toy2.Cust.Dao.CustDao;
import com.Toy2.Cust.Domain.CustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class EditInfo {
    @Autowired
    CustDao custDao;


    /* 고객정보 수정 form */
    @GetMapping("/editInfo")
    public String userInfo(Model m, @SessionAttribute String c_email){
        try {
            /* 이메일을이용해서 고객조회 */
            CustDto custDto = custDao.selectEmail(c_email);

            m.addAttribute("custDto", custDto);
        } catch (Exception e){

        }
        return "Cust/editInfo";
    }


    /* 고객정보 수정 */
    @PostMapping("/editInfo")
    public String userInfo(CustDto custDto, @SessionAttribute String c_email) {
        try {
            /* 세션을 가져온다 */
            CustDto cust = custDao.selectEmail(c_email);

            if(cust==null){
                return "";
            }

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

//    @PostMapping("/editInfo/")

//    @PostMapping("/phnValid")
//    public ResponseEntity<String> phoneValid(@Valid @RequestBody CustDto custDto, BindingResult result){
//        try {
//            if(result.hasErrors()){
//                System.out.println(result);
//                return new ResponseEntity<>("11자리의 숫자만 입력가능합니다."+
//                        result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
//
//            }
//
//        } catch(Exception e) {
//            e.printStackTrace();
//
//        }
//    }
}
