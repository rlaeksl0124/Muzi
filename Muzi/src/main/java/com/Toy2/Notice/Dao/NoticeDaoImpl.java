package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeDaoImpl implements NoticeDao{
    @Autowired
    SqlSession sqlSession;

    private String namespace = "dao.Notice.NoticeDao.";

    @Override
    public int count(){
        return sqlSession.selectOne(namespace+"count");
    }
    @Override
    public List<NoticeDto> selectNoticePage(PageHandler pagehandler){
        return sqlSession.selectList(namespace+"selectPage", pagehandler);
    }
}
