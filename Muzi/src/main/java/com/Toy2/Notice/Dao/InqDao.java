package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.InqDto;

import java.util.List;

public interface InqDao {
    int count();
    int deleteAll();
    int delete(Integer inq_no, String inq_admin);
    int insert(InqDto inqDto);
//    int update(InqDto inqDto);
    InqDto select(Integer inq_no);
    List<InqDto> selectAll();
}
