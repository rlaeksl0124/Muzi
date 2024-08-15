package com.Toy2.Notice.Service;

import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;

import java.util.List;

public interface NoticeService {
    int count();
    int addNotice(NoticeDto notice);

    int deleteNotice(int id);

    int updateNotice(NoticeDto notice);

    NoticeDto getNotice(int id) throws Exception;

    List<NoticeDto> getNoticePage(PageHandler ph);

    /* 삭제 대신 사용할 상태 변경 */
    int deleteContents(NoticeDto notice);

    /* 상단에 올릴 순서 변경 서비스 */
    int updateN_order(NoticeDto notice);
}
