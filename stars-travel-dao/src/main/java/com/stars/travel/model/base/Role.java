package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;

public class Role implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Boolean isSuper;

    private Integer priority;

    private String roleName;

    private Integer siteId;

    private Boolean isEnable;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Boolean isSuper) {
        this.isSuper = isSuper;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
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
    public  <T extends Role> T copy(T bean) {
        bean.setRoleId(getRoleId());
        bean.setIsSuper(getIsSuper());
        bean.setPriority(getPriority());
        bean.setRoleName(getRoleName());
        bean.setSiteId(getSiteId());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"roleId:" + getRoleId() + 
        	", isSuper:" + getIsSuper() + 
        	", priority:" + getPriority() + 
        	", roleName:" + getRoleName() + 
        	", siteId:" + getSiteId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}