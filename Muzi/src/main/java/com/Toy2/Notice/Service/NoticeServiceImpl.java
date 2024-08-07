package com.Toy2.Notice.Service;

import com.Toy2.Notice.Dao.NoticeDao;
import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public int addNotice(NoticeDto notice) {
        return noticeDao.insertNotice(notice);
    }
    @Override
    public int count(){
        return noticeDao.count();
    }

    @Override
    public int deleteNotice(int id) {
        return noticeDao.deleteById(id);
    }
    @Override
    public int updateNotice(NoticeDto notice) {
        return noticeDao.updateContents(notice);
    }
    @Override
    public NoticeDto getNotice(int id) {
        return noticeDao.selectNoticeById(id);
    }
    @Override
    public List<NoticeDto> getNoticePage(int page) {
        PageHandler pagehandler= new PageHandler(noticeDao.count(),page);
        return noticeDao.selectNoticePage(pagehandler);
    }
    /* 삭제 대신 사용할 상태 변경 */
    @Override
    public int deleteContents(NoticeDto notice) {
        notice.setN_state("N");
        return noticeDao.updateState(notice);
    }
}
