package xyz.onlytype.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author 白也
 */
@Configuration
public class ShiroConfig {

    /**
     * 3. ShiroFilterFactoryBean
     * 负责拦截请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置访问资源
        HashMap<String, String> map = new HashMap<>();
        //authc：表示请求这个资源需要认证和授权
        //anon：表示所有用户均可访问
//        map.put("/register","anon");
//        map.put("/sysUser","authc");
        map.put("/user","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //默认认证界面
        shiroFilterFactoryBean.setLoginUrl("/doc.html");
        return shiroFilterFactoryBean;
    }
    //

    /**
     * 2. DafaultwebSecurityManager
     *  创建安全管理器
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager( UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置Realm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 1. 创建 Realm 对象
     */
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
       //设置加密算法与散列次数
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(credentialsMatcher);
//        //开启缓存管理
//        userRealm.setCacheManager(new EhCacheManager());
//        //全局缓存管理
//        userRealm.setCachingEnabled(true);
//        //认证
//        userRealm.setAuthenticationCachingEnabled(true);
//        userRealm.setAuthenticationCacheName("AuthenticationCache");
//        //授权
//        userRealm.setAuthorizationCachingEnabled(true);
//        userRealm.setAuthorizationCacheName("AuthorizationCache");
        return userRealm;
    }
}
