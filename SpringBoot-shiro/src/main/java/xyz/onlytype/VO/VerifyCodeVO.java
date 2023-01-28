package xyz.onlytype.VO;

import lombok.Data;

/**
 * @author 白也
 * @date 2023/1/23 17:02
 */
@Data
public class VerifyCodeVO {
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer TIME = 1;
    /**
     * 验证码
     */
    private String verifyCode;
    /**
     * 剩余时间
     */
    private Integer timeout;
}