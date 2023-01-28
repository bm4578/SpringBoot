package xyz.onlytype.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.ObjectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;
import org.springframework.stereotype.Service;
import xyz.onlytype.vo.ReturnUserVo;

import java.util.Objects;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 白也
 * @since 2023-01-28 11:23:37
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername,username);
        SysUser user = sysUserDao.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("当前用户不存在");
        }

//        ReturnUserVo returnUserVo = new ReturnUserVo();
//        if (Objects.isNull(user)){
//            BeanUtils.copyProperties(user,returnUserVo);
//        }
        //根据用户名从数据库中查询权限信息 P41:10:40


        return null;
    }
}

