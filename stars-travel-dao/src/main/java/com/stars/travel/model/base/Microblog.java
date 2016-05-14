package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;
import java.util.Date;

public class Microblog implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String phone;

    private String picture;

    private String funPicture;

    private String foodPicture;

    private String sceneryPicture;

    private String newnessPicture;

    private Date createtime;

    private Date updatetime;

    private Boolean isEnable;

    private Boolean isShared;

    private Integer top;

    private Short status;

    private String title;

    private String destination;

    private String destDescription;

    private String novelDescription;

    private String endDescription;

    private String sceneryDescription;

    private Integer sharetimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getFunPicture() {
        return funPicture;
    }

    public void setFunPicture(String funPicture) {
        this.funPicture = funPicture == null ? null : funPicture.trim();
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture == null ? null : foodPicture.trim();
    }

    public String getSceneryPicture() {
        return sceneryPicture;
    }

    public void setSceneryPicture(String sceneryPicture) {
        this.sceneryPicture = sceneryPicture == null ? null : sceneryPicture.trim();
    }

    public String getNewnessPicture() {
        return newnessPicture;
    }

    public void setNewnessPicture(String newnessPicture) {
        this.newnessPicture = newnessPicture == null ? null : newnessPicture.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public String getDestDescription() {
        return destDescription;
    }

    public void setDestDescription(String destDescription) {
        this.destDescription = destDescription == null ? null : destDescription.trim();
    }

    public String getNovelDescription() {
        return novelDescription;
    }

    public void setNovelDescription(String novelDescription) {
        this.novelDescription = novelDescription == null ? null : novelDescription.trim();
    }

    public String getEndDescription() {
        return endDescription;
    }

    public void setEndDescription(String endDescription) {
        this.endDescription = endDescription == null ? null : endDescription.trim();
    }

    public String getSceneryDescription() {
        return sceneryDescription;
    }

    public void setSceneryDescription(String sceneryDescription) {
        this.sceneryDescription = sceneryDescription == null ? null : sceneryDescription.trim();
    }

    public Integer getSharetimes() {
        return sharetimes;
    }

    public void setSharetimes(Integer sharetimes) {
        this.sharetimes = sharetimes;
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends Microblog> T copy(T bean) {
        bean.setId(getId());
        bean.setPhone(getPhone());
        bean.setPicture(getPicture());
        bean.setFunPicture(getFunPicture());
        bean.setFoodPicture(getFoodPicture());
        bean.setSceneryPicture(getSceneryPicture());
        bean.setNewnessPicture(getNewnessPicture());
        bean.setCreatetime(getCreatetime());
        bean.setUpdatetime(getUpdatetime());
        bean.setIsEnable(getIsEnable());
        bean.setIsShared(getIsShared());
        bean.setTop(getTop());
        bean.setStatus(getStatus());
        bean.setTitle(getTitle());
        bean.setDestination(getDestination());
        bean.setDestDescription(getDestDescription());
        bean.setNovelDescription(getNovelDescription());
        bean.setEndDescription(getEndDescription());
        bean.setSceneryDescription(getSceneryDescription());
        bean.setSharetimes(getSharetimes());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"id:" + getId() + 
        	", phone:" + getPhone() + 
        	", picture:" + getPicture() + 
        	", funPicture:" + getFunPicture() + 
        	", foodPicture:" + getFoodPicture() + 
        	", sceneryPicture:" + getSceneryPicture() + 
        	", newnessPicture:" + getNewnessPicture() + 
        	", createtime:" + getCreatetime() + 
        	", updatetime:" + getUpdatetime() + 
        	", isEnable:" + getIsEnable() + 
        	", isShared:" + getIsShared() + 
        	", top:" + getTop() + 
        	", status:" + getStatus() + 
        	", title:" + getTitle() + 
        	", destination:" + getDestination() + 
        	", destDescription:" + getDestDescription() + 
        	", novelDescription:" + getNovelDescription() + 
        	", endDescription:" + getEndDescription() + 
        	", sceneryDescription:" + getSceneryDescription() + 
        	", sharetimes:" + getSharetimes() + 
        "}";
    }
}