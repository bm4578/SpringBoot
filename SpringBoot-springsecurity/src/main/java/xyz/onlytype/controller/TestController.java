package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 白也
 * @title
 * @date 2023/1/28 6:28 下午
 */
@Api(value = "/test", tags = {"测试类"})
@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation(value = "测试类", notes = "测试类", httpMethod = "GET")
    @ApiImplicitParam(dataType = "String",name = "token", value = "token(默认30min过期)",required = true)
    @GetMapping
    public String test(String token){
        return "test";
    }
}
