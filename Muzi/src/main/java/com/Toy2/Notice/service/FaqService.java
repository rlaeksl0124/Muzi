package com.Toy2.Notice.service;

import com.Toy2.Notice.domain.FaqDto;

import java.util.List;

public interface FaqService {

    /* 서비스 단에서 예외 던지기 */

    // FAQ 개수 세기
    int countFaq() throws Exception;

    // FAQ 전체 삭제
    int deleteAllFaq() throws Exception;

    // FAQ 삭제
    int deleteFaq(Integer faq_no) throws Exception;

    // FAQ 등록
    int insertFaq(FaqDto faqDto) throws Exception;

    // 관리자가 전체 FAQ 조회
    List<FaqDto> selectAll() throws Exception;

    // 선택한 FAQ 조회
    FaqDto selectFaq(Integer faq_no) throws Exception;

    // FAQ 수정
    int updateFaq(FaqDto faqDto) throws Exception;

}