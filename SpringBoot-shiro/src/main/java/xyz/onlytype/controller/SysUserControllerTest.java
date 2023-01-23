package xyz.onlytype.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.onlytype.redis.RedisCache;

/**
 * @author 白也
 * @date 2023/1/23 13:19
 */
@SpringBootTest
class SysUserControllerTest {
    @Autowired
    RedisCache redisCache;
    @Test
    void hello() {
//        redisCache.getExpire("lcode");
//        String lcode = redisCache.getCacheObject("lcode");
//        System.out.println("剩余时间："+redisCache.getExpire("lcode")+ "值:"+ lcode );
    }
}