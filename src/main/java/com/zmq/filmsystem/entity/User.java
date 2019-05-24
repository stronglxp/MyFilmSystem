package com.zmq.filmsystem.entity;

/**
 * @Description 用户表对应的实体类
 * @Param
 * @return
 **/
public class User {
    // 用户id
    private Integer userId;
    // 用户名称
    private String userName;
    // 盐值
    private String userSalt;
    // 用户密码
    private String userPassword;
    // 角色id
    private Integer roleId;
    // 影院id
    private Integer canemaId;

    public User() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCanemaId() {
        return canemaId;
    }

    public void setCanemaId(Integer canemaId) {
        this.canemaId = canemaId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSalt='" + userSalt + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", roleId=" + roleId +
                ", canemaId=" + canemaId +
                '}';
    }
}