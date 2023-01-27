package xyz.onlytype.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;
import xyz.onlytype.VO.user.UserRoleVO;
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
     * @param email  邮箱
     */
    Boolean register (String username , String password, String email);

    /**
     * 用户登录
     * @param username 用户名
     */
    SysUser login(String username);

    /**
     * 根据用户查询所有角色
     * @param username 用户名
     * @return 用户角色列表
     */

    List<UserRoleVO> findRolesByUserName(String username);

    /**
     * 发送邮箱信息
     * @param recipient 收件人
     * @param template 模板
     * @param message 信息
     */
    void sendMessage(String recipient,String template,String message);
}

