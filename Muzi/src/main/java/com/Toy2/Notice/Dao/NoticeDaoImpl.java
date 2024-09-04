//package com.Toy2.Notice.Dao;
//
//import com.Toy2.Notice.domain.NoticeDto;
//import com.Toy2.Notice.entity.PageHandler;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class NoticeDaoImpl implements NoticeDao{
//    @Autowired
//    SqlSession sqlSession;
//
//    private String namespace = "dao.Notice.NoticeDao.";
//
//    @Override
//    public int count(){
//        return sqlSession.selectOne(namespace+"count");
//    }
//    @Override
//    public List<NoticeDto> selectNoticePage(PageHandler pagehandler){
//        return sqlSession.selectList(namespace+"selectPage", pagehandler);
//    }
//    @Override
//    public int insertNotice(NoticeDto noticeDto){
//        return sqlSession.insert(namespace+"insertNotice", noticeDto);
//    }
//    @Override
//    public NoticeDto selectById(int notice_no){
//        return sqlSession.selectOne(namespace+"selectNoticeById", notice_no);
//    }
//    @Override
//    public int updateContents(NoticeDto noticeDto){
//       return sqlSession.update(namespace+"updateContents", noticeDto);
//    }
//
//    @Override
//    public int deleteById(int notice_no) {
//        return sqlSession.delete(namespace+"deleteById", notice_no);
//    }
//    @Override
//    public int updateState(NoticeDto noticeDto){
//        return sqlSession.update(namespace+"updateState", noticeDto);
//    }
//    @Override
//    public int updateN_order(NoticeDto notice){
//        return sqlSession.update(namespace+"updateN_order", notice);
//    }
//}