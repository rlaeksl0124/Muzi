package com.Toy2.Faq.Controller;

import com.Toy2.Faq.Dao.FaqCateDao;
import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Entity.FaqCategory;
import com.Toy2.Faq.Service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Service에 Transaction 넣으면 돼
@Controller
@RequestMapping( "/faq")
public class FaqController {

    @Autowired FaqService faqService;       // FaqService 주입 받기
//    @Autowired FaqCateDao faqCateDao;


    /* READ - 관계자가 보는 목록 페이지 faq_list  보여줌 */
    @RequestMapping("")
    public String faqList(Model model) {
        try {
            List<FaqDto> faqList = faqService.selectAll();      // 서비스에서 모든 FAQ 게시글을 List로 가져옴
            for (FaqDto faq : faqList) {        // 가져온 FaqDto의 리스트를 순회
                // Join 해서 카테고리 이름  갖고오기
                faq.setCategoryName(faqService.joinCategory(faq.getFaq_no(), faq.getCate_no()));
            }
            model.addAttribute("list", faqList);        // model에 FAQ 목록을 추가하여 view에서 사용할 수 있도록 함

        } catch (Exception e) {              // 게시글 목록 조회를 요청했는데 예외가 발생하면
            e.printStackTrace();

            // 사용자에게 어떤 행동을 할지 - 게시글 목록이 없다고 뜨기 / 에러 페이지
            model.addAttribute("msg", "LIST_ERR");      // 에러 메시지를 model에 추가
        }
        return "faq_list";
    }


    /* READ - 선택한 FAQ 게시글 조회 */
    //    @RequestMapping("/view")
    @GetMapping("/view")
    public String viewFaq(@RequestParam("faq_no") Integer faq_no, Model model) throws Exception {       // 매개변수로 조회할 faq_no 넘김
        try {
            FaqDto faqDto = faqService.select(faq_no);           // 매개변수로 받은 faq_no를 가진 게시글을 faqDto로 저장
            if (faqDto == null) {               // faqDto가 null이면 해당하는 게시글이 없다는 것
                // 목록 페이지로 돌아가기
                throw new Exception("FAQ not found");
            }

            // cate_no를 카테고리의 이름으로 표시하기 위한 변환
            String categoryName = FaqCategory.getCategoryName(faqDto.getCate_no());         // FaqCategory에서 번호에 해당하는 카테고리 이름을 저장
            faqDto.setCategoryName(categoryName);           // 저장한 카테고리 이름을 faqDto의 카테고리로 설정

            model.addAttribute("faqDto", faqDto);           // view에 넘기기 위해 faqDto를 model에 추가
            return "faq_view";          // faq_view.jsp 선택한 게시글 보여주는 페이지로 이동
        } catch (Exception e){
            e.printStackTrace();
            return "faq";
        }
        // 예외처리 빠짐 -> try-catch
        // 예외 공통처리는 ExceptionHandler로 뽑으면 돼 -> ExceptionHandler에 공통인게 있으면 Advice
    }


    /* CREATE - FAQ 등록 페이지로 이동 */
    // 글 등록하다 에러 발생하면 - 화면 뿌리고 작성하던 데이터는 저장되어야 됨
    @GetMapping("/register")
    public String insertFAQ() {
        return "faq_register";
    }


    /* CREATE - FAQ 등록 페이지에서 내용 입력 후 등록 */
    @PostMapping("/register")
    public String registerFAQ(
            @RequestParam("cate_no") Integer cate_no, @RequestParam("faq_order") Integer faq_order, @RequestParam("faq_title") String faq_title,
            @RequestParam("faq_content") String faq_content, @RequestParam("faq_writer") String faq_writer, Model model) {       // faq_reg_date는 사용자가 입력하는게 아니라 FAQ 등록 시점을 저장하는 거

        FaqDto faqDto = new FaqDto();           // 새로운 FaqDto 객체 생성
        faqDto.setCate_no(cate_no);             // 매개변수로 넘어온 값을 Setter를 이용해서 객체에 저장
        faqDto.setFaq_title(faq_title);
        faqDto.setFaq_order(faq_order);
        faqDto.setFaq_content(faq_content);
        faqDto.setFaq_writer(faq_writer);

        try {
            if (faqService.insert(faqDto) != 1)          // != 1이면 insert 안 됐다는 뜻
                throw new Exception("FAQ registration failed.");

//    주석 처리해도 동일할듯
            model.addAttribute("faqDto", faqDto);           // == 1이면 insert 된 거니까
//            model.addAttribute("msg", "REG_OK");
            return "redirect:/faq";                    // /faq로 등록해놓은 faq_center.jsp로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
//            model.addAttribute("msg", "REG_ERR");
            return "faq_register";          // 예외가 발생하면 다시 FAQ 등록 폼으로 이동
        }
    }


    /* delete - 게시글 조회한 페이지 faq_view.jsp에서 삭제 작업 */
    //     관리자만 FAQ 게시글 삭제 가능
    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer faq_no, HttpServletResponse response) throws IOException {
        try {
            if (faq_no == null || faq_no <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("유효하지 않은 FAQ 번호");
                return;
            }
            if (faqService.delete(faq_no) != 1) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("삭제 실패했습니다.");
                return;
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


    /* update - 등록된 FAQ 수정 (작성자 & 관리자 수정 가능) */
    // faq_view.jsp 페이지에서 수정 버튼 클릭 -> faq_modify 페이지에서 기존 데이터를 가지고 수정
    @PostMapping("/modify")         // PostMapping 사용
    public String updateFaq(FaqDto faqDto) throws Exception {
        faqService.update(faqDto);           // faqDto 객체 보내서 업데이트
        return "redirect:/faq";             // /faq로 리다이렉트
    }


    //  faq_modify.jsp 페이지에 faqDto를 전달하는 메서드
    @GetMapping("/modify")
    public String updateFaq(@RequestParam("faq_no") int faq_no, Model model) throws Exception {
        FaqDto faqDto = faqService.select(faq_no);           // 선택한 FAQ 게시글(일치하는 faq_no) 조회 후 저장
        model.addAttribute("faqDto", faqDto);       // Model에 가져온 faqDto 저장
        return "faq_modify";        // faq_modify 페이지로 이동
    }


    // showFaq - 클라이언트에게 보여주는 펼쳐지는 FAQ 페이지 맵핑
    // 로그인은 FaqController에서 고려하지 않음
    @GetMapping("/showFaq")
    public String showFaq(Model model) {
        try {
            List<FaqDto> faqList = faqService.selectAll();          // 저장된 FaqDto를 전부 조회해서 List에 저장

            for (FaqDto faq : faqList) {        // 가져온 FaqDto의 리스트를 순회
                // Join 해서 카테고리 이름  갖고오기
                faq.setCategoryName(faqService.joinCategory(faq.getFaq_no(), faq.getCate_no()));
            }
            model.addAttribute("faqList", faqList);          // model에 FAQ 목록을 추가하여 view에서 사용할 수 있도록 함
            return "faq_cust_list";             // 조회 성공하면 faq_cust_list FAQ 보여주는 페이지로 이동
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "faq_cust_list ERROR");
            return "redirect:/home";
        }
    }
}
