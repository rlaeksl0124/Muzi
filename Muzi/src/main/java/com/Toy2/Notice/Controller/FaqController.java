package com.Toy2.Notice.Controller;


import com.Toy2.Notice.domain.FaqDto;
import com.Toy2.Notice.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping( "/faq")
public class FaqController {

    @Autowired FaqService faqService;

    // FAQ 개수 세기
//    int countFaq() throws Exception;

    // FAQ 등록 페이지로 이동
    @GetMapping("/register")
    public String insertFAQ() {
        return "faq_register";
    }

    //    int insertFaq(FaqDto faqDto) throws Exception;
    // FAQ 등록 제출
    @PostMapping("/register")
    public String registerFAQ(
            @RequestParam("cate_no") Integer cate_no,
            @RequestParam("faq_order") Integer faq_order,
            @RequestParam("faq_title") String faq_title,
            @RequestParam("faq_content") String faq_content,
            @RequestParam("faq_closing") String faq_closing,
            @RequestParam("faq_writer") String faq_writer, Model model){

        FaqDto faqDto = new FaqDto();
        faqDto.setCate_no(cate_no);
        faqDto.setFaq_title(faq_title);
        faqDto.setFaq_order(faq_order);
        faqDto.setFaq_content(faq_content);
        faqDto.setFaq_closing(faq_closing);
        faqDto.setFaq_writer(faq_writer);

        try {
            if (faqService.insertFaq(faqDto) != 1)
                throw new Exception("FAQ Register failed.");

            model.addAttribute("faqDto", faqDto);
            model.addAttribute("msg", "REG_OK");
            return "redirect:/faq"; // faq_center.jsp로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "REG_ERR");
            return "faq_register";
        }
    }

    @RequestMapping("/view")
    public String viewFaq(@RequestParam("faq_no") Integer faq_no, Model model) throws Exception {
        FaqDto faqDto = faqService.selectFaq(faq_no);
        model.addAttribute("faqDto", faqDto);
        return "faq_view";
    }

    @RequestMapping("")
    public String faqCenter(Model model){
        try {
            List<FaqDto> faqList = faqService.selectAll();
            model.addAttribute("list", faqList);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "LIST_ERR");
        }
        return "faq_center";
    }
}