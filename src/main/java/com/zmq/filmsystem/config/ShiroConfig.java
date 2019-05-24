package com.zmq.filmsystem.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zmq.filmsystem.realm.LoginRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description shiro相关配置
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * @Description 凭证匹配器
     * @Param []
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     **/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // MD5加密一次
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * @Description 自定义realm
     * @Param []
     * @return LoginRealm
     **/
    @Bean
    public LoginRealm loginRealm() {

        LoginRealm loginRealm = new LoginRealm();
        loginRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return loginRealm;
    }

    /**
     * @Description 安全管理器
     * @Param []
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     **/
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(loginRealm());
        return securityManager;
    }

    /**
     * @Description 生命周期
     * @Param []
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     **/
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @Description 启用注解
     * @Param []
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     **/
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor as=new AuthorizationAttributeSourceAdvisor();
        as.setSecurityManager(securityManager());
        return as;
    }

    /**
     * @Description 配置过滤规则
     * @Param []
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        /*定义shiro过滤链 Map结构 * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
         * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
         * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 需要管理员权限才能访问这些路径
        filterChainDefinitionMap.put("/admin/**", "authc,roles[admin]");
        // 需要商家权限
        filterChainDefinitionMap.put("/business/**", "authc,roles[business]");
        // 需要顾客权限
        filterChainDefinitionMap.put("/customer/**", "authc,roles[customer]");
        // 登陆可以不需要认证
        filterChainDefinitionMap.put("/login", "anon");
        // 注册可以不需要认证
        filterChainDefinitionMap.put("/register", "anon");

        //filterChainDefinitionMap.put("/logout", "logout");
        // 静态资源不需要认证
        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/images/**", "anon");
//        filterChainDefinitionMap.put("/fonts/**", "anon");
        // 除了上面额 /login 可以匿名访问，其他路径都需要登录访问
        // 如果没登录，就访问其他路径会跳转到 /login 登录
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @Description 配置方言标签，在thymeleaf中使用shiro标签
     * @Param []
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     **/
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
