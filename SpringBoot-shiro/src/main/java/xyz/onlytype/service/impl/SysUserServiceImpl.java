package xyz.onlytype.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import xyz.onlytype.VO.user.UserRoleVO;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 白也
 * @since 2023-01-20 15:52:47
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Value("${spring.mail.username}")
    // 邮件发送人
    private String sender;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *  注册账号
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public Boolean register(String username , String password,String email) {
        // 1. 生成随机盐，并存入数据库
        String salt = RandomUtil.randomStringUpper(10);
        SysUser user = new SysUser();
        user.setIsSalt(salt);
        // 2. 密码md5+salt + hash散列
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        user.setPassword(md5Hash.toHex());
        user.setUsername(username);
        // 3. 邮箱存入数据库
        user.setIsEmail(email);
        // 存入数据库
        return userDao.insert(user) > 0;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     */
    @Override
    public SysUser login(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userDao.selectOne(wrapper);
    }

    /**
     * 根据用户查询所有角色
     *
     * @param username 用户名
     * @return 用户角色列表
     */
    @Cacheable(value = "test",key = "#username")
    @Override
    public List<UserRoleVO> findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }

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
