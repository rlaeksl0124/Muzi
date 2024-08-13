package com.Toy2.Cust.Dao;

import com.Toy2.Cust.Domain.CustDto;

import java.util.List;

public interface CustDao {
    int count() throws Exception;

    int insert(CustDto custDto) throws Exception;

    CustDto selectEmail(String c_email) throws Exception;

    List<CustDto> getCustAll() throws Exception;

    int updateCust(CustDto custDto) throws Exception;

    int updateLogin(String c_email) throws Exception;

    int deleteCustChangeStatusCode(String c_email) throws Exception;

    int deleteAll() throws Exception;

    int delete(String c_email) throws Exception;
}
