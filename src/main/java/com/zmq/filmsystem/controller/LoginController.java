package com.zmq.filmsystem.controller;

import com.zmq.filmsystem.entity.User;
import com.zmq.filmsystem.service.UserService;
import com.zmq.filmsystem.util.DataUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 登录的逻辑
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 获取登录页面
     * @return
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    /**
     * 注册请求
     * @param model
     * @param user
     * @return
     */
    @PostMapping("/register")
    @Transactional
    public String userRegister(Model model, User user) {

        // 获取用户输入的注册信息
        String username = user.getUserName();

        // 判断用户名是否重复
        User u = userService.findByName(username);
        // 存在重复用户名
        if(u != null) {
            model.addAttribute("msg", "用户名存在!");
            return "login";
        }

        String password = user.getUserPassword();

        // 生成盐
        String salt = DataUtils.getSalt();
        // 密码加密
        user.setUserSalt(salt);
        user.setUserPassword(DataUtils.getMD5Str(password, salt));

        Integer num = userService.addUser(user);

        if(num != 1) {
            model.addAttribute("msg", "注册失败!");
            return "login";
        }

        return "login";
    }

    /**
     * 处理登陆
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(User user, Model model) {
        // 获取用户输入的用户名和密码
        String username = user.getUserName();
        String password = user.getUserPassword();

        // shiro实现登陆
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            // 如果获取不到用户名，则登陆失败，会抛出异常
            subject.login(token);
            logger.info("subject = " + subject);
            // 管理员
            if(subject.hasRole("admin")) {
                //return "redirect:/admin/student/list";
            } else if(subject.hasRole("business")) {
                // 商家
                return "redirect:/business/film/list";
            } else if(subject.hasRole("customer")) {
                // 顾客
                return "redirect:/customer/order/list";
            }
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("msg", "用户名不存在!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "密码错误!");
        }

        // 返回登录界面
        return "login";
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
