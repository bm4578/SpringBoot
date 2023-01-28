package xyz.onlytype.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.onlytype.security.Filter.TokenAuthFilter;
import xyz.onlytype.security.UnAuthEntryPoint;
import xyz.onlytype.security.Filter.TokenLoginFilter;
import xyz.onlytype.security.token.TokenLogoutHandler;
import xyz.onlytype.security.token.TokenManager;

/**
 * @author 白也
 * @title
 * @date 2023/1/27 4:10 下午
 */
@Configuration
//启用Spring Security
@EnableWebSecurity
//请求之前进行授权认证
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplatel;
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(TokenManager tokenManager, RedisTemplate redisTemplate,UserDetailsService userDetailsService){
        this.tokenManager = tokenManager;
        this.redisTemplatel = redisTemplate;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 配置权限资源
     * @param http 访问
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.exceptionHandling()
                //无权限时处理方案
                .authenticationEntryPoint(new UnAuthEntryPoint())
                //关闭csrf
                .and().csrf().disable()
                //设置接需要做接口认证
                .authorizeRequests().anyRequest().authenticated()
                //注销路径
                .and().logout().logoutUrl("/api/user/logout")
                //注销处理器
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplatel))
                //处理登录
                .and().addFilter(new TokenLoginFilter(tokenManager, redisTemplatel, authenticationManager()))
                //处理权限
                .addFilter(new TokenAuthFilter(tokenManager, redisTemplatel, authenticationManager()))
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 获得用户名与密码
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
