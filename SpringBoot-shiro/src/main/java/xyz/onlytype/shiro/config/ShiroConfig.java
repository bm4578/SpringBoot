package xyz.onlytype.shiro.config;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.onlytype.shiro.Realm.UserRealm;
import java.util.HashMap;

/**
 * @author 白也
 */
@Configuration
public class ShiroConfig {

    /**
     * 负责拦截请求
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
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


    /**
     *  安全管理器
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置Realm
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    /**
     *  自定义Realm 对象
     */
    @Bean("userRealm")
    public UserRealm userRealm(){

//        userRealm.setCredentialsMatcher(credentialsMatcher);
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
        return new UserRealm();
    }
}
