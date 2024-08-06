package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;

import java.util.List;

public interface NoticeDao {
    public int count();
    public List<NoticeDto> selectNoticePage(PageHandler pagehandler);
}
