package com.stars.travel.model.base;

public class UserRole implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private Boolean isEnable;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
    public  <T extends UserRole> T copy(T bean) {
        bean.setUserRoleId(getUserRoleId());
        bean.setUserId(getUserId());
        bean.setRoleId(getRoleId());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"userRoleId:" + getUserRoleId() + 
        	", userId:" + getUserId() + 
        	", roleId:" + getRoleId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}