package xyz.onlytype.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(SysUser)表实体类
 *
 * @author 白也
 * @since 2023-01-20 15:52:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
    //用户 ID
    private Long id;
    //与博客关联
    private String isArticle;
    //系统登录名
    private String username;
    //登录密码，加密存储, admin/1234
    private String password;
    //加密盐
    private String isSalt;
    //头像
    private String isImg;
    //权限字段0/管理员，1/普通用户
    private Integer isPermission;
    //帐户是否可用(1 可用，0 删除用户)
    private Integer isEnabled;
    //创建时间
    private Date createTime = new Date();
    //更新时间
    private Date updateTime = new Date();
}

