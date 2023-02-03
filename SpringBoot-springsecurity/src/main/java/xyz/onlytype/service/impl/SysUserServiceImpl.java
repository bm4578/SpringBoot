package xyz.onlytype.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.ObjectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.entity.SecurityUser;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;
import org.springframework.stereotype.Service;
import xyz.onlytype.vo.ReturnUserVo;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 白也
 * @since 2023-01-28 11:23:37
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService{
    @Value("${spring.mail.username}")
    // 邮件发送人
    private String sender;
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮箱信息
     *
     * @param recipient 收件人
     * @param template  模板
     * @param message   信息
     */
    @Override
    public void sendMessage(String recipient, String template, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发件人
        simpleMailMessage.setFrom(sender);
        //收件人
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(template);
        simpleMailMessage.setText(message);
        //发送邮件
        mailSender.send(simpleMailMessage);
    }


}

