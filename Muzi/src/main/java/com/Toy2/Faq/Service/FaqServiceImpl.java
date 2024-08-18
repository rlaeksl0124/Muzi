package com.Toy2.Faq.Service;

import com.Toy2.Faq.Dao.FaqDao;
import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqDao faqDao;          // FaqDao 주입받음

    // 개수 세기
    @Override
    public int count() throws Exception {
        return faqDao.count();
    }


    // 관리자가 삭제
    @Override
    public int delete(Integer faq_no) throws Exception{
        return faqDao.delete(faq_no);
    }

    // FAQ 등록
    @Override
    public int insert(FaqDto faqDto) throws Exception{
        return faqDao.insert(faqDto);
    }

    // FAQ 전체 조회 - getList
    @Override
    public List<FaqDto> selectAll() throws Exception{
        return faqDao.selectAll();
    }

    // FAQ 하나 조회
    @Override
    public FaqDto select(Integer faq_no) throws Exception{
        FaqDto faqDto = faqDao.select(faq_no);
        faqDao.increaseViewCnt(faq_no);
        return faqDto;
    }

    // FAQ 수정
    @Override
    public int update(FaqDto faqDto) throws Exception{
        return faqDao.update(faqDto);
    }

    @Override
    public String joinCategory(Integer faq_no, Integer cate_no) throws Exception {
        return faqDao.joinCategory(faq_no, cate_no);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return faqDao.searchResultCnt(sc);
    }

    @Override
    public List<FaqDto> getSearchResult(SearchCondition sc) throws Exception {
        return faqDao.searchSelected(sc);
    }
}
