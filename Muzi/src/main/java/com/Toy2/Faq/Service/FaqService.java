package com.Toy2.Faq.Service;

import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Domain.SearchCondition;

import java.util.List;

public interface FaqService {

    /* 서비스 단에서 예외 던지기 */

    // FAQ 개수 세기
    int count() throws Exception;

    // FAQ 전체 삭제
//    int deleteAll() throws Exception;

    // FAQ 삭제
    int delete(Integer faq_no) throws Exception;

    // FAQ 등록
    int insert(FaqDto faqDto) throws Exception;

    // 관리자가 전체 FAQ 조회
    List<FaqDto> selectAll() throws Exception;

    // 선택한 FAQ 조회
    FaqDto select(Integer faq_no) throws Exception;

    // FAQ 수정
    int update(FaqDto faqDto) throws Exception;

    String joinCategory(Integer faq_no ,Integer cate_no) throws Exception;

    int getSearchResultCnt(String option, String keyword) throws Exception;
    List<FaqDto> getSearchResult(String option, String keyword) throws Exception;
}