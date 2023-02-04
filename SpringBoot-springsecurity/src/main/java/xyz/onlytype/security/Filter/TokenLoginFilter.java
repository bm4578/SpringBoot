package xyz.onlytype.security.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.onlytype.entity.SecurityUser;
import xyz.onlytype.config.utils.ResponseUtils;
import xyz.onlytype.config.utils.ResultModel;
import xyz.onlytype.security.exception.CustomerAuthenionException;
import xyz.onlytype.security.token.TokenManager;
import xyz.onlytype.vo.ReturnUserVo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 白也
 * @title 登录拦截
 * @date 2023/1/28 11:11 上午
 */

@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate<String, Object> redisTemplate;
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        //允许其它请求认证
        this.setPostOnly(false);
        //设置登录路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/user/login", "POST"));
    }

    /**
     * 执行认证的方法
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            //从表单中获取用户信息
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getParameter("username"),
                            request.getParameter("password")
                            , new ArrayList<>())
            );
        } catch (AuthenticationException e) {
           throw new CustomerAuthenionException(e.getMessage());
        }
    }

    /**
     * 认证成功之后
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            //为空时生成token
            SecurityUser principal = (SecurityUser) authResult.getPrincipal();
            String token = tokenManager.getToken(principal.getUsername());
            ReturnUserVo returnUserVo = new ReturnUserVo();
            returnUserVo.setToken(token);
            returnUserVo.setPermission(principal.getPermissionList());
            //存入redis
            redisTemplate.opsForValue().set("token", returnUserVo, tokenManager.getRemainingTime(token), TimeUnit.MINUTES);
            // 返回生成的token
            ResponseUtils.out(response, ResultModel.ok(token));
        } catch (Exception e) {
            ResultModel.error("token生成异常", e.getMessage());
        }
    }

    /**
     * 认证失败
     *
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtils.out(response, ResultModel.error(401, failed.getMessage()));
    }
}
