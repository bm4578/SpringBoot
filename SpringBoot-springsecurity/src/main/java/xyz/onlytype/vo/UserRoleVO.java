package xyz.onlytype.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 白也
 * @title 封装用户权限信息
 * @date 2023/1/28 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户id
    private String userId;
    //系统登录名
    private String username;
    //角色id
    private String roleId;
    //角色名称
    private String roleName;
}
