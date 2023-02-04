package xyz.onlytype.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.entity.SecurityUser;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.security.exception.CustomerAuthenionException;

import java.util.*;

/**
 * @author 白也
 * @title 用户注册
 * @date 2023/1/28 3:25 下午
 */
@Service("SysUserImpl")
public class SysUserImpl implements UserDetailsService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户异常
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //从数据库查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser user = sysUserDao.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new CustomerAuthenionException("用户不存在!!!");
        }
//        ReturnUserVo returnUserVo = new ReturnUserVo();
//        if (Objects.isNull(user)){
//            BeanUtils.copyProperties(user,returnUserVo);
//        }
        //根据用户名从数据库中查询角色信息
        List<String> roleByName = sysUserDao.findRoleByUserId(user.getUserId());
        //添加相应权限
        SecurityUser securityUser = new SecurityUser();
        securityUser.setSysUser(user);
        //设置相应权限列表
        // TODO 设置权限
        securityUser.setPermissionList(roleByName);
        return securityUser;
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱
     */
    public Boolean register(String username, String password, String email) {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        /**
         * 随机头像
         */
        ArrayList<String> list = new ArrayList<>();
        list.add("data:image/jpeg;base64,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");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOr92WYXk5zZr2MSoxGg5jGP-QQCUDTJGnag&usqp=CAU");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8LwoEAtiosOkAB1YhQpVA7cwBPNV4g5BNyA&usqp=CAU");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSyLyJoTwBWH_q93Fitw1YphdP89WahZoUxg&usqp=CAU");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ14WdvWBE05NlsE_oKYWo6YMcpNMWduZVN1w&usqp=CAU");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWTA8HjQQNit9TG8rk1R92Ou_RnLny5QqwzA&usqp=CAU");
        list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTa9GAH0Ju_ik4A_RBdpcH2SYTK6_ELW1BiQ&usqp=CAU");
        Random random = new Random();
        int i = random.nextInt(list.size());
        user.setIsImg(list.get(i));
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 邮箱存入数据库
        user.setIsEmail(email);
        return sysUserDao.insert(user) > 0;
    }
}
