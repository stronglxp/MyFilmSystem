package com.zmq.filmsystem.realm;

import com.zmq.filmsystem.entity.Role;
import com.zmq.filmsystem.entity.User;
import com.zmq.filmsystem.service.RoleService;
import com.zmq.filmsystem.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description shiro登陆认证
 * @Version 1.0
 */
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(LoginRealm.class);

    @Autowired
    private RoleService roleService;

    /**
     * @Description 获取身份信息，当进行身份验证的时候调用
     * @Param [principals]
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 获取用户名
        String username = (String)getAvailablePrincipal(principals);

        SimpleAuthorizationInfo info = null;
        // 获取用户名对应的用户的信息
        User user = userService.findByName(username);
        if(user != null) {
            // 获取角色信息
            Role role = roleService.findById(user.getRoleId());
            info = new SimpleAuthorizationInfo();
            Set<String> r = new HashSet<>();
            if(role != null) {
                r.add(role.getRoleName());
                info.setRoles(r);
            }
        }

        return info;
    }

    /**
     * @Description 进行身份验证，login时调用
     * @Param [token]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        // 用户名
        String username = (String) token.getPrincipal();

        User user = null;
        // 通过用户名获取用户信息
        user = userService.findByName(username);

        logger.info("user = " + user);
        // 用户名不存在则抛出异常
        if(user == null) {
            throw new UnknownAccountException("用户不存在!");
        }

        //身份验证通过,返回一个身份信息
        AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getUserPassword(),
                ByteSource.Util.bytes(user.getUserSalt()), getName());
        logger.info("info = " + info);

        return info;
    }
}
