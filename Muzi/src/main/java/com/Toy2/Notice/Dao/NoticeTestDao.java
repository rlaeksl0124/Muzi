package com.Toy2.Notice.Dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeTestDao{

    @Autowired
    SqlSession sqlSession;

    private String namespace = "dao.Notice.NoticeDao.";

    public void deleteAll(){
        sqlSession.delete(namespace+"deleteAll");
    }
    public void autoIncreasereset(){
        sqlSession.update(namespace+"autoIncreasereset");
    }

}
