package com.Toy2.Notice.Controller;


import com.Toy2.Notice.domain.FaqDto;
import com.Toy2.Notice.entity.FaqCategory;
import com.Toy2.Notice.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping( "/faq")
public class FaqController {

    @Autowired FaqService faqService;       // FaqService 주입 받기

    // FAQ 개수 세기
    //    int countFaq() throws Exception;


    // FAQ 등록 페이지로 이동
    @GetMapping("/register")
    public String insertFAQ() {

        return "faq_register";
    }


    // FAQ 등록 페이지에서 진짜 등록
    @PostMapping("/register")
    public String registerFAQ(
            @RequestParam("cate_no") Integer cate_no,
            @RequestParam("faq_order") Integer faq_order,
            @RequestParam("faq_title") String faq_title,
            @RequestParam("faq_content") String faq_content,
            @RequestParam("faq_closing") String faq_closing,
            @RequestParam("faq_writer") String faq_writer,
            // faq_reg_date는 사용자가 입력하는게 아니라 FAQ 등록 시점을 저장하는 거
            Model model){

        FaqDto faqDto = new FaqDto();           // 새로운 FaqDto 객체 생성
        faqDto.setCate_no(cate_no);             // 매개변수로 넘어온 값을 Setter를 이용해서 객체에 저장
        faqDto.setFaq_title(faq_title);
        faqDto.setFaq_order(faq_order);
        faqDto.setFaq_content(faq_content);
        faqDto.setFaq_closing(faq_closing);
        faqDto.setFaq_writer(faq_writer);

        try {
            if (faqService.insertFaq(faqDto) != 1)          // != 1이면 insert 안됐다는 뜻
                throw new Exception("FAQ registration failed.");

            model.addAttribute("faqDto", faqDto);           // == 1이면 insert 된 거니까
//            model.addAttribute("msg", "REG_OK");
            return "redirect:/faq";                    // faq로 등록해놓은 faq_center.jsp로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "REG_ERR");
//            return "faq_register";                  // 예외가 발생하면 다시 FAQ 등록 폼으로 이동
            return "redirect:/register";
        }
    }

    @RequestMapping("/view")
    public String viewFaq(@RequestParam("faq_no") Integer faq_no, Model model) throws Exception {
        FaqDto faqDto = faqService.selectFaq(faq_no);
        if (faqDto == null) {
            throw new Exception("FAQ not found");
        }

        // cate_no를 카테고리의 이름으로 표시하기 위한 변환
        String categoryName = FaqCategory.getCategoryName(faqDto.getCate_no());         // FaqCategory에서 번호에 해당하는 카테고리 이름을 저장
        faqDto.setCategoryName(categoryName);       // 저장한 카테고리 이름을 faqDto의 카테고리로 설정


        model.addAttribute("faqDto", faqDto);
        return "faq_view";
    }


    @RequestMapping("")
    public String faqCenter(Model model){
        try {
            List<FaqDto> faqList = faqService.selectAll();

            for (FaqDto faq : faqList) {
                String categoryName = FaqCategory.getCategoryName(faq.getCate_no());
                faq.setCategoryName(categoryName);
            }

            model.addAttribute("list", faqList);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "LIST_ERR");
        }
        return "faq_center";
    }
}