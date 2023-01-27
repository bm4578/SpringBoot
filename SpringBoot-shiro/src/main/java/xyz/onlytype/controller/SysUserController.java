package xyz.onlytype.controller;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import xyz.onlytype.VO.VerifyCodeVO;
import xyz.onlytype.service.SysUserService;
import org.springframework.web.bind.annotation.*;
import xyz.onlytype.util.HttpStatus;
import xyz.onlytype.util.R;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
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
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true),
            @ApiImplicitParam(dataType = "String",name = "code", value = "图形验证码",required = true)
    })
    @GetMapping
    public R login(String username,String password,String code){
        try {
        //比较验证码
            String verifyCode = stringRedisTemplate.opsForValue().get("verifyCode");
            assert verifyCode != null;
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
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String",name = "username", value = "用户名",required = true),
            @ApiImplicitParam(dataType = "String",name = "password", value = "密码",required = true),
            @ApiImplicitParam(dataType = "String",name = "email", value = "收件人",required = true),
            @ApiImplicitParam(dataType = "String",name = "code", value = "邮箱验证码",required = true)
    })
    @PostMapping
    public R register(String username , String password,String email,String code){
        try {
            String emailCode = stringRedisTemplate.opsForValue().get("emailCode");
            assert emailCode != null;
            if (emailCode.equalsIgnoreCase(code)){
                sysUserService.register(username , password, email);
                return R.ok(HttpStatus.SUCCESS,"注册成功");
            }else {
                return R.fail(HttpStatus.ERROR,"邮箱验证码出错 ! ! !");
            }
        } catch (Exception e) {
            return R.fail(HttpStatus.ERROR,e.getMessage());
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
    public R getMsg(String email){
        try {
            String template = "验证码";
            String emailCode = RandomUtil.randomStringUpper(5);
            String message = "验证码为: " + emailCode + "有效期为五分钟！！！";
            // 存入redis,有效期为5分钟
            stringRedisTemplate.opsForValue().set("emailCode",emailCode,5,TimeUnit.MINUTES);
            sysUserService.sendMessage(email,template,message);
            return R.ok(HttpStatus.SUCCESS,"发送成功，请前往邮箱查看验证码");
        } catch (Exception e) {
            return R.fail(HttpStatus.ERROR,e.getMessage());
        }
    }


    /**
     * 退出登录
     * @return 结果
     */
    @ApiOperation(value = "退出登录")
    @PutMapping
    public R logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.ok(HttpStatus.SUCCESS,"退出成功 ！！！");
    }

    /**
     *
     * @param response 验证码
     * @throws IOException IO异常
     */

    @CrossOrigin
    @ApiOperation(value = "图形验证码")
    @GetMapping ("/imgCode")
    public void imgCode(HttpServletResponse response) throws IOException {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100,4,3);
        // 自定义纯数字的验证码（随机4位数字，可重复）
        captcha.setGenerator(new RandomGenerator("0123456789", 4));
        //存入redis
        stringRedisTemplate.opsForValue().set("verifyCode", captcha.getCode(), VerifyCodeVO.TIME, TimeUnit.MINUTES);
        //设置返回格式
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(outputStream);
        //关闭流
        outputStream.close();
    }

}

