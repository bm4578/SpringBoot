package xyz.onlytype.controller;

import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.onlytype.config.utils.ResultModel;
import xyz.onlytype.service.SysUserService;
import xyz.onlytype.service.impl.SysUserImpl;

import java.util.concurrent.TimeUnit;

/**
 * @author 白也
 * @title 用户页面
 * @date 2023/1/27 4:08 下午
 */

@Api(value = "/api/user/", tags = {"用户中心"})
@RestController
@RequestMapping("/api/user/")
public class SysUserController {
    @Autowired
    private SysUserImpl sysUser;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * @param username 用户名
     * @return
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true)
//            @ApiImplicitParam(dataType = "String",name = "code", value = "验证码",required = true)
    })
    @PostMapping(value = "/login")
    public void login(String username){
        sysUser.loadUserByUsername(username);
//        try {
//            return ResultModel.ok("登录成功",);
//        } catch (UsernameNotFoundException e) {
//            return ResultModel.error("登录失败",e.getMessage());
//        }
    }

    /**
     * 用户注册
     * @param username 账号
     * @param password 密码
     * @param email 邮箱
     * @return
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true),
            @ApiImplicitParam(dataType = "String",name = "email", value = "邮箱",required = true),
            @ApiImplicitParam(dataType = "String",name = "code", value = "邮箱验证码",required = true)
    })
    @PostMapping(value = "/register")
    public ResultModel register(String username , String password,String email,String code){
        try {
            String emailCode = stringRedisTemplate.opsForValue().get("emailCode");
            assert emailCode != null;
            if (emailCode.equalsIgnoreCase(code)){
                if (sysUser.register(username, password, email)){
                    return ResultModel.ok("注册成功");
                } else {
                    return ResultModel.error("注册失败，请联系管理员 ！！！");
                }

            }else {
                return ResultModel.error("邮箱验证码错误 ！！！");
            }
        } catch (Exception e) {
            return ResultModel.error("未知异常",e.getMessage());
        }
    }
    /**
     * 发送邮箱验证码
     * @param email 邮箱
     */
    @PostMapping("/getMsg")
    @ApiOperation(value = "邮箱验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "email", value = "收件人",required = true)
    })
    public ResultModel getMsg(String email){
        try {
            String template = "验证码";
            String emailCode = RandomUtil.randomStringUpper(5);
            String message = "验证码为: " + emailCode + "有效期为五分钟！！！";
            // 存入redis,有效期为5分钟
            stringRedisTemplate.opsForValue().set("emailCode",emailCode,5, TimeUnit.MINUTES);
            sysUserService.sendMessage(email,template,message);
            return ResultModel.ok("发送成功，请前往邮箱查看验证码");
        } catch (Exception e) {
            return ResultModel.error("发送失败",e.getMessage());
        }
    }
}
