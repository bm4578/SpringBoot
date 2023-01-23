package xyz.onlytype.controller;
import cn.hutool.captcha.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.onlytype.VO.VerifyCodeVO;
import xyz.onlytype.redis.RedisCache;
import xyz.onlytype.service.SysUserService;
import org.springframework.web.bind.annotation.*;
import xyz.onlytype.util.HttpStatus;
import xyz.onlytype.util.R;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisCache redisCache;


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     *
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true),
            @ApiImplicitParam(dataType = "String",name = "code", value = "验证码",required = true)
    })
    @GetMapping
    public R login(String username,String password,String code){
        try {
        //比较验证码
        String verifyCode = redisCache.getCacheObject("verifyCode");
        if (verifyCode.equalsIgnoreCase(code)){
            //获取主题对象
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(username,password));
            return  R.ok(HttpStatus.SUCCESS,"登录成功");
        }else {
            return R.fail("验证码错误 ！！！");
        }
        } catch (UnknownAccountException e) {
            return R.fail(HttpStatus.ERROR,"账户不存在");
        } catch (IncorrectCredentialsException e){
            return R.fail(HttpStatus.ERROR,"密码错误");
        } catch (NullPointerException e){
            return R.fail("验证码过期，请重新获取 ！！！");
        }
    }
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     *
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true),
            @ApiImplicitParam(dataType = "String",name = "code", value = "验证码",required = true)
    })
    @PostMapping
    public R register(String username , String password,String code){
        try {
        //比较验证码
        String verifyCode = redisCache.getCacheObject("verifyCode");
        if (verifyCode.equalsIgnoreCase(code)){
            sysUserService.register(username , password);
            return R.ok(HttpStatus.SUCCESS,"注册成功");
        }else {
            return R.fail("验证码错误 ！！！");
        }
        }catch (NullPointerException e){
            return R.fail("验证码过期，请重新获取 ！！！");
        }
    }
    @GetMapping("/list")
    @RequiresRoles("admin")
    @ApiOperation(value = "权限测试")
    public R hello(){
         return R.ok(HttpStatus.SUCCESS,"访问成功");
    }


    @CrossOrigin
    @ApiOperation(value = "验证码")
    @GetMapping ("/imgCode")
    public void imgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        GifCaptcha captcha = CaptchaUtil.createGifCaptcha(200,100,4);
        //获取验证码中的文字内容
        String verifyCode = captcha.getCode();
        //存入redis
        redisCache.setCacheObject("verifyCode", verifyCode, VerifyCodeVO.TIME, TimeUnit.MINUTES);
        //设置返回格式
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(outputStream);
        //关闭流
        outputStream.close();
    }
}

