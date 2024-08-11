package com.Toy2.Notice.Controller;


import com.Toy2.Notice.domain.FaqDto;
import com.Toy2.Notice.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/faq")
public class FaqController {

    @Autowired FaqService faqService;

    // FAQ 개수 세기
//    int countFaq() throws Exception;


    // FAQ 전체 삭제
//    int deleteAllFaq() throws Exception;
//    @GetMapping("/removeAll")
//    public String remove(Integer faq_no, HttpSession session){
//    }
//
//    // FAQ 삭제
//    int deleteFaq(Integer faq_no, String faq_admin) throws Exception;
//
//    // FAQ 등록
//    int insertFaq(FaqDto faqDto) throws Exception;
//
//    // 관리자가 전체 FAQ 조회
//    List<FaqDto> selectAll() throws Exception;
//
//    // 선택한 FAQ 조회
//    FaqDto selectFaq(Integer faq_no) throws Exception;
//
//    // FAQ 수정
//    int updateFaq(FaqDto faqDto) throws Exception;
}