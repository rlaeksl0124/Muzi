package com.Toy2.Faq.Service;

import com.Toy2.Faq.Domain.FaqDto;
import com.Toy2.Faq.Dao.FaqDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/* @RunWith, @ContextConfiguration 추가 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
@Transactional
public class FaqServiceImplTest {

    @Autowired FaqService faqService;
    @Autowired FaqDao faqDao;          // deleteAll을 하기 위함


    // count - 행 개수 세는 테스트
    @Test
    public void countTest() throws Exception {
        faqDao.deleteAll();
        assertTrue(faqService.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqService.count() == 1);

        faqDao.deleteAll();
        // 잘 들어갔는지 확인 - 다시 읽어와서 동일한지 확인
        assertTrue(faqService.count() == 0);
    }


    // deleteTest1 - 지정한 FAQ 게시글 하나씩 삭제
    @Test
    public void deleteTest1() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "[관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장
        assertTrue(faqService.delete(faq_no) == 1);        // 추가한 행 하나 지정 삭제
        assertTrue(faqService.count() == 0);        /* 삭제했으니 행 0개*/

        faqDto = new FaqDto(301, 2, 'N', "Title", "Content");       // 새로운 faqDto (FAQ 글) 생성
        assertTrue(faqService.insert(faqDto) == 1);         // 새로운 faqDto 추가하기
        faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장
        assertTrue(faqService.delete(faq_no) == 1);         // 새로 추가한 행 삭제
        assertTrue(faqService.count() == 0);
    }


    // deleteTest2 - 지정한 FAQ 게시글 하나씩 삭제할 때 이미 삭제한 FAQ 글을 다시 삭제하는 경우
    @Test
    public void deleteTest2() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "[관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장
        assertTrue(faqService.delete(faq_no) == 1);        // 추가한 행 하나 지정 삭제
        assertTrue(faqService.count() == 0);        /* 삭제했으니 행 0개*/

        // 이미 삭제한 게시글을 또 삭제할 때 - return 값으로 컨트롤러에게 주든가, 예외가 발생하든가 -> 케이스 정리하기
        // return 값이 0이면 삭제 실패 알림할지
        assertFalse(faqService.delete(faq_no) == 1);         // 새로 추가한 행 삭제
        assertTrue(faqService.count() == 0);
    }


    // insertTest - FAQ 게시글 등록 테스트
    @Test
    public void insertTest() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "[관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqService.count() == 1);            /* 행 1개 됨 */
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장

        assertTrue(faqService.select(faq_no).equals(faqDto));       // faq_no로 조회한 faqDto가 동일한지 확인
        // 다르게 나오는지도 보기 - 다른 테케
        assertTrue(faqService.count() == 1);            // 존재하는 행 1개
    }


    // selectAllTest - 등록된 FAQ 모두 조회
    @Test
    public void selectAllTest() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        List<FaqDto> list = faqService.selectAll();         // 모든 FAQ를 list에 저장
        assertTrue(list.size() == 0);         // 등록된 FAQ 없으므로 사이즈는 0

        FaqDto faqDto1 = new FaqDto(101, 2, 'N', "Title1", "Content1");       // 새로운 faqDto (FAQ 글) 생성
        assertTrue(faqService.insert(faqDto1) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no1 = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장
        assertTrue(faqService.count() == 1);            /* 행 1개 됨 */

        FaqDto faqDto2 = new FaqDto(301, 2, 'N', "Title2", "Content2");       // 새로운 faqDto (FAQ 글) 생성
        assertTrue(faqService.insert(faqDto2) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no2 = faqService.selectAll().get(1).getFaq_no();         // 이번에 추가한 행의 faq_no 저장 - 1번째 인덱스
        assertTrue(faqService.count() == 2);            /* 행 2개 됨 */

        list = faqService.selectAll();                      // 모든 FAQ를 list에 저장
        assertTrue(list.size() == 2);         // 등록한 게시글 2개만큼의 크기

        for (FaqDto f : list){
            System.out.println(f);
        }

        assertTrue(faqService.select(faq_no1).equals(faqDto1));         // 등록한 FAQ 게시글이 faq_no로 조회한 것과 동일
        assertTrue(faqService.select(faq_no2).equals(faqDto2));         // 등록한 FAQ 게시글이 faq_no로 조회한 것과 동일
    }


    // selectTest - 선택한 FAQ 게시글 조회
    // 조회시 faq_view_cnt 증가해야 됨
    @Test
    public void selectTest() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "[관련 동영상] https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqService.count() == 1);            /* 행 1개 됨 */
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장

        assertTrue(faqService.select(faq_no).equals(faqDto));       // 조회한 FAQ가 첫 번째로 추가한 faqDto와 동일한지
        assertTrue(faqService.select(faq_no).getFaq_view_cnt() == 1);       // 조회했으니 faq_view_cnt가 1 증가해서 1

        FaqDto faqDto2 = new FaqDto(301, 2, 'N', "Title2", "Content2");       // 새로운 faqDto (FAQ 글) 생성
        assertTrue(faqService.insert(faqDto2) == 1);     /* 추가되는 행은 1개*/
        faq_no = faqService.selectAll().get(1).getFaq_no();         // 이번에 추가한 행의 faq_no 저장 - 1번째 인덱스
        assertTrue(faqService.count() == 2);            /* 행 2개 됨 */

        assertTrue(faqService.select(faq_no).equals(faqDto2));       // 조회한 FAQ가 두 번째로 추가한 faqDto와 동일한지
        assertTrue(faqService.select(faq_no).getFaq_view_cnt() == 1);       // 조회했으니 faq_view_cnt가 1 증가해서 1
    }


    // updateTest
    @Test
    public void updateTest() throws Exception {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqService.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오.");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqService.count() == 1);            /* 행 1개 됨 */
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장

        assertTrue(faqService.select(faq_no).equals(faqDto));       // 조회한 FAQ가 추가한 faqDto와 동일한지
        assertTrue(faqService.select(faq_no).getFaq_view_cnt() == 1);       // 조회했으니 faq_view_cnt가 1 증가해서 1

        faqDto.setFaq_order(3);
        faqDto.setFaq_title("Title1");
        assertTrue(faqService.update(faqDto) == 1);         // 수정되는 행은 1개
        assertTrue(faqService.select(faq_no).getFaq_view_cnt() == 0);
    }

    // joinCategoryTest - 카테고리 이름을 가져오는지 확인
    @Test
    public void joinCategoryTest() throws Exception{
        faqDao.deleteAll();         // FAQ 테이블 비우기
        assertTrue(faqService.count() == 0);        // 데이터 없는거 확인

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "Title",
                "Content");
        assertTrue(faqService.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqService.count() == 1);            /* 행 1개 됨 */
        Integer faq_no = faqService.selectAll().get(0).getFaq_no();         // 추가한 행의 faq_no 저장
        Integer cate_no = faqService.selectAll().get(0).getCate_no();       // 추가한 행의 cate_no 저장
        assertTrue(faqDao.joinCategory(faq_no, cate_no).equals("수납선반류"));       // 카테고리 이름이 "수납선반류"로 동일해야 함
    }


    // 작성자가 수정할 때 관리자가 삭제하는 경우
}
