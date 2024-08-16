package com.Toy2.Faq.Service;

import com.Toy2.Faq.Dao.FaqDao;
import com.Toy2.Faq.Domain.FaqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqDao faqDao;          // FaqDao 주입받음

    // 개수 세기
    @Override
    public int countFaq() throws Exception {
        return faqDao.count();
    }

    // 관리자가 모두 삭제 - 필요한지 다시 생각해보기
    @Override
    public int deleteAllFaq() throws Exception {
        return faqDao.deleteAll();
    }

    // 관리자가 삭제
    @Override
    public int deleteFaq(Integer faq_no) throws Exception{
        return faqDao.delete(faq_no);
    }

    // FAQ 등록
    @Override
    public int insertFaq(FaqDto faqDto) throws Exception{
        return faqDao.insert(faqDto);
    }

    // FAQ 전체 조회 - getList
    @Override
    public List<FaqDto> selectAll() throws Exception{
        return faqDao.selectAll();
    }

    // FAQ 하나 조회
    @Override
    public FaqDto selectFaq(Integer faq_no) throws Exception{
        FaqDto faqDto = faqDao.select(faq_no);
        faqDao.increaseViewCnt(faq_no);
        return faqDto;
    }

    // FAQ 수정
    @Override
    public int updateFaq(FaqDto faqDto) throws Exception{
        return faqDao.update(faqDto);
    }
}
