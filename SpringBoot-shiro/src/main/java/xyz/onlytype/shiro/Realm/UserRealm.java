package xyz.onlytype.shiro.Realm;


import cn.hutool.core.util.ObjectUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.onlytype.VO.user.UserRoleVO;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.service.SysUserService;
import java.util.List;

/**
 * @author 白也
 * @date 2023/1/19 13:19
 * 自定义UserRealm
 */

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;

    public UserRealm(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        //指定生效
        setCredentialsMatcher(hashedCredentialsMatcher);
    }
    /**
     * 用户认证
     * @param token token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token令牌信息
        String principal = (String) token.getPrincipal();
        //数据库查询用户
        SysUser user = sysUserService.login(principal);
        //构建认证信息
        if (ObjectUtil.isNotEmpty(user)){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getIsSalt()),this.getName());
        }
        return null;
    }

    /**
     * 用户授权
     */
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
}
