package com.Toy2.Notice.Controller;

import com.Toy2.Notice.Service.NoticeService;
import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("")
    public String notice(Integer page, Model model) throws Exception {
        page = page==null?1:page;
        PageHandler ph =new PageHandler(noticeService.count(),page);
         List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
        model.addAttribute("NoticeArr",dtolist);
        model.addAttribute("ph",ph);
        return "/Notice/read";
    }
    @ResponseBody
    @GetMapping("get")
    public NoticeDto getNotice(Integer no) throws Exception {
        return noticeService.getNotice(no);
    }
    @GetMapping("/Delete")
    public String delete(Integer no, Integer page, Model model){
        noticeService.deleteNotice(no);
        page = page==null?1:page;
        PageHandler ph =new PageHandler(noticeService.count(),page);
        List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
        model.addAttribute("NoticeArr",dtolist);
        model.addAttribute("ph",ph);
        return "/Notice/read";
    }
    @GetMapping("/Modify")
    public String modify(Integer no, Integer page, Model model) throws Exception {
        page = page==null?1:page;
        NoticeDto dto=noticeService.getNotice(no);
        model.addAttribute("NoticeDto", dto);
        return "/Notice/write";
    }
    @PostMapping("/Modify")
    public String modify(NoticeDto dto, Integer page, Model model) throws Exception {
        noticeService.updateNotice(dto);
        System.out.println(dto);
        System.out.println(page);
        PageHandler ph =new PageHandler(noticeService.count(),page);
        List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
        model.addAttribute("NoticeArr",dtolist);
        return "/Notice/read";
    }

    @GetMapping("/Write")
    public String write() throws Exception {
        return "/Notice/write";
    }
    @PostMapping("/Write")
    public String write(NoticeDto dto, Model model) throws Exception {
        int success=noticeService.addNotice(dto);
        PageHandler ph =new PageHandler(noticeService.count(),1);
        List<NoticeDto> dtolist = noticeService.getNoticePage(ph);
        model.addAttribute("NoticeArr",dtolist);
        model.addAttribute("ph",ph);
        if(success==0){
            model.addAttribute("errorMessage", "입력 실패하였습니다.");
        }
        return "/Notice/read";
    }

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseBody
    public String exception(Exception e, Model model) {
        return e.getMessage();
    }
}
