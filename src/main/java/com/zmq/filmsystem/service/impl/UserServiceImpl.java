package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.UserMapper;
import com.zmq.filmsystem.entity.User;
import com.zmq.filmsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User getUserInfo(String userName) {
        return userMapper.getUserInfo(userName);
    }

    @Override
    public Integer updateCanema(User user) {
        return userMapper.updateCanema(user);
    }
}
