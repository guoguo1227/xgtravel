package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;

public class Permission implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer permissionId;

    private String description;

    private String permission;

    private String permissionName;

    private String parentId;

    private Boolean isEnable;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
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
    public  <T extends Permission> T copy(T bean) {
        bean.setPermissionId(getPermissionId());
        bean.setDescription(getDescription());
        bean.setPermission(getPermission());
        bean.setPermissionName(getPermissionName());
        bean.setParentId(getParentId());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"permissionId:" + getPermissionId() + 
        	", description:" + getDescription() + 
        	", permission:" + getPermission() + 
        	", permissionName:" + getPermissionName() + 
        	", parentId:" + getParentId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}