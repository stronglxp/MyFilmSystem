package com.zmq.filmsystem.dao;

import com.zmq.filmsystem.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    /**
     * @Description 通过id查找role
     * @Param [roleId]
     * @return Role
     **/
    Role findById(Integer roleId);
}
