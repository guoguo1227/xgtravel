package com.stars.travel.model.base;

import java.util.Date;

public class Customization implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String phone;

    private Date createtime;

    private Date updatetime;

    private String traveltime;

    private Boolean isEnable;

    private Boolean isShared;

    private Short status;

    private String city;

    private String outTraffic;

    private String innerTraffic;

    private String hotel;

    private String hotelFactors;

    private String featureHobby;

    private String foodHobby;

    private String remark;

    private String budget;

    private Integer peopleday;

    private String orderNumber;

    private String destination;

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

    public String getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(String traveltime) {
        this.traveltime = traveltime == null ? null : traveltime.trim();
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getOutTraffic() {
        return outTraffic;
    }

    public void setOutTraffic(String outTraffic) {
        this.outTraffic = outTraffic == null ? null : outTraffic.trim();
    }

    public String getInnerTraffic() {
        return innerTraffic;
    }

    public void setInnerTraffic(String innerTraffic) {
        this.innerTraffic = innerTraffic == null ? null : innerTraffic.trim();
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel == null ? null : hotel.trim();
    }

    public String getHotelFactors() {
        return hotelFactors;
    }

    public void setHotelFactors(String hotelFactors) {
        this.hotelFactors = hotelFactors == null ? null : hotelFactors.trim();
    }

    public String getFeatureHobby() {
        return featureHobby;
    }

    public void setFeatureHobby(String featureHobby) {
        this.featureHobby = featureHobby == null ? null : featureHobby.trim();
    }

    public String getFoodHobby() {
        return foodHobby;
    }

    public void setFoodHobby(String foodHobby) {
        this.foodHobby = foodHobby == null ? null : foodHobby.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget == null ? null : budget.trim();
    }

    public Integer getPeopleday() {
        return peopleday;
    }

    public void setPeopleday(Integer peopleday) {
        this.peopleday = peopleday;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends Customization> T copy(T bean) {
        bean.setId(getId());
        bean.setPhone(getPhone());
        bean.setCreatetime(getCreatetime());
        bean.setUpdatetime(getUpdatetime());
        bean.setTraveltime(getTraveltime());
        bean.setIsEnable(getIsEnable());
        bean.setIsShared(getIsShared());
        bean.setStatus(getStatus());
        bean.setCity(getCity());
        bean.setOutTraffic(getOutTraffic());
        bean.setInnerTraffic(getInnerTraffic());
        bean.setHotel(getHotel());
        bean.setHotelFactors(getHotelFactors());
        bean.setFeatureHobby(getFeatureHobby());
        bean.setFoodHobby(getFoodHobby());
        bean.setRemark(getRemark());
        bean.setBudget(getBudget());
        bean.setPeopleday(getPeopleday());
        bean.setOrderNumber(getOrderNumber());
        bean.setDestination(getDestination());
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
        	", createtime:" + getCreatetime() + 
        	", updatetime:" + getUpdatetime() + 
        	", traveltime:" + getTraveltime() + 
        	", isEnable:" + getIsEnable() + 
        	", isShared:" + getIsShared() + 
        	", status:" + getStatus() + 
        	", city:" + getCity() + 
        	", outTraffic:" + getOutTraffic() + 
        	", innerTraffic:" + getInnerTraffic() + 
        	", hotel:" + getHotel() + 
        	", hotelFactors:" + getHotelFactors() + 
        	", featureHobby:" + getFeatureHobby() + 
        	", foodHobby:" + getFoodHobby() + 
        	", remark:" + getRemark() + 
        	", budget:" + getBudget() + 
        	", peopleday:" + getPeopleday() + 
        	", orderNumber:" + getOrderNumber() + 
        	", destination:" + getDestination() + 
        "}";
    }
}