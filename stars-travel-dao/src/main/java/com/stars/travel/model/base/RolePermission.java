package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;

public class RolePermission implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer rolePermissionId;

    private Integer roleId;

    private Integer permissionId;

    private Boolean isEnable;

    public Integer getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Integer rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends RolePermission> T copy(T bean) {
        bean.setRolePermissionId(getRolePermissionId());
        bean.setRoleId(getRoleId());
        bean.setPermissionId(getPermissionId());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"rolePermissionId:" + getRolePermissionId() + 
        	", roleId:" + getRoleId() + 
        	", permissionId:" + getPermissionId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}