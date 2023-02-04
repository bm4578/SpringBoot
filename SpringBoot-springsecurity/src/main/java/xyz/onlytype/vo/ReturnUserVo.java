package xyz.onlytype.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 白也
 * @titlen 返回信息
 * @date 2023/1/28 1:24 下午
 */
@Data
public class ReturnUserVo implements Serializable {
    //token
    private String token;
    //权限信息
    private List<String> permission;
}
