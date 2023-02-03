package xyz.onlytype.entity;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.onlytype.vo.UserRoleVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 白也
 * @title
 * @date 2023/1/28 11:51 上午
 */
@Data
public class SecurityUser implements UserDetails {
    //当前登录的用户
    private SysUser sysUser;
    //存放权限列表
    private List<String> permissionList;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }


    /**
     * 获取权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission: permissionList){
            if (ObjectUtil.isNotEmpty(permission)){
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                authorities.add(simpleGrantedAuthority);
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sysUser.getUsername();
    }

    /**
     * 是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 是否锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 凭证是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
