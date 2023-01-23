package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.onlytype.VO.VerifyCodeVO;
import xyz.onlytype.redis.RedisCache;
import xyz.onlytype.service.SysUserService;
import xyz.onlytype.util.R;

import java.util.concurrent.TimeUnit;

/**
 * @author 白也
 * @date 2023/1/23 13:45
 */
@RestController
@RequestMapping("test")
@Api(tags = "用户中心")
public class TestController {
    @Autowired
    RedisCache redisCache;

    @GetMapping("/test")
    @ApiOperation(value = "存缓存")
    public R test(){
        return R.ok();
    }

    @GetMapping
    @ApiOperation(value = "取缓存")
    public R hello(){
        return R.ok(redisCache.getCacheObject("verifyCode"));
    }
}
