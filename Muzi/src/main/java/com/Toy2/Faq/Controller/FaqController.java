package com.Toy2.Faq.Controller;

import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Domain.SearchCondition;
import com.Toy2.Faq.Service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// Service에 Transaction 넣으면 돼
@Controller
@RequestMapping( "/faq")
public class FaqController {

    @Autowired FaqService faqService;       // FaqService 주입 받기

    /* readList - 관계자가 보는 목록 페이지 faq_list  보여줌 */
    @RequestMapping("")
    public String readList(Model model, HttpSession session, RedirectAttributes rd, SearchCondition sc) {
        if (session.getAttribute("c_email") == null || !(session.getAttribute("c_email").equals("admin"))){
            rd.addFlashAttribute("msg", "No_Grant_ERR");
            return "redirect:/login";
        }

        try {
            List<FaqDto> faqList = faqService.selectAll();      // 서비스에서 모든 FAQ 게시글을 List로 가져옴

            for (FaqDto faq : faqList) {        // 가져온 FaqDto 리스트를 순회
                faq.setCategoryName(faqService.joinCategory(faq.getFaq_no(), faq.getCate_no()));        // Join 해서 카테고리 이름  갖고오기
            }
            model.addAttribute("list", faqList);        // model에 FAQ 목록을 추가하여 view에서 사용할 수 있도록 함
            return "faq_list";              // faq_list 조회 페이지로 이동

        } catch (Exception e) {              // 게시글 목록 조회를 요청했는데 예외가 발생하면
            e.printStackTrace();

            // 사용자에게 어떤 행동을 할지 - 게시글 목록이 없다고 뜨기 / 에러 페이지
            rd.addFlashAttribute("msg", "ReadList_ERR");      // 에러 메시지를 RedirectAttributes에 추가
            return "redirect:/faq" + sc.getQueryString();
        }
    }


    /* readOne - 선택한 FAQ 게시글 하나 조회 */
    @GetMapping("/read")
    public String readOne(@RequestParam("faq_no") Integer faq_no, HttpSession session, Model model, RedirectAttributes rd) throws Exception {       // 매개변수로 조회할 faq_no 넘김
        if (session.getAttribute("c_email") == null || !(session.getAttribute("c_email").equals("admin"))){
            rd.addFlashAttribute("msg", "No_Grant_ERR");
            return "redirect:/login";
        }

        try {
            FaqDto faqDto = faqService.select(faq_no);           // 매개변수로 받은 faq_no를 가진 게시글을 faqDto로 저장

            if (faqDto == null) {               // faqDto가 null이면 해당하는 게시글이 없다는 것
                rd.addFlashAttribute("msg", "ReadOne_ERR");
                return "redirect:/faq";         // faq_list 목록 페이지로 돌아가기
            }

            faqDto.setCategoryName(faqService.joinCategory(faqDto.getFaq_no(), faqDto.getCate_no()));           // JOIN으로 카테고리 이름 가져오기

            model.addAttribute("faqDto", faqDto);           // view에 넘기기 위해 faqDto를 model에 추가
            return "faq_one";                              // faq_one.jsp 선택한 게시글 보여주는 페이지로 이동

        } catch (Exception e){
            e.printStackTrace();
            rd.addFlashAttribute("msg", "READONE_ERR");
            return "redirect:/faq";
        }
        // 예외처리 빠짐 -> try-catch
        // 예외 공통처리는 ExceptionHandler로 뽑으면 돼 -> ExceptionHandler에 공통인게 있으면 Advice
    }


    /* write - faq_list 페이지이에서 등록 버튼 */
    // 글 등록하다 에러 발생하면 - 화면 뿌리고 작성하던 데이터는 저장되어야 됨 -> ?
    @GetMapping("/write")
    public String write(RedirectAttributes rd) {
        try {
            return "faq_write";
        } catch (Exception e){
            e.printStackTrace();
            rd.addFlashAttribute("msg", "Write_ERR");
            return "redirect:/faq";
        }
    }


    /* write - FAQ 등록 페이지에서 내용 입력 후 등록 */
    @PostMapping("/write")
    public String write(
            @RequestParam("cate_no") Integer cate_no, @RequestParam("faq_order") Integer faq_order, @RequestParam("faq_title") String faq_title,
            @RequestParam("faq_content") String faq_content, @RequestParam("faq_writer") String faq_writer, Model model, RedirectAttributes rd) {       // faq_reg_date는 사용자가 입력하는게 아니라 FAQ 등록 시점을 저장하는 거

        try {
        FaqDto faqDto = new FaqDto();           // 새로운 FaqDto 객체 생성
        faqDto.setCate_no(cate_no);             // 매개변수로 넘어온 값을 Setter를 이용해서 객체에 저장
        faqDto.setFaq_title(faq_title);
        faqDto.setFaq_order(faq_order);
        faqDto.setFaq_content(faq_content);
        faqDto.setFaq_writer(faq_writer);

        if (faqService.insert(faqDto) != 1)          // != 1이면 insert 안 됐다는 뜻
            throw new Exception("FAQ registration failed.");
        return "redirect:/faq";                    // /faq로 등록해놓은 faq_center.jsp로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            rd.addFlashAttribute("msg", "Submit_Write_ERR");
            return "redirect:/faq";                     // 예외가 발생하면 다시 FAQ 등록 폼으로 이동
        }
    }


    /* delete - 게시글 조회한 페이지 faq_one.jsp에서 삭제 작업 -> alert창으로 삭제 완료 알림  */
    // 관리자만 FAQ 게시글 삭제 가능
    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer faq_no, HttpServletResponse response) throws IOException {
        try {
            if (faq_no == null || faq_no <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("유효하지 않은 FAQ 번호");
            }
            if (faqService.delete(faq_no) != 1) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("삭제 실패했습니다.");
            }
//            response.setContentType("text/plain;charset=UTF-8");      // 설정된 문자 인코딩과 Content-Type을 사용하여 응답 작성
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("FAQ 삭제 에러");
        }
    }



    /* modify - 등록된 FAQ 수정 (작성자 & 관리자 수정 가능) */
    // faq_one.jsp 페이지에서 수정 버튼 클릭 -> faq_modify 페이지에서 기존 데이터를 가지고 수정
    @PostMapping("/modify")         // PostMapping 사용
    public String modify(FaqDto faqDto, HttpSession session, SearchCondition sc, RedirectAttributes rd) {
        String c_email = (String) session.getAttribute("c_email");

        try {
            if (c_email.equals("admin") || c_email.equals(faqDto.getFaq_writer())){
                faqDto.setCategoryName(faqService.joinCategory(faqDto.getFaq_no(), faqDto.getCate_no()));
                faqService.update(faqDto);
                return "redirect:/faq" + sc.getQueryString(faqDto.getFaq_no());
            }
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            rd.addFlashAttribute("msg", "Modify_ERR");
            return "redirect:/login";
        }
    }


    //  faq_modify.jsp 페이지에 faqDto를 전달하는 메서드
    @GetMapping("/modify")
    public String modify(@RequestParam("faq_no") int faq_no, HttpSession session, Model model, RedirectAttributes rd) throws Exception {
        String c_email = (String) session.getAttribute("c_email");

        if (c_email == null || !c_email.equals("admin"))
            return "redirect:/login";

        try{
            FaqDto faqDto = faqService.select(faq_no);           // 선택한 FAQ 게시글(일치하는 faq_no) 조회 후 저장
            model.addAttribute("faqDto", faqDto);       // Model에 가져온 faqDto 저장
            return "faq_modify";        // faq_modify 페이지로 이동
        } catch (Exception e){
            e.printStackTrace();
            rd.addFlashAttribute("msg", "Modify_ERR");
            return "redirect:/faq";
        }
    }


    @GetMapping("/show")
    public String show(@RequestParam(value = "option", required = false) String option,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       Model model) {
        try {
            List<FaqDto> faqList;

            if (keyword != null && !keyword.trim().isEmpty()) {
                faqList = faqService.getSearchResult(option, keyword);
            } else {
                faqList = faqService.selectAll();  // Retrieve all FAQs if no search keyword is provided
            }

            for (FaqDto faq : faqList) {
                faq.setCategoryName(faqService.joinCategory(faq.getFaq_no(), faq.getCate_no()));
            }

            model.addAttribute("faqList", faqList);
            return "faq_cust_list";                 // faq_cust_list.jsp 화면
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "faq_cust_list ERROR");
            return "redirect:/home";
        }
    }



    @GetMapping("")
    public String listFaqs(@RequestParam(value = "option", required = false) String option,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           Model model) throws Exception {

        List<FaqDto> faqList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            faqList = faqService.getSearchResult(option, keyword);
        } else {
            faqList = faqService.selectAll();
        }

        model.addAttribute("list", faqList);
        return "faq_list";
    }
}
