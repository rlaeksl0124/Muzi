package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;

import java.util.List;

public interface NoticeDao {
    public int count();
    public List<NoticeDto> selectNoticePage(PageHandler pagehandler);
    public int insertNotice(NoticeDto notice);
    public NoticeDto selectById(int notice_no);
    public int updateContents(NoticeDto notice);
    public int deleteById(int notice_no);
    public int updateState(NoticeDto notice);
    public int updateN_order(NoticeDto notice);
}
