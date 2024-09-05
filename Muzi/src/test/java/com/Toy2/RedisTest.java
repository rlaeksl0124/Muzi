package com.Toy2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class RedisTest {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void test() {

        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        stringStringValueOperations.set("testKey", "testValue"); // redis set

        String result = stringStringValueOperations.get("testKey"); // redis get
        System.out.println(result);
//        Boolean check = stringStringValueOperations.getOperations().delete("testKey"); // redis delete
//        System.out.println(check);
        stringStringValueOperations.getOperations().rename("testKey", "testKey2"); //key명 변경

        stringStringValueOperations.set("testKey2", "testValue2"); // redis 덮어쓰기

    }
}
