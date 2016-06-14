package com.stars.travel.model.condition;


import java.util.*;

/**
 * 搜索条件：通用
 */
public class AuctionSearchCondition extends BaseSearchCondition {

    //搜索文字内容
    private String searchContent ;

    private String name; //名称

    private Integer id; // 查询详情id

    private Integer fId; //标记id

    private Integer userId ; //用户Id

    private Boolean ifEnable; //是否可用

    private Boolean ifShared; //是否分享

    private Boolean ifNew; //是否最新

    private Boolean ifHot; //是否最热

    private String type; //类型

    private String orderByClause; //排列

    private String destination; //目的地

    private Short activated; //是否激活
    //phone
    private String phone ;
    //email
    private String email ;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;

    private Integer idGreaterThan; //大于某id

    private Integer idLessThan; //小于某id

    private String token ; //授权token

    private Boolean my; //我的

    public Boolean getMy() {
        return my;
    }

    public void setMy(Boolean my) {
        this.my = my;
    }

    public Short getActivated() {
        return activated;
    }

    public void setActivated(Short activated) {
        this.activated = activated;
    }

    private List<Integer> idsIn;  //符合要求的id

    public List<Integer> getIdsIn() {
        return idsIn;
    }

    public void setIdsIn(List<Integer> idsIn) {
        this.idsIn = idsIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIdGreaterThan() {
        return idGreaterThan;
    }

    public void setIdGreaterThan(Integer idGreaterThan) {
        this.idGreaterThan = idGreaterThan;
    }

    public Integer getIdLessThan() {
        return idLessThan;
    }

    public void setIdLessThan(Integer idLessThan) {
        this.idLessThan = idLessThan;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIfEnable() {
        return ifEnable;
    }

    public void setIfEnable(Boolean ifEnable) {
        this.ifEnable = ifEnable;
    }

    public Boolean getIfShared() {
        return ifShared;
    }

    public void setIfShared(Boolean ifShared) {
        this.ifShared = ifShared;
    }

    public Boolean getIfNew() {
        return ifNew;
    }

    public void setIfNew(Boolean ifNew) {
        this.ifNew = ifNew;
        if(ifNew){
            this.orderByClause = " order by createtime";
        }
    }

    public Boolean getIfHot() {
        return ifHot;
    }

    public void setIfHot(Boolean ifHot) {
        this.ifHot = ifHot;
        if(ifHot){
            this.orderByClause = " order by top";
        }
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}
