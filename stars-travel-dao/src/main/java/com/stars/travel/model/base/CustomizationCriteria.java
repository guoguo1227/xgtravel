package com.stars.travel.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomizationCriteria implements BaseCriteria {
    private static final long serialVersionUID = 1L;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public CustomizationCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        private static final long serialVersionUID = 1L;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeIsNull() {
            addCriterion("traveltime is null");
            return (Criteria) this;
        }

        public Criteria andTraveltimeIsNotNull() {
            addCriterion("traveltime is not null");
            return (Criteria) this;
        }

        public Criteria andTraveltimeEqualTo(String value) {
            addCriterion("traveltime =", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeNotEqualTo(String value) {
            addCriterion("traveltime <>", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeGreaterThan(String value) {
            addCriterion("traveltime >", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeGreaterThanOrEqualTo(String value) {
            addCriterion("traveltime >=", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeLessThan(String value) {
            addCriterion("traveltime <", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeLessThanOrEqualTo(String value) {
            addCriterion("traveltime <=", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeLike(String value) {
            addCriterion("traveltime like", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeNotLike(String value) {
            addCriterion("traveltime not like", value, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeIn(List<String> values) {
            addCriterion("traveltime in", values, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeNotIn(List<String> values) {
            addCriterion("traveltime not in", values, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeBetween(String value1, String value2) {
            addCriterion("traveltime between", value1, value2, "traveltime");
            return (Criteria) this;
        }

        public Criteria andTraveltimeNotBetween(String value1, String value2) {
            addCriterion("traveltime not between", value1, value2, "traveltime");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNull() {
            addCriterion("is_enable is null");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNotNull() {
            addCriterion("is_enable is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnableEqualTo(Boolean value) {
            addCriterion("is_enable =", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotEqualTo(Boolean value) {
            addCriterion("is_enable <>", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThan(Boolean value) {
            addCriterion("is_enable >", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_enable >=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThan(Boolean value) {
            addCriterion("is_enable <", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThanOrEqualTo(Boolean value) {
            addCriterion("is_enable <=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableIn(List<Boolean> values) {
            addCriterion("is_enable in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotIn(List<Boolean> values) {
            addCriterion("is_enable not in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enable between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_enable not between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsSharedIsNull() {
            addCriterion("is_shared is null");
            return (Criteria) this;
        }

        public Criteria andIsSharedIsNotNull() {
            addCriterion("is_shared is not null");
            return (Criteria) this;
        }

        public Criteria andIsSharedEqualTo(Boolean value) {
            addCriterion("is_shared =", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedNotEqualTo(Boolean value) {
            addCriterion("is_shared <>", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedGreaterThan(Boolean value) {
            addCriterion("is_shared >", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_shared >=", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedLessThan(Boolean value) {
            addCriterion("is_shared <", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_shared <=", value, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedIn(List<Boolean> values) {
            addCriterion("is_shared in", values, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedNotIn(List<Boolean> values) {
            addCriterion("is_shared not in", values, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_shared between", value1, value2, "isShared");
            return (Criteria) this;
        }

        public Criteria andIsSharedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_shared not between", value1, value2, "isShared");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andOutTrafficIsNull() {
            addCriterion("out_traffic is null");
            return (Criteria) this;
        }

        public Criteria andOutTrafficIsNotNull() {
            addCriterion("out_traffic is not null");
            return (Criteria) this;
        }

        public Criteria andOutTrafficEqualTo(String value) {
            addCriterion("out_traffic =", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficNotEqualTo(String value) {
            addCriterion("out_traffic <>", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficGreaterThan(String value) {
            addCriterion("out_traffic >", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficGreaterThanOrEqualTo(String value) {
            addCriterion("out_traffic >=", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficLessThan(String value) {
            addCriterion("out_traffic <", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficLessThanOrEqualTo(String value) {
            addCriterion("out_traffic <=", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficLike(String value) {
            addCriterion("out_traffic like", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficNotLike(String value) {
            addCriterion("out_traffic not like", value, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficIn(List<String> values) {
            addCriterion("out_traffic in", values, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficNotIn(List<String> values) {
            addCriterion("out_traffic not in", values, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficBetween(String value1, String value2) {
            addCriterion("out_traffic between", value1, value2, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andOutTrafficNotBetween(String value1, String value2) {
            addCriterion("out_traffic not between", value1, value2, "outTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficIsNull() {
            addCriterion("inner_traffic is null");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficIsNotNull() {
            addCriterion("inner_traffic is not null");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficEqualTo(String value) {
            addCriterion("inner_traffic =", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficNotEqualTo(String value) {
            addCriterion("inner_traffic <>", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficGreaterThan(String value) {
            addCriterion("inner_traffic >", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficGreaterThanOrEqualTo(String value) {
            addCriterion("inner_traffic >=", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficLessThan(String value) {
            addCriterion("inner_traffic <", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficLessThanOrEqualTo(String value) {
            addCriterion("inner_traffic <=", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficLike(String value) {
            addCriterion("inner_traffic like", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficNotLike(String value) {
            addCriterion("inner_traffic not like", value, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficIn(List<String> values) {
            addCriterion("inner_traffic in", values, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficNotIn(List<String> values) {
            addCriterion("inner_traffic not in", values, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficBetween(String value1, String value2) {
            addCriterion("inner_traffic between", value1, value2, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andInnerTrafficNotBetween(String value1, String value2) {
            addCriterion("inner_traffic not between", value1, value2, "innerTraffic");
            return (Criteria) this;
        }

        public Criteria andHotelIsNull() {
            addCriterion("hotel is null");
            return (Criteria) this;
        }

        public Criteria andHotelIsNotNull() {
            addCriterion("hotel is not null");
            return (Criteria) this;
        }

        public Criteria andHotelEqualTo(String value) {
            addCriterion("hotel =", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelNotEqualTo(String value) {
            addCriterion("hotel <>", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelGreaterThan(String value) {
            addCriterion("hotel >", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelGreaterThanOrEqualTo(String value) {
            addCriterion("hotel >=", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelLessThan(String value) {
            addCriterion("hotel <", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelLessThanOrEqualTo(String value) {
            addCriterion("hotel <=", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelLike(String value) {
            addCriterion("hotel like", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelNotLike(String value) {
            addCriterion("hotel not like", value, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelIn(List<String> values) {
            addCriterion("hotel in", values, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelNotIn(List<String> values) {
            addCriterion("hotel not in", values, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelBetween(String value1, String value2) {
            addCriterion("hotel between", value1, value2, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelNotBetween(String value1, String value2) {
            addCriterion("hotel not between", value1, value2, "hotel");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsIsNull() {
            addCriterion("hotel_factors is null");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsIsNotNull() {
            addCriterion("hotel_factors is not null");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsEqualTo(String value) {
            addCriterion("hotel_factors =", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsNotEqualTo(String value) {
            addCriterion("hotel_factors <>", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsGreaterThan(String value) {
            addCriterion("hotel_factors >", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsGreaterThanOrEqualTo(String value) {
            addCriterion("hotel_factors >=", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsLessThan(String value) {
            addCriterion("hotel_factors <", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsLessThanOrEqualTo(String value) {
            addCriterion("hotel_factors <=", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsLike(String value) {
            addCriterion("hotel_factors like", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsNotLike(String value) {
            addCriterion("hotel_factors not like", value, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsIn(List<String> values) {
            addCriterion("hotel_factors in", values, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsNotIn(List<String> values) {
            addCriterion("hotel_factors not in", values, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsBetween(String value1, String value2) {
            addCriterion("hotel_factors between", value1, value2, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andHotelFactorsNotBetween(String value1, String value2) {
            addCriterion("hotel_factors not between", value1, value2, "hotelFactors");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyIsNull() {
            addCriterion("feature_hobby is null");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyIsNotNull() {
            addCriterion("feature_hobby is not null");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyEqualTo(String value) {
            addCriterion("feature_hobby =", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyNotEqualTo(String value) {
            addCriterion("feature_hobby <>", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyGreaterThan(String value) {
            addCriterion("feature_hobby >", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyGreaterThanOrEqualTo(String value) {
            addCriterion("feature_hobby >=", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyLessThan(String value) {
            addCriterion("feature_hobby <", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyLessThanOrEqualTo(String value) {
            addCriterion("feature_hobby <=", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyLike(String value) {
            addCriterion("feature_hobby like", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyNotLike(String value) {
            addCriterion("feature_hobby not like", value, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyIn(List<String> values) {
            addCriterion("feature_hobby in", values, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyNotIn(List<String> values) {
            addCriterion("feature_hobby not in", values, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyBetween(String value1, String value2) {
            addCriterion("feature_hobby between", value1, value2, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFeatureHobbyNotBetween(String value1, String value2) {
            addCriterion("feature_hobby not between", value1, value2, "featureHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyIsNull() {
            addCriterion("food_hobby is null");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyIsNotNull() {
            addCriterion("food_hobby is not null");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyEqualTo(String value) {
            addCriterion("food_hobby =", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyNotEqualTo(String value) {
            addCriterion("food_hobby <>", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyGreaterThan(String value) {
            addCriterion("food_hobby >", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyGreaterThanOrEqualTo(String value) {
            addCriterion("food_hobby >=", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyLessThan(String value) {
            addCriterion("food_hobby <", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyLessThanOrEqualTo(String value) {
            addCriterion("food_hobby <=", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyLike(String value) {
            addCriterion("food_hobby like", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyNotLike(String value) {
            addCriterion("food_hobby not like", value, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyIn(List<String> values) {
            addCriterion("food_hobby in", values, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyNotIn(List<String> values) {
            addCriterion("food_hobby not in", values, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyBetween(String value1, String value2) {
            addCriterion("food_hobby between", value1, value2, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andFoodHobbyNotBetween(String value1, String value2) {
            addCriterion("food_hobby not between", value1, value2, "foodHobby");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andBudgetIsNull() {
            addCriterion("budget is null");
            return (Criteria) this;
        }

        public Criteria andBudgetIsNotNull() {
            addCriterion("budget is not null");
            return (Criteria) this;
        }

        public Criteria andBudgetEqualTo(String value) {
            addCriterion("budget =", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotEqualTo(String value) {
            addCriterion("budget <>", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetGreaterThan(String value) {
            addCriterion("budget >", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetGreaterThanOrEqualTo(String value) {
            addCriterion("budget >=", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetLessThan(String value) {
            addCriterion("budget <", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetLessThanOrEqualTo(String value) {
            addCriterion("budget <=", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetLike(String value) {
            addCriterion("budget like", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotLike(String value) {
            addCriterion("budget not like", value, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetIn(List<String> values) {
            addCriterion("budget in", values, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotIn(List<String> values) {
            addCriterion("budget not in", values, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetBetween(String value1, String value2) {
            addCriterion("budget between", value1, value2, "budget");
            return (Criteria) this;
        }

        public Criteria andBudgetNotBetween(String value1, String value2) {
            addCriterion("budget not between", value1, value2, "budget");
            return (Criteria) this;
        }

        public Criteria andPeopledayIsNull() {
            addCriterion("peopleday is null");
            return (Criteria) this;
        }

        public Criteria andPeopledayIsNotNull() {
            addCriterion("peopleday is not null");
            return (Criteria) this;
        }

        public Criteria andPeopledayEqualTo(Integer value) {
            addCriterion("peopleday =", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayNotEqualTo(Integer value) {
            addCriterion("peopleday <>", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayGreaterThan(Integer value) {
            addCriterion("peopleday >", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayGreaterThanOrEqualTo(Integer value) {
            addCriterion("peopleday >=", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayLessThan(Integer value) {
            addCriterion("peopleday <", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayLessThanOrEqualTo(Integer value) {
            addCriterion("peopleday <=", value, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayIn(List<Integer> values) {
            addCriterion("peopleday in", values, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayNotIn(List<Integer> values) {
            addCriterion("peopleday not in", values, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayBetween(Integer value1, Integer value2) {
            addCriterion("peopleday between", value1, value2, "peopleday");
            return (Criteria) this;
        }

        public Criteria andPeopledayNotBetween(Integer value1, Integer value2) {
            addCriterion("peopleday not between", value1, value2, "peopleday");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNull() {
            addCriterion("order_number is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIsNotNull() {
            addCriterion("order_number is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumberEqualTo(String value) {
            addCriterion("order_number =", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotEqualTo(String value) {
            addCriterion("order_number <>", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThan(String value) {
            addCriterion("order_number >", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberGreaterThanOrEqualTo(String value) {
            addCriterion("order_number >=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThan(String value) {
            addCriterion("order_number <", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLessThanOrEqualTo(String value) {
            addCriterion("order_number <=", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberLike(String value) {
            addCriterion("order_number like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotLike(String value) {
            addCriterion("order_number not like", value, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberIn(List<String> values) {
            addCriterion("order_number in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotIn(List<String> values) {
            addCriterion("order_number not in", values, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberBetween(String value1, String value2) {
            addCriterion("order_number between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andOrderNumberNotBetween(String value1, String value2) {
            addCriterion("order_number not between", value1, value2, "orderNumber");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNull() {
            addCriterion("destination is null");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNotNull() {
            addCriterion("destination is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationEqualTo(String value) {
            addCriterion("destination =", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotEqualTo(String value) {
            addCriterion("destination <>", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThan(String value) {
            addCriterion("destination >", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("destination >=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThan(String value) {
            addCriterion("destination <", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThanOrEqualTo(String value) {
            addCriterion("destination <=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLike(String value) {
            addCriterion("destination like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotLike(String value) {
            addCriterion("destination not like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationIn(List<String> values) {
            addCriterion("destination in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotIn(List<String> values) {
            addCriterion("destination not in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationBetween(String value1, String value2) {
            addCriterion("destination between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotBetween(String value1, String value2) {
            addCriterion("destination not between", value1, value2, "destination");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {
        private static final long serialVersionUID = 1L;

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private static final long serialVersionUID = 1L;

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}