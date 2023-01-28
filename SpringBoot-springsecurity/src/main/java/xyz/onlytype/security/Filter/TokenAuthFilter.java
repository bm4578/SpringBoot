package xyz.onlytype.security.Filter;

import io.jsonwebtoken.Claims;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
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
    private RedisTemplate redisTemplate;

    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public TokenAuthFilter(TokenManager tokenManager,RedisTemplate redisTemplate,AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    /**
     * 权限相关操作
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
        //获取token
        String token = request.getHeader("token");
        if(!StringUtil.isNullOrEmpty(token)){
            Claims claimsFromToken = tokenManager.getClaimsFromToken(token);
            String issuer = claimsFromToken.getIssuer();
            //从redis获取权限token
            List<String> permissionList = (List<String>) redisTemplate.opsForValue().get(issuer);
            //将取出的权限信息存入上下文之中，方便系统知道用户具有哪些权限
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (! CollectionUtils.isEmpty(permissionList)){
                for (String permission :permissionList){
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                    authorities.add(simpleGrantedAuthority);
                }
            }
            //生成权限信息对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(issuer,token,authorities);
            //存入权限上下文之中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        //放行
        chain.doFilter(request,response);
    }
}
