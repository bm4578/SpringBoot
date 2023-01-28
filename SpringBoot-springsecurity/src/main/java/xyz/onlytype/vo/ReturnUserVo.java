package xyz.onlytype.vo;

import java.io.Serializable;

/**
 * @author 白也
 * @title
 * @date 2023/1/28 1:24 下午
 */
public class ReturnUserVo implements Serializable {
    //用户 ID
    private Long userId;
    //系统登录名
    private String username;
    //登录密码，加密存储, admin/1234
    private String password;
    //token
    private String token;
}
