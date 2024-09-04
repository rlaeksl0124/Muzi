//package com.Toy2.Notice.Controller;
//
//import com.Toy2.Notice.Service.NoticeService;
//import com.Toy2.Notice.domain.NoticeDto;
//import com.Toy2.Notice.entity.PageHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/Notice")
//public class NoticeController {
//    @Autowired
//    private NoticeService noticeService;
//
//    @GetMapping("")
//    public String notice(Integer page, Model model) throws Exception {
//        /* 페이징된 화면을 보여주기 위해 목록을 조회 */
//        page = page==null?1:page;
//        PageHandler ph =new PageHandler(noticeService.count(),page);
//         List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
//        /* 모델에 담아서 페이지로 전달 */
//        model.addAttribute("NoticeArr",dtolist);
//        model.addAttribute("ph",ph);
//        return "/Notice/read";
//    }
//    @ResponseBody
//    @GetMapping("get")
//    public NoticeDto getNotice(Integer no) throws Exception {
//        /* 비동기로 화면에서 버튼을 누르면 화면에 내용을 보내준다. */
//        return noticeService.getNotice(no);
//    }
//    @GetMapping("/Delete")
//    public String delete(Integer no, Integer page, Model model){
//        /* 삭제 버튼을 누르면 해당 번호에 DB데이터를 삭제 */
//        noticeService.deleteNotice(no);
//        page = page==null?1:page;
//        /*삭제 후 읽기 페이지로 돌려보내기(페이지는 유지)*/
//        return "redirect:/Notice?page="+page;
//    }
//    @GetMapping("/Modify")
//    public String modify(Integer no, Integer page, Model model) throws Exception {
//        /* 수정 작업을 할 땐 작성 페이지에 해당 dto를 보내서 채워 주어서 변경을 쉽게 할 수 있도록 한다. */
//        page = page==null?1:page;
//        NoticeDto dto=noticeService.getNotice(no);
//        model.addAttribute("NoticeDto", dto);
//        return "/Notice/write";
//    }
//    @PostMapping("/Modify")
//    public String modify(NoticeDto dto, Integer page, Model model) throws Exception {
//        /*수정 후 해당 읽기 페이지로 돌려보내준다.*/
//        noticeService.updateNotice(dto);
//        return "redirect:/Notice?page="+page;
//    }
//
//    @GetMapping("/Write")
//    public String write() throws Exception {
//        return "/Notice/write";
//    }
//    @PostMapping("/Write")
//    public String write(NoticeDto dto, Model model) throws Exception {
//        /*작성이 성공 했을 때는 최근 페이지로 돌려준다.*/
//        int success=noticeService.addNotice(dto);
//        PageHandler ph =new PageHandler(noticeService.count(),1);
//        List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
//        model.addAttribute("NoticeArr",dtolist);
//        model.addAttribute("ph",ph);
//        /*실패 하는 경우에는 메세지를 보내서 화면에 알려준다.*/
//        if(success==0){
//            model.addAttribute("errorMessage", "입력 실패하였습니다.");
//        }
//        return "/Notice/read";
//    }
//
//    @ExceptionHandler(IllegalAccessException.class)
//    @ResponseBody
//    public String exception(Exception e, Model model) {
//        /* 삭제 작업을 상태로 관리 하기 때문에 삭제 상태인 게시글을 조회 시도 할 때 예외를 발생시켜 관리 하기 위한 exceptionHandler */
//        return e.getMessage();
//    }
//}
