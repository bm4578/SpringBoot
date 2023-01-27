package xyz.onlytype.VO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装用户权限信息
 * @author 白也
 * @date 2023/1/22 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户id
    private Long userId;
    //系统登录名
    private String username;
    //角色id
    private Long roleId;
    //角色名称
    private String roleName;
}
