package com.Toy2.Cust.Dao;

import com.Toy2.Cust.Domain.CustDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class CustDaoTest {
    @Autowired
    private CustDao custDao;


    /* 고객 count */
    @Test
    public void count() throws Exception {
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);

        /* bbb 고객 가입 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");

        /* bbb고객 데이터 insert 후 count */
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==1);

        /* ccc 고객 가입 - 데이터 insert 후 count */
        cust = CustDto.custDto("ccc@gmail.com");
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==2);
    }

    /* 고객 insert */
    @Test
    public void insert() throws Exception {
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);

        /* bbb : 고객 가입 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");
        assertTrue(custDao.insert(cust)==1);

        /* bbb : 동일고객이 회원가입시 같은지 비교 */
        CustDto cust2 = CustDto.custDto("bbb@gmail.com");

        /* bbb : 동일고객이 가입할경우 예외발생 */
        assertThrows(Exception.class, ()-> {
            custDao.insert(cust2);
        });

        /* 고객의 총 count는 1 */
        assertTrue(custDao.count()==1);

        /* ccc 고객 가입후 bbb 고객과 같은지 비교 */
        CustDto cust3 = CustDto.custDto("ccc@gmail.com");
        assertTrue(custDao.insert(cust3)==1);
        assertTrue(!cust.equals(cust3));

        /* 고객의 총 count는 2 */
        assertTrue(custDao.count()==2);

        /* bbb 고객 select */
        CustDto cust4 = custDao.selectEmail(cust2.getC_email());

        /* bbb 고객 탈퇴 남은 고객의 총 count는 1 */
        assertTrue(custDao.delete(cust4.getC_email())==1);
        assertTrue(custDao.count()==1);
    }

    /* 고객 select */
    @Test
    public void select() throws Exception {
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);

        /* bbb : 고객 가입, 고객의 총 count는 1 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==1);


        /* bbb : 고객의 id를 select 후 cust2에 저장 */
        CustDto cust2 = custDao.selectEmail(cust.getC_email());


        /* equals로 비교후 동일한 고객임을 확인. 고객의 총 count는 1 */
        assertTrue(cust.getC_email().equals(cust2.getC_email()));
        assertTrue(custDao.count()==1);


        /* bbb 고객 삭제후, 고객의 총 count는 0 */
        assertTrue(custDao.delete(cust2.getC_email())==1);
        assertTrue(custDao.count()==0);
    }

    /* 고객 update */
    @Test
    public void updateCust() throws Exception{
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);


        /* bbb : 고객 가입, 고객의 총 count는 1 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==1);


        /*  bbb고객의 pwd가 일치하지않을경우 예외발생 assert문으로 확인  */
        String cust_pwd = cust.getC_pwd();
        assertTrue(custDao.selectEmail(cust.getC_email()).getC_pwd().equals(cust.getC_pwd()));

        /* bbb고객의 pwd가 일치할경우 update 실행 */
        cust.setC_pwd("4321");
        cust.setC_name("ccc");
        cust.setC_nick("ccc");
        cust.setC_phn("01000000000");
        cust.setC_zip("newzip");
        cust.setC_road_a("c New Road Add");


        /* update문이 실행되고 고객의 총 count는 1인지 확인 */
        assertTrue(custDao.updateCust(cust)==1);
        assertTrue(custDao.count()==1);


        /* bbb : 고객의 id를 select 후 cust2에 저장 */
        CustDto cust2 = custDao.selectEmail(cust.getC_email());


        /* 비밀번호만 수정후 update 재실행 */
        cust.setC_pwd("5555");


        /* update 실행. 고객의 총 count는 1인지 확인 */
        assertTrue(custDao.updateCust(cust)==1);


        /* equals로 비교후 동일한 고객인지 확인 및 변경된 비밀번호 확인. 고객의 총 count는 1 */
        assertTrue(cust.getC_email().equals(cust2.getC_email()));
        assertTrue(cust.getC_pwd()=="5555");
        assertTrue(custDao.count()==1);
    }

    /* 로그인할경우 로그인시간 update */
    @Test
    public void updateLogin() throws Exception{
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);


        /* bbb : 고객 가입, 고객의 총 count는 1 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==1);


        /* 로그인한 고객의 마지막로그인시간을 업데이트 한다 */
        assertTrue(custDao.updateLogin(cust.getC_email())==1);
    }

    /* 고객 전체삭제 */
    @Test
    public void deleteAll() throws Exception{
        /* 전체고객 삭제 후 count */
        custDao.deleteAll();
        assertTrue(custDao.count()==0);


        /* bbb : 고객 가입, 고객의 총 count는 1 */
        CustDto cust = CustDto.custDto("bbb@gmail.com");
        assertTrue(custDao.insert(cust)==1);
        assertTrue(custDao.count()==1);


        /* 고객 전체 삭제 count는 0 */
        assertTrue(custDao.deleteAll()==1);
        assertTrue(custDao.count()==0);


        /* 고객 5명 생성 count는 5 */
        CustDto custs = null;
        for(int i=1; i<=5; i++){
            custs= CustDto.custDto("ccc"+i+"@naver.com");
            custDao.insert(custs);
        }
        assertTrue(custDao.count()==5);


        /* 고객 1명 삭제 */
        assertTrue(custDao.delete(custDao.getCustAll().get(0).getC_email())==1);


        /* 고객의 총 count는 4 */
        assertTrue(custDao.count()==4);


        /* 고객 4명 삭제 count는 0 */
        assertTrue(custDao.deleteAll()==4);
        assertTrue(custDao.count()==0);
    }
}