package xyz.onlytype.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 白也
 * @title 用户所有信息
 * @date 2023/2/11 9:54 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户名称
    private String username;
    //角色名称
    private String roleName;
    //用户邮箱



}
