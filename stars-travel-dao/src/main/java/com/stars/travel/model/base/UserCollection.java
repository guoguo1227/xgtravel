package com.stars.travel.model.base;

import java.util.Date;

public class UserCollection implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date createtime;

    private String userPhone;

    private String collectionPhone;

    private Boolean isEnable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getCollectionPhone() {
        return collectionPhone;
    }

    public void setCollectionPhone(String collectionPhone) {
        this.collectionPhone = collectionPhone == null ? null : collectionPhone.trim();
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
    public  <T extends UserCollection> T copy(T bean) {
        bean.setId(getId());
        bean.setCreatetime(getCreatetime());
        bean.setUserPhone(getUserPhone());
        bean.setCollectionPhone(getCollectionPhone());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"id:" + getId() + 
        	", createtime:" + getCreatetime() + 
        	", userPhone:" + getUserPhone() + 
        	", collectionPhone:" + getCollectionPhone() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}