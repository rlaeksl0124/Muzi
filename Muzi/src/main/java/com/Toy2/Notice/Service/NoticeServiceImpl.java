//package com.Toy2.Notice.Service;
//
//import com.Toy2.Notice.Dao.NoticeDao;
//import com.Toy2.Notice.domain.NoticeDto;
//import com.Toy2.Notice.entity.PageHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.Date;
//import java.util.List;
//
//@Service
//public class NoticeServiceImpl implements NoticeService {
//    @Autowired
//    private NoticeDao noticeDao;
//
//    @Override
//    public int addNotice(NoticeDto notice) {
//        return noticeDao.insertNotice(notice);
//    }
//    @Override
//    public int count(){
//        return noticeDao.count();
//    }
//
//    @Override
//    public int deleteNotice(int id) {
//        return noticeDao.deleteById(id);
//    }
////
//    @Override
//    public int updateNotice(NoticeDto notice) {
//        /* 업데이트 시 업데이트 시간을 입력해 주고 업데이트를 한다.*/
//        Date now = new Date(System.currentTimeMillis());
//        notice.setLast_mod_dt(now);
//        return noticeDao.updateContents(notice);
//    }
//    @Override
//    public NoticeDto getNotice(int id) throws IllegalAccessException{
//        NoticeDto notice = noticeDao.selectById(id);
//        /* 삭제된 게시글을 상태로 관리 하기 때문에 삭제된 게시글 조회시 예외로 처리한다.*/
//        if (notice.getN_state() == "N")
//            throw new IllegalAccessException("이미 삭제된 게시글 입니다.");
//        return noticeDao.selectById(id);
//    }
//    @Override
//    public List<NoticeDto> getNoticePage(PageHandler ph) {
//        return noticeDao.selectNoticePage(ph);
//    }
//    /* 삭제 대신 사용할 상태 변경 */
//    @Override
//    public int deleteContents(NoticeDto notice) {
//        notice.setN_state("N");
//        /* 업데이트 시간 현재 시간으로 설정 */
//        Date now = new Date(System.currentTimeMillis());
//        notice.setLast_mod_dt(now);
//        return noticeDao.updateState(notice);
//    }
//
//    @Override
//    public int updateN_order(NoticeDto notice){
//        Date now = new Date(System.currentTimeMillis());
//        notice.setLast_mod_dt(now);
//        /* 중복 제거 및 값 관리는 DB 트리거와 제약조건으로 관리 */
//
//        return noticeDao.updateN_order(notice);
//    }
//
//}