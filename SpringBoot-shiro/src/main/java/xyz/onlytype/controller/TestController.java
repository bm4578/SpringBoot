package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.onlytype.util.HttpStatus;
import xyz.onlytype.util.R;

import java.util.concurrent.TimeUnit;

/**
 * @author 白也
 * @date 2023/1/23 13:45
 */
@RestController
@RequestMapping("test")
@Api(tags = "测试")
public class TestController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "权限测试")
    public R hello(){
        return R.ok(HttpStatus.SUCCESS,"访问成功");
    }


    @GetMapping("/test")
    @ApiOperation(value = "获取图形验证码")
    public R test(){
        return R.ok(stringRedisTemplate.opsForValue().get("verifyCode"));
    }

//    @GetMapping
//    @ApiOperation(value = "取缓存")
//    public R hello(){
//        return R.ok(redisCache.getCacheObject("verifyCode"));
//    }

    @GetMapping("/getMsg")
    @ApiOperation(value = "获取邮箱验证码")
    public R getMsg(){
        return R.ok(stringRedisTemplate.opsForValue().get("eamilCode"));
    }
}
