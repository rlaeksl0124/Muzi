package com.Toy2.Notice.Controller;

import com.Toy2.Notice.Service.NoticeService;
import com.Toy2.Notice.domain.NoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/NoticeList")
    public String notice(Integer page, Model model) throws Exception {
        page = page==null?1:page;
        model.addAttribute(noticeService.getNotice(page));
        return "NoticeList";
    }
    @ResponseBody
    @GetMapping("/notice")
    public NoticeDto getNotice(Integer no) throws Exception {

        return noticeService.getNotice(no);
    }


    @ExceptionHandler(IllegalAccessException.class)
    public String exception(Exception e, Model model) {
        model.addAttribute("msg", e.getMessage());
        return "error";
    }
}
