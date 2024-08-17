package com.Toy2.Faq.Dao;

import com.Toy2.Faq.Domain.FaqDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/* Bean 등록해야 함 */
@Repository
public class FaqDaoImpl implements FaqDao{

    @Autowired
    SqlSession session;     /* SqlSession 주입 받기 */
//    String namespace = "com.Toy2.Notice.Dao.FaqMapper.";        /* namespace 저장 */
    String namespace= "com.Toy2.Faq.Dao.FaqDao.";

    /* count - 행 개수 셈 */
    @Override
    public int count() {
        return session.selectOne(namespace + "count");
    }

    /* deleteAll - FAQ 테이블 행 전제 삭제 */
    @Override
    public int deleteAll() {
        return session.delete(namespace + "deleteAll");
    }

    /* delete - 특정 FAQ 게시글을 하나 삭제; 게시글 번호로 관리자만 FAQ 글을 삭제 */
    // 관리자는 FaqController에서 확인
    @Override
    public int delete(Integer faq_no) {
        return session.delete(namespace + "delete", faq_no);
    }

    /* insert - FAQ 게시글로 FaqDto을 등록 */
    @Override
    public int insert(FaqDto faqDto) {             /* insert된 row의 수라서 int형 */
        return session.insert(namespace + "insert", faqDto);
    }

    @Override
    public List<FaqDto> selectAll() {
        return session.selectList(namespace + "selectAll");
    }

    /* select - 선택한 FAQ 게시글을 하나 조회 */
    // faq_no, cate_no, faq_order, faq_title, faq_content, faq_closing, faq_att_file
    @Override
    public FaqDto select(Integer faq_no) {
        /* 조회 결과는 FaqDto로 받음 */
        return session.selectOne(namespace + "select", faq_no);
    }

    /* update - FAQ에 등록된 게시글을 수정; 게시글의 번호와 담당자가 같은지 확인 */
    @Override
    public int update(FaqDto faqDto) {
//        System.out.println("Updating FAQ with data: " + faqDto);
        return session.update(namespace + "update", faqDto);
    }

    @Override
    public int increaseViewCnt(Integer faq_no) {
        return session.update(namespace + "increaseViewCnt", faq_no);
    }

    @Override
    public String joinCategory(Integer cate_no) {
        return session.selectOne(namespace + "joinCategory", cate_no);
    }
}
