package xyz.onlytype.config;


import cn.hutool.core.util.ObjectUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;

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
        System.out.println("执行了授权");
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
