package com.Toy2.Notice.service;

import com.Toy2.Faq.Dao.FaqDao;
import com.Toy2.Faq.Domain.FaqDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/* @RunWith, @ContextConfiguration 추가 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class FaqServiceImplTest {

    @Autowired FaqDao faqDao;

    @Test
    public void countFaq() {
        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);

        /* 첫 번째 행 추가 */
        FaqDto faqDto = new FaqDto(104, 1, 'Y', "SUS SET 상품 조립시 선반이 잘 끼워지지 않습니다.",
                "선반을 끼우기 힘든 상태일때, 선반 상부를 가볍게 두드려 선반을 끼워주십시오." +
                        "그래도 끼워지지 않을 때는 전체적으로 조금씩 후크를 느슨하게 풀어주면 끼우기 쉬워집니다." +
                        "자세한 내용은 동영상 링크에서 확인 할 수 있습니다.\n" +
                        "    [관련 동영상]   https://www.muji.com/kr/mp4_file/sus_asssembly.mp4");
        assertTrue(faqDao.insert(faqDto) == 1);     /* 추가되는 행은 1개*/
        assertTrue(faqDao.count() == 1);

        faqDao.deleteAll();
        assertTrue(faqDao.count() == 0);
    }

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

    // deleteAllTest3- faq_admin이 달라도 삭제됨
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
        assertFalse(faqDao.update(faqDto) == 1);
        faqDto.setCate_no(104);
        assertTrue(faqDao.update(faqDto) == 1);

        assertTrue(faqDao.deleteAll() == 1);      /* 삭제되는 행은 1개 */
        assertTrue(faqDao.count() == 0);        /* 행 삭제해서 없음 */
    }


    @Test
    public void deleteFaq() {

    }

    @Test
    public void insertFaq() {
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void selectFaq() {
    }

    @Test
    public void updateFaq() {
    }
}