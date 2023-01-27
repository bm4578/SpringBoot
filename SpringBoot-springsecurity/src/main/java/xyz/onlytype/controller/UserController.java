package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 白也
 * @title 用户页面
 * @date 2023/1/27 4:08 下午
 */

@Api(value = "/api/user/", tags = {"用户中心"})
@RestController
@RequestMapping("/api/user/")
public class UserController {
    /**
     * 测试
     * @return
     */
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "GET")
    @GetMapping
    public String test(){
        return "test";
    }
}
