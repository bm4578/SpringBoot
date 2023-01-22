package xyz.onlytype.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.onlytype.VO.UserRoleVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private static final long serialVersionUID = 1L;
    //用户 ID
    private Long userId;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date createTime = new Date();
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date updateTime = new Date();
}

