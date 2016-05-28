package com.stars.travel.model.base;

import java.util.Date;

public class JourneyCollection extends JourneyCollectionKey implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer type;

    private Date createtime;

    private String phone;

    private Integer relateId;

    private Boolean isEnable;

    private Date updatetime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends JourneyCollection> T copy(T bean) {
        bean.setType(getType());
        bean.setCreatetime(getCreatetime());
        bean.setPhone(getPhone());
        bean.setRelateId(getRelateId());
        bean.setIsEnable(getIsEnable());
        bean.setUpdatetime(getUpdatetime());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"type:" + getType() + 
        	", createtime:" + getCreatetime() + 
        	", phone:" + getPhone() + 
        	", relateId:" + getRelateId() + 
        	", isEnable:" + getIsEnable() + 
        	", updatetime:" + getUpdatetime() + 
        "}";
    }
}