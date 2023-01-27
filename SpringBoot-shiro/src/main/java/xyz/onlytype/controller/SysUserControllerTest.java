package xyz.onlytype.controller;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import xyz.onlytype.VO.user.UserRoleVO;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.service.SysUserService;

import java.util.List;

/**
 * @author 白也
 * @date 2023/1/23 13:19
 */
@SpringBootTest
class SysUserControllerTest {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void hello() {
        String eamilCode = stringRedisTemplate.opsForValue().get("eamilCode");
        System.out.println("剩余时间："+stringRedisTemplate.getExpire("eamilCode")+ "值:" + eamilCode);
    }

    @Test
    void testCache(){
        List<UserRoleVO> admin = sysUserService.findRolesByUserName("admin");
        admin.forEach(System.out::println);
        System.out.println("==================");
        sysUserService.findRolesByUserName("admin");
    }
    @Test
    void qs(){
    }
}