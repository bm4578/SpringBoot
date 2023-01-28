package xyz.onlytype.VO.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户是否启用
 * @author 白也
 * @date 2023/1/24 12:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEnabledVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    private String isEmail;
    /**
     * 确认码
     */
    private String confirmationCode;
    /**
     * 帐户是否可用(1 可用，0 不可用)
     */
    private Integer isEnabled;
    /**
     * 激活失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date activationTime = new Date();

}
