package com.zmq.filmsystem.service.impl;

import com.zmq.filmsystem.dao.RoleMapper;
import com.zmq.filmsystem.entity.Role;
import com.zmq.filmsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findById(Integer roleId) {
        return roleMapper.findById(roleId);
    }
}
