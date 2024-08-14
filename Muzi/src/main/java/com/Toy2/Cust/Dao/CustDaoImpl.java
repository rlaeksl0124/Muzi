package com.Toy2.Cust.Dao;

import com.Toy2.Cust.Domain.CustDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustDaoImpl implements CustDao {
    @Autowired
    private SqlSession session;

    private static String namespace = "com.Toy2.Cust.Dao.CustDao.";

    /* 고객수 조회 */
    @Override
    public int count() throws Exception {
        return session.selectOne(namespace + "count");
    }

    /* 회원가입 */
    @Override
    public int insert(CustDto custDto) throws Exception {
        return session.insert(namespace + "custInsert", custDto);
    }

    /* 이메일 조회 */
    @Override
    public CustDto selectEmail(String c_email) throws Exception {
        return session.selectOne(namespace + "selectEmail", c_email);
    }

    /* 모든고객조회 */
    @Override
    public List<CustDto> getCustAll() throws Exception {
        return session.selectList(namespace + "getCustAll");
    }

    /* 고객정보 변경 */
    @Override
    public int updateCust(CustDto custDto) throws Exception {
        return session.update(namespace + "updateCust", custDto);
    }

    /* 마지막 로그인 기록 */
    @Override
    public int updateLogin(String c_email) throws Exception {
        return session.update(namespace + "updateLogin", c_email);
    }

    /* 삭제: 고객이 계정을 삭제할때 고객 상태코드만 변경 */
    @Override
    public int deleteCustChangeStatusCode(String c_email) throws Exception{
        return session.delete(namespace+"deleteCustChangeStatusCode", c_email);
    }

    /* 모든고객 삭제 */
    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }

    /* 선택고객 삭제 */
    @Override
    public int delete(String c_email) throws Exception {
        return session.delete(namespace + "selectCustDelete", c_email);
    }
}
