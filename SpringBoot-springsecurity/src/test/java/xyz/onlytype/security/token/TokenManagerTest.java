package xyz.onlytype.security.token;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 白也
 * @title
 * @date 2023/1/29 3:12 下午
 */
@SpringBootTest
class TokenManagerTest {
    @Autowired
    TokenManager tokenManager;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void getToken() {
        String token = tokenManager.getToken("test");
        redisTemplate.opsForValue().set("token",token);
        System.out.println(token);
    }

    @Test
    void getClaimsFromToken() {
        String token = (String) redisTemplate.opsForValue().get("token");
        String userName = tokenManager.getUserName(token);
        System.out.println("userName = " + userName);
    }
}