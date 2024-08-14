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
        DeliveryDto dDto = new DeliveryDto(1, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertEquals(dDao.deliveryInsert(dDto), 1);
    }
    @Test
    public void 배송지_추가_실패() throws Exception {
        DeliveryDto dDto = new DeliveryDto(89, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertEquals(dDao.deliveryInsert(dDto), 1);
        DeliveryDto dDto2 = new DeliveryDto(89, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(DuplicateKeyException.class, () -> dDao.deliveryInsert(dDto2));
        //같은 orderNo가 들어가지 않아야함.
        DeliveryDto dDto3 = new DeliveryDto(99999, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertThrows(DataIntegrityViolationException.class, () -> dDao.deliveryInsert(dDto3));
        //없는 orderNo가 들어가면 안됨.
    }

    @Test
    public void 배송지_출력_성공() throws Exception {
        DeliveryDto dDto = new DeliveryDto(5, "유민우", "집",
                "010-0000-0000", "서울 강남구 강남대로 364",
                "역삼동 826-21","10층","안전배달");
        assertEquals(dDao.deliveryInsert(dDto), 1);
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryName(), dDto.getDeliveryName());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryMessage(), dDto.getDeliveryMessage());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryAddressName(), dDto.getDeliveryAddressName());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryRoadAddress(), dDto.getDeliveryRoadAddress());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryPhone(), dDto.getDeliveryPhone());
        assertEquals(dDao.deliverySelect(dDto.getOrderNo()).getDeliveryDetailAddress(), dDto.getDeliveryDetailAddress());
    }
    @Test
    public void 배송지_출력_실패() throws Exception {
        assertNull(dDao.deliverySelect(99999));

    }
}