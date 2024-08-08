package com.Toy2.Notice.Service;

import com.Toy2.Notice.domain.NoticeDto;

import java.util.List;

public interface NoticeService {
    int count();
    int addNotice(NoticeDto notice);

    int deleteNotice(int id);

    int updateNotice(NoticeDto notice);

    NoticeDto getNotice(int id) throws Exception;

    List<NoticeDto> getNoticePage(int page);

    /* 삭제 대신 사용할 상태 변경 */
    int deleteContents(NoticeDto notice);
}
