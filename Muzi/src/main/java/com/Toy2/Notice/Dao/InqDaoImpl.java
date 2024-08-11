package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.FaqDto;
import com.Toy2.Notice.domain.InqDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InqDaoImpl implements InqDao{

    @Autowired
    SqlSession session;         /* SqlSession 주입 받음 */
    String namespace = "com.Toy2.Notice.Dao.InqMapper.";    /* namespace 설정 - "." 필수 */

    @Override
    public int count() {
        return session.selectOne(namespace + "count");
    }

    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

    @Override
    public int delete(Integer inq_no, String inq_admin) {
        Map map = new HashMap();
        map.put("inq_no", inq_no);
        map.put("inq_admin", inq_admin);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public InqDto select(Integer inq_no) {
        return session.selectOne(namespace + "select", inq_no);
    }

    @Override
    public List<InqDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public int insert(InqDto inqDto) {
        return session.insert(namespace + "insert", inqDto);
    }

}
