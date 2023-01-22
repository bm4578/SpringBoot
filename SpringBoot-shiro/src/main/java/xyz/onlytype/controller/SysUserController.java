package xyz.onlytype.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import xyz.onlytype.service.SysUserService;
import org.springframework.web.bind.annotation.*;
import xyz.onlytype.util.HttpStatus;
import xyz.onlytype.util.R;

import javax.annotation.Resource;

/**
 * 用户表(SysUser)表控制层
 *
 * @author 白也
 * @since 2023-01-20 15:52:46
 */
@RestController
@RequestMapping("sysUser")
@Api(tags = "用户中心")
public class SysUserController{
    @Resource
    private SysUserService sysUserService;



    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     *
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true)
//            @ApiImplicitParam(dataType = "String",name = "code", value = "验证码",required = true)
    })
    @PostMapping
    public R register(String username , String password){
        try {
            sysUserService.register(username , password);
        }catch (Exception e){
            return R.fail(HttpStatus.NOT_IMPLEMENTED,e.getMessage());
        }
        return R.ok(HttpStatus.SUCCESS,"注册成功");
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     *
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true)
//            @ApiImplicitParam(dataType = "String",name = "code", value = "验证码",required = true)
    })
    @GetMapping
    public R login(String username,String password){
        //获取主题对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username,password));
        } catch (UnknownAccountException e) {
            return R.fail(HttpStatus.ERROR,"账户不存在");
        } catch (IncorrectCredentialsException e){
            return R.fail(HttpStatus.ERROR,"密码错误");
        }
        return  R.ok(HttpStatus.SUCCESS,"登录成功");
    }

    @GetMapping("/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "权限测试")
    public String hello(){
        return "hello,world";
    }
}

