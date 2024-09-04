package com.Toy2.Faq.Dao;

import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Domain.SearchCondition;

import java.util.List;

public interface FaqDao {
    //  throws  Exception 제거하기
    int count();
    int deleteAll();
    List<FaqDto> selectAll();

    int delete(Integer faq_no);
    int insert(FaqDto faqDto);
    int update(FaqDto faqDto);
    FaqDto select(Integer faq_no);
    int increaseViewCnt(Integer faq_no);

    String joinCategory(Integer faq_no, Integer cate_no);

    int searchResultCnt(SearchCondition sc);
    List<FaqDto> searchSelected(SearchCondition sc);
}
