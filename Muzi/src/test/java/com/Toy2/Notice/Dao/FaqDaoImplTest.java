package com.Toy2.Notice.Dao;

import com.Toy2.Notice.domain.FaqDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/* @RunWith, @ContextConfiguration 추가 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class FaqDaoImplTest {

    /* FaqDao 주입 받기 */
    @Autowired FaqDao faqDao;

    /* 메서드 이름으로 테스트 구분 */


    // 개수_테스트 - FAQ 게시글 개수 세기
    @Test
    public void countTest() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 1);
//        int faq_no = faqDao.selectAll().get(0).getFaq_no();
        System.out.println(faqDto);
        System.out.println(faqDao.selectAll().get(0).getFaq_no());
        System.out.println();

        /* 두 번째 행 추가 */
        FaqDto faqDto2 = new FaqDto(407, 1, 'Y',"해외배송 상품도 주문취소가 가능한가요?",
                "하나의 주문번호에 여러 상품을 주문한 경우 상품이 별도로  배송되어 여러개에 운송장으로 발송될 수 있습니다.\n" +
                        "이런 경우 택배사에 확인 하시기 전에 먼저 번거로우시더라도 무인양품 고객센터로 문의 주시면 추가 송장으로 출고된 택배 운송장 확인 후 안내해 드리겠습니다.\n" +
                        "MUJI 온라인 스토어 고객센터 전화번호는 1577-2892 입니다.\n" +
                        "- 고객센터 운영시간\n" +
                        "  평일 (월~금) : 9:00~20:00\n" +
                        " ＊주말/공휴일 휴무 ");
        assertTrue(faqDao.insert(faqDto2) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 2);
        System.out.println(faqDto2);
        System.out.println(faqDao.selectAll().get(1).getFaq_no());
    }

    // deleteAllTest1 - 모두 삭제하기
    @Test
    public void deleteAllTest1() {
        faqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);       /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.deleteAll() == 1);        /* 행 1개밖에 없음 - 삭제되는 행 1개*/
        assertTrue(faqDao.count() == 0);        /* 모두 삭제했으니 행 0개*/

        faqDto = new FaqDto(402, 1, 'N', "no content", "asdf");
        assertTrue(faqDao.insert(faqDto) == 1);         /* insert 2번 */
        assertTrue(faqDao.insert(faqDto) == 1);         /* insert 2번 */
        assertTrue(faqDao.deleteAll() == 2);            /* 행 2개 전부 삭제 */
        assertTrue(faqDao.count() == 0);
    }


    // deleteAllTest2 - faq_admin이 달라도 삭제됨
    @Test
    public void deleteAllTest2() {
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        FaqDto faqDto = new FaqDto(407, 1, 'Y',"해외배송 상품도 주문취소가 가능한가요?",
                "하나의 주문번호에 여러 상품을 주문한 경우 상품이 별도로  배송되어 여러개에 운송장으로 발송될 수 있습니다.\n" +
                        "이런 경우 택배사에 확인 하시기 전에 먼저 번거로우시더라도 무인양품 고객센터로 문의 주시면 추가 송장으로 출고된 택배 운송장 확인 후 안내해 드리겠습니다.\n" +
                        "MUJI 온라인 스토어 고객센터 전화번호는 1577-2892 입니다.\n" +
                        "- 고객센터 운영시간\n" +
                        "  평일 (월~금) : 9:00~20:00\n" +
                        " ＊주말/공휴일 휴무 ");
        assertTrue(faqDao.insert(faqDto) == 1);         /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 1);

        faqDto.setFaq_admin("asdfasdf");        /* faq_admin 변경 */
        assertFalse(faqDao.delete(faqDto.getFaq_no(), faqDto.getFaq_admin()) == 1);     /* faq_admin이 달라서 삭제 안됨 */

        faqDto.setFaq_admin("admin");        /* faq_admin 동일하게 변경 */
        assertTrue(faqDao.deleteAll() == 1);      /* 삭제되는 행은 1개 */
        assertTrue(faqDao.count() == 0);        /* 행 삭제해서 없음 */
    }


    // deleteTest1 - 특정 FAQ 하나 삭제 (faq_no와 faq_admin이 같아야 삭제 가능)
    @Test
    public void deleteTest1(){
        faqDao.deleteAll();        /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);       /* 행 없음 */

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();     /* 등록한 faqDto의 faq_no 가져와서 저장 */
        assertTrue(faqDao.delete(faq_no, faqDto.getFaq_writer()) == 1);        /* admin 같음 - 삭제됨 */
        assertTrue(faqDao.count() == 0);        /* 삭제했으니 행 0개*/

        assertTrue(faqDao.insert(faqDto) == 1);     /* faqDto 다시 등록 */
        faq_no = faqDao.selectAll().get(0).getFaq_no();     /* 등록한 faqDto의 faq_no 저장 */
        assertTrue(faqDao.delete(faq_no, faqDto.getFaq_writer() + "aa") == 0);      /* admin 다른 값 - 삭제 불가 */

        assertTrue(faqDao.delete(faq_no, faqDto.getFaq_writer()) == 1);      /* admin 변경 안함 - 삭제 가능 */
        assertTrue(faqDao.count() == 0);
    }


    // deleteTest2 - faq_no, faq_admin 값이 다르면 삭제 못함
    @Test
    public void deleteTest2() {
        faqDao.deleteAll();        /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);       /* 행 없음 */

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        // faq_no 계속 바뀌니 insert 이후에 바로 저장하기
        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();     /* 등록한 faqDto의 faq_no 값 저장 */
        assertTrue(faqDao.count() == 1);

        faqDto.setFaq_writer("change");      /* admin 값 변경 */
        assertFalse(faqDao.delete(faq_no, faqDto.getFaq_writer()) == 1);        /* admin 다르니 삭제 못함 */
        assertTrue(faqDao.count() == 1);        /* 삭제 못했으니 행 1개*/

        faqDto.setFaq_writer("test");       /* faq_admin 다시 동일하게 변경 */
        assertTrue(faqDao.delete(faq_no, faqDto.getFaq_writer()) == 1);        /* admin 같음 - 삭제됨 */
        assertTrue(faqDao.count() == 0);        /* 삭제했으니 행 0개*/
    }

    // insertTest1 - FAQ 게시글 추가
    @Test
    public void insertTest1() {
        faqDao.deleteAll();             /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);         /* 추가되는 행 1개*/
        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();     /* insert한 faqDao의 faq_no 가져와서 저장 */
        assertTrue(faqDao.count() == 1);             /* 첫 번째 행 추가 - count 개수 1 */
        assertTrue(faq_no.equals(faqDao.selectAll().get(0).getFaq_no()));
        System.out.println(faqDto);
        System.out.println(faq_no);

        /* 두 번째 행 추가 */
        FaqDto faqDto2 = new FaqDto(407, 1, 'Y',"해외배송 상품도 주문취소가 가능한가요?",
                "하나의 주문번호에 여러 상품을 주문한 경우 상품이 별도로  배송되어 여러개에 운송장으로 발송될 수 있습니다.\n" +
                        "이런 경우 택배사에 확인 하시기 전에 먼저 번거로우시더라도 무인양품 고객센터로 문의 주시면 추가 송장으로 출고된 택배 운송장 확인 후 안내해 드리겠습니다.\n" +
                        "MUJI 온라인 스토어 고객센터 전화번호는 1577-2892 입니다.\n" +
                        "- 고객센터 운영시간\n" +
                        "  평일 (월~금) : 9:00~20:00\n" +
                        " ＊주말/공휴일 휴무 ");
        assertTrue(faqDao.insert(faqDto2) == 1);         /* 추가되는 행 1개*/
        assertTrue(faqDao.count() == 2);        /* 두 번째 행 추가 - count 개수 2 */
    }


    // insertTest2 - 동일한 내용이어도 추가되어야 함 (faq_no 다르게)
    @Test
    public void insertTest2() {
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(105, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);        /* 추가되는 행 1개 */
        assertTrue(faqDao.count() == 1);             /* 세 번째 행 추가 - count 개수 3 */

        /* 동일한 내용이어도 추가되어야 함 (faq_no 다르게) */
        FaqDto faqDto2 = new FaqDto(105, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto2) == 1);        /* 추가되는 행 1개 */
        assertTrue(faqDao.count() == 2);             /* 세 번째 행 추가 - count 개수 3 */
    }


    /* insertTest3 - NOT NULL 컬럼에 NULL 값 들어갔을 떄  */
    @Test
    public void insertTest3() {
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        try {
            FaqDto faqDto = new FaqDto(102, 2, 'Y', null, null);
            assertTrue(faqDao.insert(faqDto) == 1);
            assertTrue(faqDao.count() == 1);
        } catch (DataIntegrityViolationException e){
            System.out.println("NULL값은 허용되지 않습니다.");
        }
    }

    // selectAllTest1() - 전체 조회
    @Test
    public void selectAllTest() {
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        List<FaqDto> list = faqDao.selectAll();
        assertTrue(list.size() == 0);

        FaqDto faqDto = new FaqDto(101, 2, 'N', "title", "content");
        assertTrue(faqDao.insert(faqDto) == 1);

        list = faqDao.selectAll();
        assertTrue(list.size() == 1);

        assertTrue(faqDao.insert(faqDto) == 1);
        list = list = faqDao.selectAll();
        assertTrue(list.size() == 2);

        for (FaqDto f : list){
            System.out.println("faq_no : " + f.getFaq_no() + ", faq_title : " + f.getFaq_title());
        }
    }

    // selectTest2 - 존재하는 faq_no FAQ 게시글 조회
    // faq_no 로 조회 x -> list 가져와서 (전부 가져와서) 꺼내오기
    @Test
    public void selectTest1() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);        /* 행 없음 */

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오.   " +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다. " +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/

        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();         /* 등록한 faqDto의 faq_no 저장 */
        faqDto.setFaq_no(faq_no);
//        System.out.println(faqDto);
//        System.out.println();
//        System.out.println(faq_no);
        FaqDto faqDto2 = faqDao.select(faq_no);
        assertTrue(faqDto.equals(faqDto2));          // faq_no로 조회해서 저장한 faqDto2는 insert한 faqDto와 같아야 함
    }


    //  selectTest2 - 존재하지 않는 faq_no로는 조회 못함
    // 지우기
    @Test
    public void selectTest2() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);        /* 행 없음 */

        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오.   " +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다. " +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);
        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();
        assertTrue(faqDao.count() == 1);

        try {
            FaqDto faqDto1 = faqDao.select(faq_no + 1);
        } catch (Exception ex){
            System.out.println("FAQ에 행 없음");
        }
    }


    // updateTest1 - 존재하는 faq_no의 컬럼값 수정하기
    @Test
    public void updateTest1() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();
        assertTrue(faqDao.count() == 1);
        System.out.println(faq_no);
        System.out.println(faqDao.select(faq_no));

        //faqDto.setCate_no(302);                 /* cate_no 값 변경 */
        faqDto.setFaq_order(3);          /* last_mod_id 값 변경 */
        assertTrue(faqDao.update(faqDto) == 1);
        System.out.println(faqDao.selectAll().get(0).getFaq_no());
    }


    // updateTest2 - faq_admin 관리자는 수정 못함
    @Test
    public void updateTest2() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);        /* 행 없음 */

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 1);            /* 행 1개 추가돼서 전체 행이 1개 */

        /* faq_admin은 값 변경 안됨 */
        faqDto.setCate_no(104);              /* cate_no 값 변경 */
        faqDto.setFaq_admin("change");         /* faq_admin 변경함 */
        System.out.println(faqDto);
        assertFalse(faqDao.update(faqDto) == 1);        /* faq_admin 달라져서 update 안됨*/

        /* faq_admin 원래대로 변경 */
        faqDto.setFaq_admin("admin1");
        faqDto.setCate_no(202);
        System.out.println(faqDto);
        assertTrue(faqDao.update(faqDto) == 1);
    }

    // updateTest3 - update 해서 NOT NULL인 컬럼 NULL로 됐을 때
    @Test
    public void updateTest3() {
        faqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(faqDao.count() == 0);        /* 행 없는 거 확인 */

        /* 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "title" ,"content");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 1);            /* 행 1개 추가돼서 전체 행이 1개 */

        try {
            /* 필수인 컬럼 NULL로 변경 */
            faqDto.setFaq_title(null);
            assertFalse(faqDao.update(faqDto) == 1);
        } catch (DataIntegrityViolationException e){
            System.out.println("NullPointerException : NULL을 허용하지 않습니다.");
        }
    }

//    @Test
//    public void increaseViewCnt(){
//        faqDao.deleteAll();     /* 테이블 비우기 */
//        assertTrue(faqDao.count() == 0);
//
//        FaqDto faqDto = new FaqDto(104, 1, 'Y', "title" ,"content");
//        assertTrue(faqDao.insert(faqDto) == 1);
//        Integer faq_no = faqDao.selectAll().get(0).getFaq_no();
//        assertTrue(faqDao.count() == 1);
//
//        // 조회수가 올라가는 건 읽을 때 == select할 때 (selectAll은 관리자를 위한 거)
//        faqDto = faqDao.select(faq_no);
//        assertTrue(faqDao.increaseViewCnt(faq_no) == 1);
//        System.out.println(faqDto.getFaq_view_cnt());
//        assertTrue(faqDto.getFaq_view_cnt() == 1);
//    }

    // 테이블에 없는 값으로 변경 faqDto.setCate_no(0);
}