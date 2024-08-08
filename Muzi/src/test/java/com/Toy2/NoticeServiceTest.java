package com.Toy2;

import com.Toy2.Notice.Dao.NoticeTestDao;
import com.Toy2.Notice.Service.NoticeService;
import com.Toy2.Notice.domain.NoticeDto;
import org.junit.Assert;
import org.junit.Before;
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
public class NoticeServiceTest {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeTestDao noticeTestDao;

    ArrayList<NoticeDto> dtolist = new ArrayList<>();

    /* 외부 csv 파일 사용 하여 더미 dtolist 만들기 */
    @Before
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
    @Before
    public void dbSetting(){

        /* db 초기화 */
        noticeTestDao.deleteAll();

        /* 번호 초기화 */
        noticeTestDao.autoIncreasereset();
    }
    @Test
    public void deleteContentsTest() throws Exception {
        Assert.assertEquals(noticeService.count(), 0);

        /* 삽입 */
        noticeService.addNotice(dtolist.get(0));
        Assert.assertEquals(noticeService.count(), 1);
        noticeService.addNotice(dtolist.get(1));
        Assert.assertEquals(noticeService.count(), 2);

        /* 삭제 (상태 변경) */
        noticeService.deleteContents(noticeService.getNotice(1));
        Assert.assertEquals(noticeService.count(), 1);

        /* 실제로 조회 했을 때 값이 지워지지는 않고 조회할 때 결과만 없어진다.
        (별도 처리해서 notice_no로 조회할 때도 조회가 안되도록 설정할 필요 있다. */
        NoticeDto noticeDto = noticeService.getNotice(1);
        Assert.assertEquals(noticeDto.getN_contents(),dtolist.get(0).getN_contents());
        Assert.assertEquals(noticeDto.getN_title(),dtolist.get(0).getN_title());
        Assert.assertNotEquals(noticeDto.getLast_mod_dt(),dtolist.get(0).getLast_mod_dt());
    }
    @Test
    public void addNoticeTest() throws Exception {
        Assert.assertEquals(noticeService.count(), 0);

        /* 삽입 테스트 */
        noticeService.addNotice(dtolist.get(0));
        Assert.assertEquals(noticeService.count(), 1);
        NoticeDto noticeDto = noticeService.getNotice(1);
        Assert.assertEquals(noticeDto.getN_title(),dtolist.get(0).getN_title());
        Assert.assertEquals(noticeDto.getN_contents(),dtolist.get(0).getN_contents());
        Assert.assertEquals(noticeDto.getN_createDate(),dtolist.get(0).getN_createDate());

    }
    @Test
    public void updateNoticeTest() throws Exception {
        Assert.assertEquals(noticeService.count(), 0);

        /* 값 넣기 */
        noticeService.addNotice(dtolist.get(0));
        Assert.assertEquals(noticeService.count(), 1);

        /* 값 확인 */
        NoticeDto noticeDto = noticeService.getNotice(1);
        Assert.assertEquals(noticeDto.getN_title(),dtolist.get(0).getN_title());
        Assert.assertEquals(noticeDto.getN_contents(),dtolist.get(0).getN_contents());

        /* 변경 후 업데이트 */
        noticeDto.setN_title(dtolist.get(1).getN_title());
        noticeDto.setN_contents(dtolist.get(1).getN_contents());
        Assert.assertEquals(dtolist.get(1).getN_title(),noticeDto.getN_title());
        Assert.assertEquals(noticeDto.getN_contents(),dtolist.get(1).getN_contents());
        noticeService.updateNotice(noticeDto);

        /* 값 확인 */
        noticeDto = noticeService.getNotice(1);
        Assert.assertEquals(noticeDto.getN_title(),dtolist.get(1).getN_title());
        Assert.assertEquals(noticeDto.getN_contents(),dtolist.get(1).getN_contents());
        Assert.assertNotEquals(noticeDto.getLast_mod_dt(),dtolist.get(0).getLast_mod_dt());
    }
    @Test
    public void getSelectTest() {
        Assert.assertEquals(noticeService.count(), 0);

        /* db 초기화(insert) */
        for(NoticeDto dto:dtolist){
            noticeService.addNotice(dto);
        }

        Assert.assertEquals(noticeService.count(), dtolist.size());

        /* 페이지 가져오기 */
        List<NoticeDto> pagelist = noticeService.getNoticePage(1);
        Assert.assertEquals(pagelist.size(), 10);
    }
}
