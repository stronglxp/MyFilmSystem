package com.zmq.filmsystem.service;

import com.zmq.filmsystem.entity.User;

public interface UserService {

    /**
     * @Description 根据用户名查找用户
     * @Param [name]
     * @return User
     **/
    User findByName(String name);

    /**
     * @Description 添加用户
     * @Param [user]
     * @return int
     **/
    int addUser(User user);

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    User getUserInfo(String userName);

    /**
     * 更新用户关联的影视信息
     * @param user
     * @return
     */
    Integer updateCanema(User user);
}
