package com.zmq.filmsystem.entity;

/**
 * @Description 角色表对应的实体类
 * @Param
 * @return
 **/
public class Role {
    // 角色id
    private Integer roleId;
    // 角色名称
    private String roleName;
    // 权限
    private String permissions;

    public Role() {}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }
}