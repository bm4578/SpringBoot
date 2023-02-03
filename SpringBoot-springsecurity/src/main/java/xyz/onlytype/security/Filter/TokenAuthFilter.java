package xyz.onlytype.security.Filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import xyz.onlytype.security.exception.CustomerAuthenionException;
import xyz.onlytype.security.token.TokenManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 白也
 * @title 权限过滤器
 * @date 2023/1/28 12:36 下午
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate<String, Object> redisTemplate;
    private AuthenticationManager authenticationManager;

    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public TokenAuthFilter(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    /**
     * 权限相关操作
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //不需要token验证
        if (StringUtils.contains(request.getServletPath(), "swagger")
                || StringUtils.contains(request.getServletPath(), "webjars")
                || StringUtils.contains(request.getServletPath(), "/api/article/list")
                || StringUtils.contains(request.getServletPath(), "/files")
                || StringUtils.contains(request.getServletPath(), "doc")
                || StringUtils.contains(request.getServletPath(), "v3")
                || StringUtils.contains(request.getServletPath(), "profile")
                || StringUtils.contains(request.getServletPath(), "swagger-ui")
                || StringUtils.contains(request.getServletPath(), "swagger-resources")
                || StringUtils.contains(request.getServletPath(), "csrf")
                || StringUtils.contains(request.getServletPath(), "favicon")
                || StringUtils.contains(request.getServletPath(), "v2")
                || StringUtils.contains(request.getServletPath(), "/api/user/login")
        ) {
            chain.doFilter(request, response);
        } else {
            //从请求的头部获取token
            String token = request.getHeader("token");
            //如果请求头部没有获取到token，则从请求参数中获取token
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter("token");
            }
            if (StringUtils.isEmpty(token)) {
                throw new CustomerAuthenionException("token不存在！");
            }
            //判断token是否过期
            if (tokenManager.isTokenExpired(token)) {
                throw new CustomerAuthenionException("token过期");
            }
            // TODO 添加权限信息到上下文
            //解析token
            String userName = tokenManager.getUserName(token);
            List<String> permissionList = (List<String>) redisTemplate.opsForValue().get(token);
            //将取出的权限信息存入上下文之中，方便系统知道用户具有哪些权限
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(permissionList)) {
                for (String permission : permissionList) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                    authorities.add(simpleGrantedAuthority);
                }
            }
            //生成权限信息对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, token, authorities);
            //存入权限上下文之中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放行
            chain.doFilter(request, response);
        }
    }

}
