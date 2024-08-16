package com.Toy2.Faq.Dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FaqCateDaoImpl implements FaqCateDao {

    @Autowired
    SqlSession session;     // SqlSession 주입 받기
    String namespace = "toy2.Faq.Dao.FaqCateDao.";      // "." 필수

    @Override
    public List<FaqCateDao> selectAll() {
        return session.selectList(namespace + "selectAllNames");
    }
}
