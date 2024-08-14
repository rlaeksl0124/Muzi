package com.Toy2.Notice.Dao;

import com.Toy2.Faq.Dao.InqDao;
import com.Toy2.Faq.Domain.InqDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/* @RunWith, @ContextConfiguration 추가 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class InqDaoImplTest {

    /* InqDao 주입 받기 */
    @Autowired
    InqDao inqDao;


    // 개수_테스트 - Inq 게시글 개수 세기
    @Test
    public void countTest() {
        inqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(inqDao.count() == 1);
//        int inq_no = inqDao.selectAll().get(0).getinq_no();
        System.out.println(inqDto);
        System.out.println(inqDao.selectAll().get(0).getInq_no());
        System.out.println();

        /* 두 번째 행 추가 */
        InqDto inqDto2 = new InqDto(302, "spring@gmail.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto2) == 1);     /* 추가되는 행은 1개*/
        assertTrue(inqDao.count() == 2);
        System.out.println(inqDto2);
        System.out.println(inqDao.selectAll().get(1).getInq_no());
    }

    // deleteAllTest1 - 모두 삭제하기
    @Test
    public void deleteAllTest1() {
        inqDao.deleteAll();                             /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);       /* 행 없음 */

        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(inqDao.deleteAll() == 1);        /* 행 1개밖에 없음 - 삭제되는 행 1개*/
        assertTrue(inqDao.count() == 0);        /* 모두 삭제했으니 행 0개*/

        inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);         /* insert 2번 */
        assertTrue(inqDao.insert(inqDto) == 1);         /* insert 2번 */
        assertTrue(inqDao.deleteAll() == 2);            /* 행 2개 전부 삭제 */
        assertTrue(inqDao.count() == 0);
    }


    // deleteAllTest2 - faq_admin이 달라도 삭제됨
    @Test
    public void deleteAllTest2() {
        inqDao.deleteAll();
        assertTrue(inqDao.count() == 0);

        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);         /* 추가되는 행은 1개*/
        assertTrue(inqDao.count() == 1);
        
        assertFalse(inqDao.delete(inqDto.getInq_no(), inqDto.getInq_admin()) == 1);     /* faq_admin이 달라서 삭제 안됨 */

        inqDto.setInq_admin("admin");        /* faq_admin 동일하게 변경 */
        assertTrue(inqDao.deleteAll() == 1);      /* 삭제되는 행은 1개 */
        assertTrue(inqDao.count() == 0);        /* 행 삭제해서 없음 */
    }


    // deleteTest1 - 특정 FAQ 하나 삭제 (inq_no와 faq_admin이 같아야 삭제 가능)
    @Test
    public void deleteTest1(){
        inqDao.deleteAll();        /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);       /* 행 없음 */

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
        Integer inq_no = inqDao.selectAll().get(0).getInq_no();     /* 등록한 inqDto의 inq_no 가져와서 저장 */
        assertTrue(inqDao.delete(inq_no, inqDto.getInq_admin()) == 1);        /* admin 같음 - 삭제됨 */
        assertTrue(inqDao.count() == 0);        /* 삭제했으니 행 0개*/

        assertTrue(inqDao.insert(inqDto) == 1);     /* inqDto 다시 등록 */
        inq_no = inqDao.selectAll().get(0).getInq_no();     /* 등록한 inqDto의 inq_no 저장 */
        assertTrue(inqDao.delete(inq_no, inqDto.getInq_admin() + "aa") == 0);      /* admin 다른 값 - 삭제 불가 */

        assertTrue(inqDao.delete(inq_no, inqDto.getInq_admin()) == 1);      /* admin 변경 안함 - 삭제 가능 */
        assertTrue(inqDao.count() == 0);
    }


    // deleteTest2 - inq_no, faq_admin 값이 다르면 삭제 못함
    @Test
    public void deleteTest2() {
        inqDao.deleteAll();        /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);       /* 행 없음 */

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
        // inq_no 계속 바뀌니 insert 이후에 바로 저장하기
        Integer inq_no = inqDao.selectAll().get(0).getInq_no();     /* 등록한 inqDto의 inq_no 값 저장 */
        assertTrue(inqDao.count() == 1);

        inqDto.setInq_admin("change");      /* admin 값 변경 */
        assertFalse(inqDao.delete(inq_no, inqDto.getInq_admin()) == 1);        /* admin 다르니 삭제 못함 */
        assertTrue(inqDao.count() == 1);        /* 삭제 못했으니 행 1개*/

        inqDto.setInq_admin("admin");       /* faq_admin 다시 동일하게 변경 */
        assertTrue(inqDao.delete(inq_no, inqDto.getInq_admin()) == 1);        /* admin 같음 - 삭제됨 */
        assertTrue(inqDao.count() == 0);        /* 삭제했으니 행 0개*/
    }

    // insertTest1 - FAQ 게시글 추가
    @Test
    public void insertTest1() {
        inqDao.deleteAll();             /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);         /* 추가되는 행 1개*/
        Integer inq_no = inqDao.selectAll().get(0).getInq_no();     /* insert한 inqDao의 inq_no 가져와서 저장 */
        assertTrue(inqDao.count() == 1);             /* 첫 번째 행 추가 - count 개수 1 */
        assertTrue(inq_no.equals(inqDao.selectAll().get(0).getInq_no()));
        System.out.println(inqDto);
        System.out.println(inq_no);

        /* 두 번째 행 추가 */
        InqDto inqDto2 = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto2) == 1);         /* 추가되는 행 1개*/
        assertTrue(inqDao.count() == 2);        /* 두 번째 행 추가 - count 개수 2 */
    }


    // insertTest2 - 동일한 내용이어도 추가되어야 함 (inq_no 다르게)
    @Test
    public void insertTest2() {
        inqDao.deleteAll();        /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);       /* 행 없음 */

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(inqDao.count() == 1);
        
        /* 동일한 내용이어도 추가되어야 함 (inq_no 다르게) */
        InqDto inqDto2 = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto2) == 1);        /* 추가되는 행 1개 */
        assertTrue(inqDao.count() == 2);             /* 세 번째 행 추가 - count 개수 3 */
    }


    /* insertTest3 - NOT NULL 컬럼에 NULL 값 들어갔을 떄  */
    @Test
    public void insertTest3() {
        inqDao.deleteAll();
        assertTrue(inqDao.count() == 0);

        try {
            InqDto inqDto = new InqDto(103, "test@test.com", "title",
                    "content", null, 'N', 'Y');
            assertTrue(inqDao.insert(inqDto) == 1);
            assertTrue(inqDao.count() == 1);
        } catch (DataIntegrityViolationException e){
            System.out.println("NULL값은 허용되지 않습니다.");
        }
    }

    // selectAllTest1() - 전체 조회
    @Test
    public void selectAllTest() {
        inqDao.deleteAll();
        assertTrue(inqDao.count() == 0);

        List<InqDto> list = inqDao.selectAll();
        assertTrue(list.size() == 0);

        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);

        list = inqDao.selectAll();
        assertTrue(list.size() == 1);

        assertTrue(inqDao.insert(inqDto) == 1);
        list = list = inqDao.selectAll();
        assertTrue(list.size() == 2);

        for (InqDto i : list){
            System.out.println("inq_no : " + i.getInq_no() + ", inq_title : " + i.getInq_title());
        }
    }

    // selectTest2 - 존재하는 inq_no FAQ 게시글 조회
    // inq_no 로 조회 x -> list 가져와서 (전부 가져와서) 꺼내오기
    @Test
    public void selectTest1() {
        inqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);        /* 행 없음 */

        /* 첫 번째 행 추가 */
        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/

        Integer inq_no = inqDao.selectAll().get(0).getInq_no();         /* 등록한 inqDto의 inq_no 저장 */
        inqDto.setInq_no(inq_no);
//        System.out.println(inqDto);
//        System.out.println();
//        System.out.println(inq_no);
        InqDto inqDto2 = inqDao.select(inq_no);
        assertTrue(inqDto.equals(inqDto2));          // inq_no로 조회해서 저장한 inqDto2는 insert한 inqDto와 같아야 함
    }


    //  selectTest2 - 존재하지 않는 inq_no로는 조회 못함
    // 지우기
    @Test
    public void selectTest2() {
        inqDao.deleteAll();         /* 테이블 비우기 */
        assertTrue(inqDao.count() == 0);        /* 행 없음 */

        InqDto inqDto = new InqDto(103, "test@test.com", "title",
                "content", null, 'N', 'Y');
        assertTrue(inqDao.insert(inqDto) == 1);
        Integer inq_no = inqDao.selectAll().get(0).getInq_no();
        assertTrue(inqDao.count() == 1);

        try {
            InqDto inqDto1 = inqDao.select(inq_no + 1);
        } catch (Exception ex){
            System.out.println("FAQ에 행 없음");
        }
    }


//     updateTest1 - 존재하는 inq_no의 컬럼값 수정하기
//    @Test
//    public void updateTest1() {
//        inqDao.deleteAll();         /* 테이블 비우기 */
//        assertTrue(inqDao.count() == 0);
//
//        /* 첫 번째 행 추가 */
//        InqDto inqDto = new InqDto(103, "test@test.com", "title",
//                "content", null, 'N', 'Y');
//        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
//        Integer inq_no = inqDao.selectAll().get(0).getInq_no();
//        assertTrue(inqDao.count() == 1);
//        System.out.println(inq_no);
//        System.out.println(inqDto);
//
//        inqDto.setCate_no(302);              /* cate_no 값 변경 */
//        inqDto.setLast_mod_id("asdf");         /* last_mod_id 값 변경 */
//        assertTrue(inqDao.update(inqDto) == 1);
//        System.out.println(inqDao.selectAll().get(0).getInq_no());
//        System.out.println(inqDto);
//    }
//
//
//    // updateTest2 - faq_admin 관리자는 수정 못함
//    @Test
//    public void updateTest2() {
//        inqDao.deleteAll();         /* 테이블 비우기 */
//        assertTrue(inqDao.count() == 0);        /* 행 없음 */
//
//        /* 첫 번째 행 추가 */
//        InqDto inqDto = new InqDto(103, "test@test.com", "title",
//                "content", null, 'N', 'Y');
//        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
//        assertTrue(inqDao.count() == 1);            /* 행 1개 추가돼서 전체 행이 1개 */
//
//        /* faq_admin은 값 변경 안됨 */
//        inqDto.setCate_no(104);              /* cate_no 값 변경 */
//        inqDto.setInq_admin("change");         /* faq_admin 변경함 */
//        assertFalse(inqDao.update(inqDto) == 1);        /* faq_admin 달라져서 update 안됨*/
//
//        /* faq_admin 원래대로 변경 */
//        inqDto.setInq_admin("admin");
//        assertTrue(inqDao.update(inqDto) == 1);
//    }
//
//    // updateTest3 - update 해서 NOT NULL인 컬럼 NULL로 됐을 때
//    @Test
//    public void updateTest3() {
//        inqDao.deleteAll();         /* 테이블 비우기 */
//        assertTrue(inqDao.count() == 0);        /* 행 없는 거 확인 */
//
//        /* 행 추가 */
//        inqDto inqDto = new inqDto(104, 1, 'Y', "title" ,"content");
//        assertTrue(inqDao.insert(inqDto) == 1);     /* 추가되는 행은 1개*/
//        assertTrue(inqDao.count() == 1);            /* 행 1개 추가돼서 전체 행이 1개 */
//
//        try {
//            /* 필수인 컬럼 NULL로 변경 */
//            inqDto.setFaq_title(null);
//            assertFalse(inqDao.update(inqDto) == 1);
//        } catch (DataIntegrityViolationException e){
//            System.out.println("NullPointerException : NULL을 허용하지 않습니다.");
//        }
//    }
}