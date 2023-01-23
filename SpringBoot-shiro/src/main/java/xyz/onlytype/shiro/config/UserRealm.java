package xyz.onlytype.shiro.config;


import cn.hutool.core.util.ObjectUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.onlytype.VO.UserRoleVO;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;

import java.util.List;

/**
 * @author 白也
 * @date 2023/1/19 13:19
 */

//自定义UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        List<UserRoleVO> rolesByUserName = sysUserService.findRolesByUserName(primaryPrincipal);
        //授权角色信息
        if (ObjectUtil.isNotEmpty(rolesByUserName)){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            rolesByUserName.forEach(role->{
                simpleAuthorizationInfo.addRole(role.getRoleName());
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录信息
        String principal = (String) authenticationToken.getPrincipal();
        //数据库查询用户
        SysUser user = sysUserService.login(principal);
        if (ObjectUtil.isNotEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getIsSalt()),this.getName());
        }
        return null;
    }
}
