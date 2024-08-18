package com.Toy2;

import com.Toy2.Notice.Dao.NoticeDao;
import com.Toy2.Notice.domain.NoticeDto;
import com.Toy2.Notice.entity.PageHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class NoticeTest {
    @Autowired
    NoticeDao noticeDao;

    PageHandler pagehanddler;
//    FileReader fr;
//    String str;
//    int data;
//    {
//        try {
//            fr = new FileReader("D:\\Noticedummy.csv");
//            while((data=fr.read())!=-1) {
//                str += (char)data;
//            }
//            fr.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    String[] strarr = str.split("\n");
//
//    for(String i : strarr) {
//        String[] arr=i.split(",");
//        NoticeDto noticeDto = new NoticeDto();
//        noticeDto.setTitle(arr[0]);
//        noticeDto.setContents(arr[1]);
//        noticeDto.setCreateDate(new Date(arr[2]));
//    }



    @Test
    public void countTest(){
        int cnt=noticeDao.count();
        Assert.assertEquals(cnt,16);
    }
    @Test
    public void selectTest(){
        pagehanddler = new PageHandler(noticeDao.count(), 1);
        List<NoticeDto> dtolist=noticeDao.selectNoticePage(pagehanddler);
        Assert.assertEquals(dtolist.size(),10);
    }

}
