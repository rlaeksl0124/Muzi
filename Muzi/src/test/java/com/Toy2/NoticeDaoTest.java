package com.Toy2;

import com.Toy2.Notice.Dao.NoticeDao;
import com.Toy2.Notice.Dao.NoticeTestDao;
import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class NoticeDaoTest {
    @Autowired
    NoticeDao noticeDao;
    @Autowired
    NoticeTestDao noticeTestDao;

    PageHandler pagehanddler;
    ArrayList<NoticeDto> dtolist = new ArrayList<>();

    /* 외부 csv 파일 사용 하여 더미 dtolist 만들기 */
    public void dtoSetting(){
        FileReader fr;
        String str = "";
        int data;

        /* 문자 형식의 날짜를 Date 객체로 만들기 위해 필요함 */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /* csv 파일에서 읽어오기 */
        try {
            fr = new FileReader("D:\\Noticedummy.csv");
            while((data=fr.read())!=-1) {
                str += (char)data;
            }
            fr.close();
            String[] strarr = str.split("\n");

            /* 읽어온 데이터 바탕으로 Dto 제작 */
            /* 작성자 시스템 컬럼의 최초 작성자 마지막 수정자는 dto에서 기본값으로 초기화 */
            for(String i : strarr) {
                String[] arr=i.split("`");
                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setN_title(arr[0]);
                noticeDto.setN_contents(arr[1]);
                Date date = dateFormat.parse(arr[2]);
                noticeDto.setN_createDate(date);
                dtolist.add(noticeDto);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* db 초기화를 위한 메서드 */
    public void dbSetting(){

        /* db 초기화 */
        noticeTestDao.deleteAll();

        /* 번호 초기화 */
        noticeTestDao.autoIncreasereset();
    }

    @Test
    public void Inserttest(){

        /* 초기화 */
        dtoSetting();
        dbSetting();

        /* 초기화 학인 */
        Assert.assertEquals(noticeDao.count(),0);

        /* 만든 더미 첫번째 값 삽입 */
        NoticeDto dto=dtolist.get(0);
        noticeDao.insertNotice(dto);

        /* 1개 삽입 확인 */
        Assert.assertEquals(noticeDao.count(),1);

        /* 값 확인 */
        NoticeDto insertDto=noticeDao.selectNoticeById(1);
        Assert.assertEquals(insertDto.getN_title(), dto.getN_title());
        Assert.assertEquals(insertDto.getN_contents(), dto.getN_contents());
        Assert.assertEquals(insertDto.getN_createDate(), dto.getN_createDate());

        /* 초기화 */
        dtoSetting();
        dbSetting();

        /* 초기화 학인 */
        Assert.assertEquals(noticeDao.count(),0);

        /* 값 두개 삽입 */
        noticeDao.insertNotice(dtolist.get(0));
        noticeDao.insertNotice(dtolist.get(1));

        /* 2개 삽입 확인 */
        Assert.assertEquals(noticeDao.count(),2);

        /* 한개 삭제  및 확인 */
        noticeDao.deleteById(1);
        Assert.assertEquals(noticeDao.count(),1);
        Assert.assertNull(noticeDao.selectNoticeById(1));
    }
    @Test
    public void Deletetest(){
        /* 초기화 */
        dtoSetting();
        dbSetting();
        Assert.assertEquals(noticeDao.count(),0);

        /* 만든 더미 첫번째 값 삽입 */
        NoticeDto dto=dtolist.get(0);
        noticeDao.insertNotice(dto);
        Assert.assertEquals(noticeDao.count(),1);
        noticeDao.deleteById(1);
        Assert.assertNull(noticeDao.selectNoticeById(1));

    }
    @Test
    public void updateTest(){
        /* 초기화 */
        dtoSetting();
        dbSetting();

        /* 초기화 확인 */
        Assert.assertEquals(noticeDao.count(),0);
        NoticeDto dto=dtolist.get(0);

        /* 삽입 및 확인*/
        noticeDao.insertNotice(dto);
        Assert.assertEquals(noticeDao.count(),1);

        /* 첫번째 값 가져와서 변경*/
        NoticeDto dto1=noticeDao.selectNoticeById(1);
        dto1.setN_title(dtolist.get(1).getN_title());
        dto1.setN_contents(dtolist.get(1).getN_contents());
        noticeDao.updateContents(dto1);

        /* 변경 확인 */
        Assert.assertEquals(noticeDao.selectNoticeById(1).getN_contents(),dto1.getN_contents());
        Assert.assertEquals(noticeDao.selectNoticeById(1).getN_title(),dto1.getN_title());


    }
    @Test
    public void InsertListtest(){
        dtoSetting();
        dbSetting();

        /* 전체 Dtolist insert */
        for(NoticeDto dto : dtolist){
            noticeDao.insertNotice(dto);
        }
        Assert.assertEquals(noticeDao.count(),dtolist.size());

        /* 값 확인*/
        Assert.assertEquals(noticeDao.selectNoticeById(1).getN_contents(),dtolist.get(0).getN_contents());
        Assert.assertEquals(noticeDao.selectNoticeById(1).getN_title(),dtolist.get(0).getN_title());
        Assert.assertEquals(noticeDao.selectNoticeById(2).getN_contents(),dtolist.get(1).getN_contents());
        Assert.assertEquals(noticeDao.selectNoticeById(2).getN_title(),dtolist.get(1).getN_title());
    }


    @Test
    public void countTest(){
        dtoSetting();
        dbSetting();
        int cnt=noticeDao.count();
        Assert.assertEquals(cnt,0);
    }
    @Test
    public void selectListTest(){
        dtoSetting();
        dbSetting();
        for(NoticeDto dto : dtolist){
            noticeDao.insertNotice(dto);
        }
        pagehanddler = new PageHandler(noticeDao.count(), 1);
        List<NoticeDto> dtolist1=noticeDao.selectNoticePage(pagehanddler);
        Assert.assertEquals(10,dtolist1.size());
        pagehanddler = new PageHandler(noticeDao.count(), pagehanddler.getEndPage());

        List<NoticeDto> dtolist2 = noticeDao.selectNoticePage(pagehanddler);
        Assert.assertEquals(dtolist.size()%10,dtolist2.size());
    }

}
