package xyz.onlytype.security.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 白也
 * @title
 * @date 2023/1/27 11:32 下午
 */

@Component
public class TokenManager {
    //设置token私钥
    @Value("{token.secret}")
    private String secret;

}
