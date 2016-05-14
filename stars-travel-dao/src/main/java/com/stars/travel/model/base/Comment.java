package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;
import java.util.Date;

public class Comment implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Boolean checked;

    private Date createtime;

    private Short downs;

    private String recommend;

    private Short ups;

    private String phone;

    private Integer relateId;

    private String type;

    private Boolean isEnable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Short getDowns() {
        return downs;
    }

    public void setDowns(Short downs) {
        this.downs = downs;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

    public Short getUps() {
        return ups;
    }

    public void setUps(Short ups) {
        this.ups = ups;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
    public  <T extends Comment> T copy(T bean) {
        bean.setId(getId());
        bean.setChecked(getChecked());
        bean.setCreatetime(getCreatetime());
        bean.setDowns(getDowns());
        bean.setRecommend(getRecommend());
        bean.setUps(getUps());
        bean.setPhone(getPhone());
        bean.setRelateId(getRelateId());
        bean.setType(getType());
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
        	", checked:" + getChecked() + 
        	", createtime:" + getCreatetime() + 
        	", downs:" + getDowns() + 
        	", recommend:" + getRecommend() + 
        	", ups:" + getUps() + 
        	", phone:" + getPhone() + 
        	", relateId:" + getRelateId() + 
        	", type:" + getType() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}