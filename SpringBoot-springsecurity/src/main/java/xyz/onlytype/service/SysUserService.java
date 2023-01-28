package xyz.onlytype.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.onlytype.entity.SysUser;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author 白也
 * @since 2023-01-28 11:23:37
 */
public interface SysUserService extends IService<SysUser> {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}

