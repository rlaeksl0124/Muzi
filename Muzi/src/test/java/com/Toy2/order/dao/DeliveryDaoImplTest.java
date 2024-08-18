package com.Toy2.order.dao;

import com.Toy2.order.entity.DeliveryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class DeliveryDaoImplTest {
    @Autowired
    private DeliveryDao dDao;
    @Test
    public void 배송지_추가_성공() throws Exception {// orderNo당 한개씩의 번호만
        dDao.deliveryDelete();
        DeliveryDto dDto = new DeliveryDto(1, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertEquals(dDao.deliveryInsert(dDto), 1);
    }
    @Test
    public void 배송지_추가_실패() throws Exception {
        dDao.deliveryDelete();//배송지전부삭제
        DeliveryDto dDto = new DeliveryDto(1, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertEquals(dDao.deliveryInsert(dDto), 1);
        //배송지생성확인
        assertThrows(DuplicateKeyException.class, () -> dDao.deliveryInsert(dDto));
        //같은 주문에 배송지가 또 들어가지 않는지 확인
        //같은 orderNo가 들어가지 않아야함.
        DeliveryDto dDto3 = new DeliveryDto(99999, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(DataIntegrityViolationException.class, () -> dDao.deliveryInsert(dDto3));
        //없는 orderNo가 들어가면 안됨.
    }

    @Test
    public void 배송지_출력_성공() throws Exception {
        dDao.deliveryDelete();
        DeliveryDto dDto = new DeliveryDto(1, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");//배송지 객체생성
        assertEquals(dDao.deliveryInsert(dDto), 1);//장바구니 객체 추가
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryName(), dDto.getDeliveryName());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryMessage(), dDto.getDeliveryMessage());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryAddressName(), dDto.getDeliveryAddressName());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryRoadAddress(), dDto.getDeliveryRoadAddress());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryPhone(), dDto.getDeliveryPhone());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryDetailAddress(), dDto.getDeliveryDetailAddress());
        //잘들어갔는지 확인
    }
    @Test
    public void 배송지_출력_실패() throws Exception {
        dDao.deliveryDelete();
        assertNull(dDao.deliverySelect(99999));//이상한번호로 확인을 하면 null

    }
}