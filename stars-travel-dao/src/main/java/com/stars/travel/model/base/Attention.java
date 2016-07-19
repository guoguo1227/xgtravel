package com.stars.travel.model.base;

import java.util.Date;

public class Attention extends AttentionKey implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Short type;

    private Integer relateId;

    private String relatePhone;

    private String currentPhone;

    private Date updatetime;

    private Date createtime;

    private Boolean isDelete;

    private Short status;

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
    }

    public String getRelatePhone() {
        return relatePhone;
    }

    public void setRelatePhone(String relatePhone) {
        this.relatePhone = relatePhone == null ? null : relatePhone.trim();
    }

    public String getCurrentPhone() {
        return currentPhone;
    }

    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone == null ? null : currentPhone.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends Attention> T copy(T bean) {
        bean.setType(getType());
        bean.setRelateId(getRelateId());
        bean.setRelatePhone(getRelatePhone());
        bean.setCurrentPhone(getCurrentPhone());
        bean.setUpdatetime(getUpdatetime());
        bean.setCreatetime(getCreatetime());
        bean.setIsDelete(getIsDelete());
        bean.setStatus(getStatus());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"type:" + getType() + 
        	", relateId:" + getRelateId() + 
        	", relatePhone:" + getRelatePhone() + 
        	", currentPhone:" + getCurrentPhone() + 
        	", updatetime:" + getUpdatetime() + 
        	", createtime:" + getCreatetime() + 
        	", isDelete:" + getIsDelete() + 
        	", status:" + getStatus() + 
        "}";
    }
}