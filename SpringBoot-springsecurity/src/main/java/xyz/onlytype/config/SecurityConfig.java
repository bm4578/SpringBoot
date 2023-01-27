package xyz.onlytype.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xyz.onlytype.security.UnAuthEntryPoint;

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
        .addLogoutHandler(new TokenLogoutHandler());


    }
}
