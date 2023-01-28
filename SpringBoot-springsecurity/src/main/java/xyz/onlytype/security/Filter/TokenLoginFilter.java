package xyz.onlytype.security.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.onlytype.entity.SecurityUser;
import xyz.onlytype.entity.SysUser;
import xyz.onlytype.security.config.utils.ResponseUtils;
import xyz.onlytype.security.config.utils.ResultModel;
import xyz.onlytype.security.token.TokenManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 白也
 * @title 登录拦截
 * @date 2023/1/28 11:11 上午
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 执行认证的方法
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //获取用户提交的数据
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SysUser user = objectMapper.readValue(request.getInputStream(), SysUser.class);
            //校验密码
           return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()
                    ,new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.attemptAuthentication(request, response);
    }

    /**
     *
     * 认证成功之后
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     *                   method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //获得用户信息
        SecurityUser authorities = (SecurityUser) authResult.getAuthorities();
        //issuer ：持有人
        String issuer = authorities.getUsername();
        //权限信息
        Map<String, Object> permissionMap = new HashMap<>();
        permissionMap.put("permission",authorities.getPermissionList());
        //生成token
        String token = tokenManager.getToken(authorities.getSysUser().getUserId(), issuer, permissionMap);
        //存入redis ==>issuer:权限信息
        redisTemplate.opsForValue().set(issuer,authorities.getPermissionList());
        //返回数据
        ResponseUtils.out(response, ResultModel.ok(token));
    }

    /**
     * 认证失败
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtils.out(response,ResultModel.error(401,failed.getMessage()));
    }
}
