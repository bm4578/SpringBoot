package xyz.onlytype.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 白也
 * @title
 * @date 2023/2/10 11:23 上午
 */
@SpringBootTest
class UserControllerTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void imgCode() {
        String imgCode = stringRedisTemplate.opsForValue().get("verifyCode");
        System.out.println(imgCode);
    }
}