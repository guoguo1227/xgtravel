package com.stars.travel.model.base;

import java.util.Date;

public class JourneyItem implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer journeyDayId;

    private String itemtype;

    private Date createtime;

    private Date starttime;

    private Date endtime;

    private String title;

    private String description;

    private Integer budget;

    private Boolean isEnable;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJourneyDayId() {
        return journeyDayId;
    }

    public void setJourneyDayId(Integer journeyDayId) {
        this.journeyDayId = journeyDayId;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype == null ? null : itemtype.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends JourneyItem> T copy(T bean) {
        bean.setId(getId());
        bean.setJourneyDayId(getJourneyDayId());
        bean.setItemtype(getItemtype());
        bean.setCreatetime(getCreatetime());
        bean.setStarttime(getStarttime());
        bean.setEndtime(getEndtime());
        bean.setTitle(getTitle());
        bean.setDescription(getDescription());
        bean.setBudget(getBudget());
        bean.setIsEnable(getIsEnable());
        bean.setContent(getContent());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"id:" + getId() + 
        	", journeyDayId:" + getJourneyDayId() + 
        	", itemtype:" + getItemtype() + 
        	", createtime:" + getCreatetime() + 
        	", starttime:" + getStarttime() + 
        	", endtime:" + getEndtime() + 
        	", title:" + getTitle() + 
        	", description:" + getDescription() + 
        	", budget:" + getBudget() + 
        	", isEnable:" + getIsEnable() + 
        	", content:" + getContent() + 
        "}";
    }
}