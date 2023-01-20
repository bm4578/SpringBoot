package xyz.onlytype.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.onlytype.entity.SysUser;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author 白也
 * @since 2023-01-20 15:52:47
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     */
    Boolean register (String username , String password);

    /**
     * 用户登录
     * @param username 用户名
     */
    SysUser login(String username);
}

