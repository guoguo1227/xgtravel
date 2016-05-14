package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;
import java.util.Date;

public class MicroblogCollection implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer type;

    private Date createtime;

    private String phone;

    private Integer relateId;

    private Boolean isEnable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends MicroblogCollection> T copy(T bean) {
        bean.setId(getId());
        bean.setType(getType());
        bean.setCreatetime(getCreatetime());
        bean.setPhone(getPhone());
        bean.setRelateId(getRelateId());
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
        	", type:" + getType() + 
        	", createtime:" + getCreatetime() + 
        	", phone:" + getPhone() + 
        	", relateId:" + getRelateId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}