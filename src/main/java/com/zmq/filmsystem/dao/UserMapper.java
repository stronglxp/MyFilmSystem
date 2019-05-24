package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * @Description 调用sql查询用户
     * @Param [username]
     * @return User
     **/
    User findByName(String username);

    /**
     * @Description 增加一个用户
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
