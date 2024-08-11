package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.FaqDto;

import java.util.List;

public interface FaqDao {


    /* 필요한 거 */
    //    exception 제거하기
    int count();
    int deleteAll();
    List<FaqDto> selectAll();

    int delete(Integer faq_no, String faq_writer);
    int insert(FaqDto faqDto);
    int update(FaqDto faqDto);
    FaqDto select(Integer faq_no);
    int increaseViewCnt(Integer faq_no);
}
