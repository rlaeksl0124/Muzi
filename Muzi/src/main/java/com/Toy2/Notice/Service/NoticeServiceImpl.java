package com.Toy2.Notice.Service;

import com.Toy2.Notice.Dao.NoticeDao;
import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
//
    @Override
    public int updateNotice(NoticeDto notice) {
        Date now = new Date(System.currentTimeMillis());
        notice.setLast_mod_dt(now);
        return noticeDao.updateContents(notice);
    }
    @Override
    public NoticeDto getNotice(int id) throws IllegalAccessException{
        NoticeDto notice = noticeDao.selectById(id);
        if (notice.getN_state() == "N")
            throw new IllegalAccessException("이미 삭제된 게시글 입니다.");
        return noticeDao.selectById(id);
    }
    @Override
    public List<NoticeDto> getNoticePage(PageHandler ph) {
        return noticeDao.selectNoticePage(ph);
    }
    /* 삭제 대신 사용할 상태 변경 */
    @Override
    public int deleteContents(NoticeDto notice) {
        notice.setN_state("N");
        Date now = new Date(System.currentTimeMillis());
        notice.setLast_mod_dt(now);
        return noticeDao.updateState(notice);
    }
}
