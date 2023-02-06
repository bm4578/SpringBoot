package xyz.onlytype.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.onlytype.security.Filter.LoginFailureFilter;
import xyz.onlytype.security.Filter.TokenAuthFilter;
import xyz.onlytype.security.Filter.UnAuthEntryPoint;
import xyz.onlytype.security.Filter.TokenLoginFilter;
import xyz.onlytype.security.token.TokenLogoutHandler;
import xyz.onlytype.security.token.TokenManager;
import xyz.onlytype.service.impl.SysUserImpl;

/**
 * @author 白也
 * @title
 * @date 2023/1/27 4:10 下午
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private TokenManager tokenManager;
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private SysUserImpl sysUser;

    @Autowired
    public SecurityConfig(TokenManager tokenManager, RedisTemplate<String,Object> redisTemplate){
        this.tokenManager = tokenManager;
        this.redisTemplate= redisTemplate;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置权限资源
     * @param http 访问
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.formLogin()
//                //登录失败时异常处理
                .failureHandler(new LoginFailureFilter())
                .and()
                .exceptionHandling()
                //无权限时处理方案
                .authenticationEntryPoint(new UnAuthEntryPoint())
                //支持跨域
                .and().cors()
                //关闭csrf
                .and().csrf().disable()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //设置接需要做接口认证
                .and().authorizeRequests()
                .antMatchers(  "/v3/**","/profile/**","/swagger-ui.html","/doc.html",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**","/swagger-ui/**","/v2/**","/favicon.ico","/webjars/springfox-swagger-ui/**","/static/**", "/webjars/**", "/v2/api-docs", "/v2/feign-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .anyRequest().authenticated()
                //注销路径
                .and().logout().logoutUrl("/api/user/logout")
                //注销处理器
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate))
                //处理登录
                .and().addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManager()))
                //处理权限
                .addFilter(new TokenAuthFilter(tokenManager, redisTemplate, authenticationManager()))
                .httpBasic();
    }

    /**
     * 获得用户名与密码
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUser);
    }
}
