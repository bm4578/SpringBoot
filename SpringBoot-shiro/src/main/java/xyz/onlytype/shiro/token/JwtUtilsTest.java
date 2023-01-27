package xyz.onlytype.shiro.token;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 白也
 * @date 2023/1/25 12:46
 */
@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private TokenUtils tokenUtils;
    /**
     * 加密
     */
    @Test
    public void testToken(){
        Map<String, Object> map = new HashMap<>();
        map.put("key1","key2");
        String token = this.tokenUtils.getToken("签发人", "主体", map);
        System.out.println(token);
    }
    /**
     * 解密
     */
    @Test
    public void jiemiToken(){
        String token =
                "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFM1MTIifQ.eyJrZXkxIjoia2V5MiIsImV4cCI6MTY3NDcyMDQyMn0.1_kyGybAEmikJm9ethw3DJbtEcJFT0oTajcJ1IpVvjlrQjl5lfKKuowbdDYJxPny5nWyzozY2UVnYov3toLohA";
//        Boolean tokenExpired = TokenUtils.isTokenExpired(token);
        DateTime remainingTime = tokenUtils.getRemainingTime(token);
        System.out.println(remainingTime);
    }
    

}