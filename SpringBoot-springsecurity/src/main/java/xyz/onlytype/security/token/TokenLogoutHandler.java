package xyz.onlytype.security.token;

import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import xyz.onlytype.config.utils.ResponseUtils;
import xyz.onlytype.config.utils.ResultModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 白也
 * @title 注销登录
 * @date 2023/1/28 12:11 上午
 */

public class TokenLogoutHandler implements LogoutHandler {
    private TokenManager tokenManager;
    private RedisTemplate<String,Object> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String,Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 注销时执行的业务
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    //从前端获取token
        String token = request.getHeader("token");
        if (StringUtil.isNullOrEmpty(token)){
            //解析token
            Claims claimsFromToken = tokenManager.getClaimsFromToken(token);
            //获取持有人
            String issuer = claimsFromToken.getIssuer();
            redisTemplate.delete(issuer);
        }
        ResponseUtils.out(response, ResultModel.ok("注销成功"));
    }
}
