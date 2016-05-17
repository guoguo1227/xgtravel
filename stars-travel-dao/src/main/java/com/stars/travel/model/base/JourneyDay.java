package com.stars.travel.model.base;

import java.util.Date;

public class JourneyDay implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer journeyId;

    private Integer currentDay;

    private Date createtime;

    private Boolean isEnable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public Integer getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Integer currentDay) {
        this.currentDay = currentDay;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
    public  <T extends JourneyDay> T copy(T bean) {
        bean.setId(getId());
        bean.setJourneyId(getJourneyId());
        bean.setCurrentDay(getCurrentDay());
        bean.setCreatetime(getCreatetime());
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
        	", journeyId:" + getJourneyId() + 
        	", currentDay:" + getCurrentDay() + 
        	", createtime:" + getCreatetime() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}