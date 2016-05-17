package com.stars.travel.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCriteria implements BaseCriteria {
    private static final long serialVersionUID = 1L;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public UserCriteria() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
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

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andOauthidIsNull() {
            addCriterion("oauthid is null");
            return (Criteria) this;
        }

        public Criteria andOauthidIsNotNull() {
            addCriterion("oauthid is not null");
            return (Criteria) this;
        }

        public Criteria andOauthidEqualTo(String value) {
            addCriterion("oauthid =", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidNotEqualTo(String value) {
            addCriterion("oauthid <>", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidGreaterThan(String value) {
            addCriterion("oauthid >", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidGreaterThanOrEqualTo(String value) {
            addCriterion("oauthid >=", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidLessThan(String value) {
            addCriterion("oauthid <", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidLessThanOrEqualTo(String value) {
            addCriterion("oauthid <=", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidLike(String value) {
            addCriterion("oauthid like", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidNotLike(String value) {
            addCriterion("oauthid not like", value, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidIn(List<String> values) {
            addCriterion("oauthid in", values, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidNotIn(List<String> values) {
            addCriterion("oauthid not in", values, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidBetween(String value1, String value2) {
            addCriterion("oauthid between", value1, value2, "oauthid");
            return (Criteria) this;
        }

        public Criteria andOauthidNotBetween(String value1, String value2) {
            addCriterion("oauthid not between", value1, value2, "oauthid");
            return (Criteria) this;
        }

        public Criteria andSinatokenIsNull() {
            addCriterion("sinaToken is null");
            return (Criteria) this;
        }

        public Criteria andSinatokenIsNotNull() {
            addCriterion("sinaToken is not null");
            return (Criteria) this;
        }

        public Criteria andSinatokenEqualTo(String value) {
            addCriterion("sinaToken =", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenNotEqualTo(String value) {
            addCriterion("sinaToken <>", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenGreaterThan(String value) {
            addCriterion("sinaToken >", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenGreaterThanOrEqualTo(String value) {
            addCriterion("sinaToken >=", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenLessThan(String value) {
            addCriterion("sinaToken <", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenLessThanOrEqualTo(String value) {
            addCriterion("sinaToken <=", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenLike(String value) {
            addCriterion("sinaToken like", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenNotLike(String value) {
            addCriterion("sinaToken not like", value, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenIn(List<String> values) {
            addCriterion("sinaToken in", values, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenNotIn(List<String> values) {
            addCriterion("sinaToken not in", values, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenBetween(String value1, String value2) {
            addCriterion("sinaToken between", value1, value2, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andSinatokenNotBetween(String value1, String value2) {
            addCriterion("sinaToken not between", value1, value2, "sinatoken");
            return (Criteria) this;
        }

        public Criteria andQqoauthidIsNull() {
            addCriterion("qqOauthid is null");
            return (Criteria) this;
        }

        public Criteria andQqoauthidIsNotNull() {
            addCriterion("qqOauthid is not null");
            return (Criteria) this;
        }

        public Criteria andQqoauthidEqualTo(String value) {
            addCriterion("qqOauthid =", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidNotEqualTo(String value) {
            addCriterion("qqOauthid <>", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidGreaterThan(String value) {
            addCriterion("qqOauthid >", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidGreaterThanOrEqualTo(String value) {
            addCriterion("qqOauthid >=", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidLessThan(String value) {
            addCriterion("qqOauthid <", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidLessThanOrEqualTo(String value) {
            addCriterion("qqOauthid <=", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidLike(String value) {
            addCriterion("qqOauthid like", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidNotLike(String value) {
            addCriterion("qqOauthid not like", value, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidIn(List<String> values) {
            addCriterion("qqOauthid in", values, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidNotIn(List<String> values) {
            addCriterion("qqOauthid not in", values, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidBetween(String value1, String value2) {
            addCriterion("qqOauthid between", value1, value2, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andQqoauthidNotBetween(String value1, String value2) {
            addCriterion("qqOauthid not between", value1, value2, "qqoauthid");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNull() {
            addCriterion("weixin is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("weixin is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(String value) {
            addCriterion("weixin =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(String value) {
            addCriterion("weixin <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(String value) {
            addCriterion("weixin >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(String value) {
            addCriterion("weixin >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(String value) {
            addCriterion("weixin <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(String value) {
            addCriterion("weixin <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLike(String value) {
            addCriterion("weixin like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotLike(String value) {
            addCriterion("weixin not like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<String> values) {
            addCriterion("weixin in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<String> values) {
            addCriterion("weixin not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(String value1, String value2) {
            addCriterion("weixin between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(String value1, String value2) {
            addCriterion("weixin not between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Short value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Short value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Short value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Short value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Short value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Short value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Short> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Short> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Short value1, Short value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Short value1, Short value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Short value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Short value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Short value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Short value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Short value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Short value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Short> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Short> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Short value1, Short value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Short value1, Short value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSignatureIsNull() {
            addCriterion("signature is null");
            return (Criteria) this;
        }

        public Criteria andSignatureIsNotNull() {
            addCriterion("signature is not null");
            return (Criteria) this;
        }

        public Criteria andSignatureEqualTo(String value) {
            addCriterion("signature =", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureNotEqualTo(String value) {
            addCriterion("signature <>", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureGreaterThan(String value) {
            addCriterion("signature >", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureGreaterThanOrEqualTo(String value) {
            addCriterion("signature >=", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureLessThan(String value) {
            addCriterion("signature <", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureLessThanOrEqualTo(String value) {
            addCriterion("signature <=", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureLike(String value) {
            addCriterion("signature like", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureNotLike(String value) {
            addCriterion("signature not like", value, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureIn(List<String> values) {
            addCriterion("signature in", values, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureNotIn(List<String> values) {
            addCriterion("signature not in", values, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureBetween(String value1, String value2) {
            addCriterion("signature between", value1, value2, "signature");
            return (Criteria) this;
        }

        public Criteria andSignatureNotBetween(String value1, String value2) {
            addCriterion("signature not between", value1, value2, "signature");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoIsNull() {
            addCriterion("strategy_info is null");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoIsNotNull() {
            addCriterion("strategy_info is not null");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoEqualTo(String value) {
            addCriterion("strategy_info =", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoNotEqualTo(String value) {
            addCriterion("strategy_info <>", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoGreaterThan(String value) {
            addCriterion("strategy_info >", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoGreaterThanOrEqualTo(String value) {
            addCriterion("strategy_info >=", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoLessThan(String value) {
            addCriterion("strategy_info <", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoLessThanOrEqualTo(String value) {
            addCriterion("strategy_info <=", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoLike(String value) {
            addCriterion("strategy_info like", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoNotLike(String value) {
            addCriterion("strategy_info not like", value, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoIn(List<String> values) {
            addCriterion("strategy_info in", values, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoNotIn(List<String> values) {
            addCriterion("strategy_info not in", values, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoBetween(String value1, String value2) {
            addCriterion("strategy_info between", value1, value2, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andStrategyInfoNotBetween(String value1, String value2) {
            addCriterion("strategy_info not between", value1, value2, "strategyInfo");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(String value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(String value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(String value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(String value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(String value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(String value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLike(String value) {
            addCriterion("score like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotLike(String value) {
            addCriterion("score not like", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<String> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<String> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(String value1, String value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(String value1, String value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
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

        public Criteria andPersonalemailIsNull() {
            addCriterion("personalemail is null");
            return (Criteria) this;
        }

        public Criteria andPersonalemailIsNotNull() {
            addCriterion("personalemail is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalemailEqualTo(String value) {
            addCriterion("personalemail =", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailNotEqualTo(String value) {
            addCriterion("personalemail <>", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailGreaterThan(String value) {
            addCriterion("personalemail >", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailGreaterThanOrEqualTo(String value) {
            addCriterion("personalemail >=", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailLessThan(String value) {
            addCriterion("personalemail <", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailLessThanOrEqualTo(String value) {
            addCriterion("personalemail <=", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailLike(String value) {
            addCriterion("personalemail like", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailNotLike(String value) {
            addCriterion("personalemail not like", value, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailIn(List<String> values) {
            addCriterion("personalemail in", values, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailNotIn(List<String> values) {
            addCriterion("personalemail not in", values, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailBetween(String value1, String value2) {
            addCriterion("personalemail between", value1, value2, "personalemail");
            return (Criteria) this;
        }

        public Criteria andPersonalemailNotBetween(String value1, String value2) {
            addCriterion("personalemail not between", value1, value2, "personalemail");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNull() {
            addCriterion("weibo is null");
            return (Criteria) this;
        }

        public Criteria andWeiboIsNotNull() {
            addCriterion("weibo is not null");
            return (Criteria) this;
        }

        public Criteria andWeiboEqualTo(String value) {
            addCriterion("weibo =", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotEqualTo(String value) {
            addCriterion("weibo <>", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThan(String value) {
            addCriterion("weibo >", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboGreaterThanOrEqualTo(String value) {
            addCriterion("weibo >=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThan(String value) {
            addCriterion("weibo <", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLessThanOrEqualTo(String value) {
            addCriterion("weibo <=", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboLike(String value) {
            addCriterion("weibo like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotLike(String value) {
            addCriterion("weibo not like", value, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboIn(List<String> values) {
            addCriterion("weibo in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotIn(List<String> values) {
            addCriterion("weibo not in", values, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboBetween(String value1, String value2) {
            addCriterion("weibo between", value1, value2, "weibo");
            return (Criteria) this;
        }

        public Criteria andWeiboNotBetween(String value1, String value2) {
            addCriterion("weibo not between", value1, value2, "weibo");
            return (Criteria) this;
        }

        public Criteria andActivatedIsNull() {
            addCriterion("activated is null");
            return (Criteria) this;
        }

        public Criteria andActivatedIsNotNull() {
            addCriterion("activated is not null");
            return (Criteria) this;
        }

        public Criteria andActivatedEqualTo(Short value) {
            addCriterion("activated =", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedNotEqualTo(Short value) {
            addCriterion("activated <>", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedGreaterThan(Short value) {
            addCriterion("activated >", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedGreaterThanOrEqualTo(Short value) {
            addCriterion("activated >=", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedLessThan(Short value) {
            addCriterion("activated <", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedLessThanOrEqualTo(Short value) {
            addCriterion("activated <=", value, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedIn(List<Short> values) {
            addCriterion("activated in", values, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedNotIn(List<Short> values) {
            addCriterion("activated not in", values, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedBetween(Short value1, Short value2) {
            addCriterion("activated between", value1, value2, "activated");
            return (Criteria) this;
        }

        public Criteria andActivatedNotBetween(Short value1, Short value2) {
            addCriterion("activated not between", value1, value2, "activated");
            return (Criteria) this;
        }

        public Criteria andPortraitIsNull() {
            addCriterion("portrait is null");
            return (Criteria) this;
        }

        public Criteria andPortraitIsNotNull() {
            addCriterion("portrait is not null");
            return (Criteria) this;
        }

        public Criteria andPortraitEqualTo(String value) {
            addCriterion("portrait =", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitNotEqualTo(String value) {
            addCriterion("portrait <>", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitGreaterThan(String value) {
            addCriterion("portrait >", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitGreaterThanOrEqualTo(String value) {
            addCriterion("portrait >=", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitLessThan(String value) {
            addCriterion("portrait <", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitLessThanOrEqualTo(String value) {
            addCriterion("portrait <=", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitLike(String value) {
            addCriterion("portrait like", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitNotLike(String value) {
            addCriterion("portrait not like", value, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitIn(List<String> values) {
            addCriterion("portrait in", values, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitNotIn(List<String> values) {
            addCriterion("portrait not in", values, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitBetween(String value1, String value2) {
            addCriterion("portrait between", value1, value2, "portrait");
            return (Criteria) this;
        }

        public Criteria andPortraitNotBetween(String value1, String value2) {
            addCriterion("portrait not between", value1, value2, "portrait");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Short value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Short value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Short value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Short value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Short value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Short> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Short> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Short value1, Short value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Short value1, Short value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageIsNull() {
            addCriterion("introduce_image is null");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageIsNotNull() {
            addCriterion("introduce_image is not null");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageEqualTo(String value) {
            addCriterion("introduce_image =", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageNotEqualTo(String value) {
            addCriterion("introduce_image <>", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageGreaterThan(String value) {
            addCriterion("introduce_image >", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageGreaterThanOrEqualTo(String value) {
            addCriterion("introduce_image >=", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageLessThan(String value) {
            addCriterion("introduce_image <", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageLessThanOrEqualTo(String value) {
            addCriterion("introduce_image <=", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageLike(String value) {
            addCriterion("introduce_image like", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageNotLike(String value) {
            addCriterion("introduce_image not like", value, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageIn(List<String> values) {
            addCriterion("introduce_image in", values, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageNotIn(List<String> values) {
            addCriterion("introduce_image not in", values, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageBetween(String value1, String value2) {
            addCriterion("introduce_image between", value1, value2, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroduceImageNotBetween(String value1, String value2) {
            addCriterion("introduce_image not between", value1, value2, "introduceImage");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNull() {
            addCriterion("introduction is null");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNotNull() {
            addCriterion("introduction is not null");
            return (Criteria) this;
        }

        public Criteria andIntroductionEqualTo(String value) {
            addCriterion("introduction =", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotEqualTo(String value) {
            addCriterion("introduction <>", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThan(String value) {
            addCriterion("introduction >", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanOrEqualTo(String value) {
            addCriterion("introduction >=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThan(String value) {
            addCriterion("introduction <", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanOrEqualTo(String value) {
            addCriterion("introduction <=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLike(String value) {
            addCriterion("introduction like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotLike(String value) {
            addCriterion("introduction not like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionIn(List<String> values) {
            addCriterion("introduction in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotIn(List<String> values) {
            addCriterion("introduction not in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionBetween(String value1, String value2) {
            addCriterion("introduction between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotBetween(String value1, String value2) {
            addCriterion("introduction not between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andProfessionIsNull() {
            addCriterion("profession is null");
            return (Criteria) this;
        }

        public Criteria andProfessionIsNotNull() {
            addCriterion("profession is not null");
            return (Criteria) this;
        }

        public Criteria andProfessionEqualTo(String value) {
            addCriterion("profession =", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotEqualTo(String value) {
            addCriterion("profession <>", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThan(String value) {
            addCriterion("profession >", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThanOrEqualTo(String value) {
            addCriterion("profession >=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThan(String value) {
            addCriterion("profession <", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThanOrEqualTo(String value) {
            addCriterion("profession <=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLike(String value) {
            addCriterion("profession like", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotLike(String value) {
            addCriterion("profession not like", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionIn(List<String> values) {
            addCriterion("profession in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotIn(List<String> values) {
            addCriterion("profession not in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionBetween(String value1, String value2) {
            addCriterion("profession between", value1, value2, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotBetween(String value1, String value2) {
            addCriterion("profession not between", value1, value2, "profession");
            return (Criteria) this;
        }

        public Criteria andNamespyIsNull() {
            addCriterion("namespy is null");
            return (Criteria) this;
        }

        public Criteria andNamespyIsNotNull() {
            addCriterion("namespy is not null");
            return (Criteria) this;
        }

        public Criteria andNamespyEqualTo(String value) {
            addCriterion("namespy =", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyNotEqualTo(String value) {
            addCriterion("namespy <>", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyGreaterThan(String value) {
            addCriterion("namespy >", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyGreaterThanOrEqualTo(String value) {
            addCriterion("namespy >=", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyLessThan(String value) {
            addCriterion("namespy <", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyLessThanOrEqualTo(String value) {
            addCriterion("namespy <=", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyLike(String value) {
            addCriterion("namespy like", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyNotLike(String value) {
            addCriterion("namespy not like", value, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyIn(List<String> values) {
            addCriterion("namespy in", values, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyNotIn(List<String> values) {
            addCriterion("namespy not in", values, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyBetween(String value1, String value2) {
            addCriterion("namespy between", value1, value2, "namespy");
            return (Criteria) this;
        }

        public Criteria andNamespyNotBetween(String value1, String value2) {
            addCriterion("namespy not between", value1, value2, "namespy");
            return (Criteria) this;
        }

        public Criteria andIdmd5IsNull() {
            addCriterion("idMd5 is null");
            return (Criteria) this;
        }

        public Criteria andIdmd5IsNotNull() {
            addCriterion("idMd5 is not null");
            return (Criteria) this;
        }

        public Criteria andIdmd5EqualTo(String value) {
            addCriterion("idMd5 =", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5NotEqualTo(String value) {
            addCriterion("idMd5 <>", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5GreaterThan(String value) {
            addCriterion("idMd5 >", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5GreaterThanOrEqualTo(String value) {
            addCriterion("idMd5 >=", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5LessThan(String value) {
            addCriterion("idMd5 <", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5LessThanOrEqualTo(String value) {
            addCriterion("idMd5 <=", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5Like(String value) {
            addCriterion("idMd5 like", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5NotLike(String value) {
            addCriterion("idMd5 not like", value, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5In(List<String> values) {
            addCriterion("idMd5 in", values, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5NotIn(List<String> values) {
            addCriterion("idMd5 not in", values, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5Between(String value1, String value2) {
            addCriterion("idMd5 between", value1, value2, "idmd5");
            return (Criteria) this;
        }

        public Criteria andIdmd5NotBetween(String value1, String value2) {
            addCriterion("idMd5 not between", value1, value2, "idmd5");
            return (Criteria) this;
        }

        public Criteria andFromsiteIsNull() {
            addCriterion("fromsite is null");
            return (Criteria) this;
        }

        public Criteria andFromsiteIsNotNull() {
            addCriterion("fromsite is not null");
            return (Criteria) this;
        }

        public Criteria andFromsiteEqualTo(String value) {
            addCriterion("fromsite =", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotEqualTo(String value) {
            addCriterion("fromsite <>", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteGreaterThan(String value) {
            addCriterion("fromsite >", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteGreaterThanOrEqualTo(String value) {
            addCriterion("fromsite >=", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLessThan(String value) {
            addCriterion("fromsite <", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLessThanOrEqualTo(String value) {
            addCriterion("fromsite <=", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLike(String value) {
            addCriterion("fromsite like", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotLike(String value) {
            addCriterion("fromsite not like", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteIn(List<String> values) {
            addCriterion("fromsite in", values, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotIn(List<String> values) {
            addCriterion("fromsite not in", values, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteBetween(String value1, String value2) {
            addCriterion("fromsite between", value1, value2, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotBetween(String value1, String value2) {
            addCriterion("fromsite not between", value1, value2, "fromsite");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNull() {
            addCriterion("utm_source is null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIsNotNull() {
            addCriterion("utm_source is not null");
            return (Criteria) this;
        }

        public Criteria andUtmSourceEqualTo(String value) {
            addCriterion("utm_source =", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotEqualTo(String value) {
            addCriterion("utm_source <>", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThan(String value) {
            addCriterion("utm_source >", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceGreaterThanOrEqualTo(String value) {
            addCriterion("utm_source >=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThan(String value) {
            addCriterion("utm_source <", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLessThanOrEqualTo(String value) {
            addCriterion("utm_source <=", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceLike(String value) {
            addCriterion("utm_source like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotLike(String value) {
            addCriterion("utm_source not like", value, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceIn(List<String> values) {
            addCriterion("utm_source in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotIn(List<String> values) {
            addCriterion("utm_source not in", values, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceBetween(String value1, String value2) {
            addCriterion("utm_source between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andUtmSourceNotBetween(String value1, String value2) {
            addCriterion("utm_source not between", value1, value2, "utmSource");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendIsNull() {
            addCriterion("isActiveEmailSend is null");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendIsNotNull() {
            addCriterion("isActiveEmailSend is not null");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendEqualTo(Integer value) {
            addCriterion("isActiveEmailSend =", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendNotEqualTo(Integer value) {
            addCriterion("isActiveEmailSend <>", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendGreaterThan(Integer value) {
            addCriterion("isActiveEmailSend >", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendGreaterThanOrEqualTo(Integer value) {
            addCriterion("isActiveEmailSend >=", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendLessThan(Integer value) {
            addCriterion("isActiveEmailSend <", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendLessThanOrEqualTo(Integer value) {
            addCriterion("isActiveEmailSend <=", value, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendIn(List<Integer> values) {
            addCriterion("isActiveEmailSend in", values, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendNotIn(List<Integer> values) {
            addCriterion("isActiveEmailSend not in", values, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendBetween(Integer value1, Integer value2) {
            addCriterion("isActiveEmailSend between", value1, value2, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andIsactiveemailsendNotBetween(Integer value1, Integer value2) {
            addCriterion("isActiveEmailSend not between", value1, value2, "isactiveemailsend");
            return (Criteria) this;
        }

        public Criteria andNikenameIsNull() {
            addCriterion("nikeName is null");
            return (Criteria) this;
        }

        public Criteria andNikenameIsNotNull() {
            addCriterion("nikeName is not null");
            return (Criteria) this;
        }

        public Criteria andNikenameEqualTo(String value) {
            addCriterion("nikeName =", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameNotEqualTo(String value) {
            addCriterion("nikeName <>", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameGreaterThan(String value) {
            addCriterion("nikeName >", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameGreaterThanOrEqualTo(String value) {
            addCriterion("nikeName >=", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameLessThan(String value) {
            addCriterion("nikeName <", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameLessThanOrEqualTo(String value) {
            addCriterion("nikeName <=", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameLike(String value) {
            addCriterion("nikeName like", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameNotLike(String value) {
            addCriterion("nikeName not like", value, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameIn(List<String> values) {
            addCriterion("nikeName in", values, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameNotIn(List<String> values) {
            addCriterion("nikeName not in", values, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameBetween(String value1, String value2) {
            addCriterion("nikeName between", value1, value2, "nikename");
            return (Criteria) this;
        }

        public Criteria andNikenameNotBetween(String value1, String value2) {
            addCriterion("nikeName not between", value1, value2, "nikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameIsNull() {
            addCriterion("qqNikeName is null");
            return (Criteria) this;
        }

        public Criteria andQqnikenameIsNotNull() {
            addCriterion("qqNikeName is not null");
            return (Criteria) this;
        }

        public Criteria andQqnikenameEqualTo(String value) {
            addCriterion("qqNikeName =", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameNotEqualTo(String value) {
            addCriterion("qqNikeName <>", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameGreaterThan(String value) {
            addCriterion("qqNikeName >", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameGreaterThanOrEqualTo(String value) {
            addCriterion("qqNikeName >=", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameLessThan(String value) {
            addCriterion("qqNikeName <", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameLessThanOrEqualTo(String value) {
            addCriterion("qqNikeName <=", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameLike(String value) {
            addCriterion("qqNikeName like", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameNotLike(String value) {
            addCriterion("qqNikeName not like", value, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameIn(List<String> values) {
            addCriterion("qqNikeName in", values, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameNotIn(List<String> values) {
            addCriterion("qqNikeName not in", values, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameBetween(String value1, String value2) {
            addCriterion("qqNikeName between", value1, value2, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andQqnikenameNotBetween(String value1, String value2) {
            addCriterion("qqNikeName not between", value1, value2, "qqnikename");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueIsNull() {
            addCriterion("isLoginTokenOverdue is null");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueIsNotNull() {
            addCriterion("isLoginTokenOverdue is not null");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueEqualTo(Boolean value) {
            addCriterion("isLoginTokenOverdue =", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueNotEqualTo(Boolean value) {
            addCriterion("isLoginTokenOverdue <>", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueGreaterThan(Boolean value) {
            addCriterion("isLoginTokenOverdue >", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isLoginTokenOverdue >=", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueLessThan(Boolean value) {
            addCriterion("isLoginTokenOverdue <", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueLessThanOrEqualTo(Boolean value) {
            addCriterion("isLoginTokenOverdue <=", value, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueIn(List<Boolean> values) {
            addCriterion("isLoginTokenOverdue in", values, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueNotIn(List<Boolean> values) {
            addCriterion("isLoginTokenOverdue not in", values, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueBetween(Boolean value1, Boolean value2) {
            addCriterion("isLoginTokenOverdue between", value1, value2, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andIslogintokenoverdueNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isLoginTokenOverdue not between", value1, value2, "islogintokenoverdue");
            return (Criteria) this;
        }

        public Criteria andAccessversionIsNull() {
            addCriterion("accessVersion is null");
            return (Criteria) this;
        }

        public Criteria andAccessversionIsNotNull() {
            addCriterion("accessVersion is not null");
            return (Criteria) this;
        }

        public Criteria andAccessversionEqualTo(String value) {
            addCriterion("accessVersion =", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionNotEqualTo(String value) {
            addCriterion("accessVersion <>", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionGreaterThan(String value) {
            addCriterion("accessVersion >", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionGreaterThanOrEqualTo(String value) {
            addCriterion("accessVersion >=", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionLessThan(String value) {
            addCriterion("accessVersion <", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionLessThanOrEqualTo(String value) {
            addCriterion("accessVersion <=", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionLike(String value) {
            addCriterion("accessVersion like", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionNotLike(String value) {
            addCriterion("accessVersion not like", value, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionIn(List<String> values) {
            addCriterion("accessVersion in", values, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionNotIn(List<String> values) {
            addCriterion("accessVersion not in", values, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionBetween(String value1, String value2) {
            addCriterion("accessVersion between", value1, value2, "accessversion");
            return (Criteria) this;
        }

        public Criteria andAccessversionNotBetween(String value1, String value2) {
            addCriterion("accessVersion not between", value1, value2, "accessversion");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeIsNull() {
            addCriterion("lastLoginTime is null");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeIsNotNull() {
            addCriterion("lastLoginTime is not null");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeEqualTo(Date value) {
            addCriterion("lastLoginTime =", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeNotEqualTo(Date value) {
            addCriterion("lastLoginTime <>", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeGreaterThan(Date value) {
            addCriterion("lastLoginTime >", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lastLoginTime >=", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeLessThan(Date value) {
            addCriterion("lastLoginTime <", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeLessThanOrEqualTo(Date value) {
            addCriterion("lastLoginTime <=", value, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeIn(List<Date> values) {
            addCriterion("lastLoginTime in", values, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeNotIn(List<Date> values) {
            addCriterion("lastLoginTime not in", values, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeBetween(Date value1, Date value2) {
            addCriterion("lastLoginTime between", value1, value2, "lastlogintime");
            return (Criteria) this;
        }

        public Criteria andLastlogintimeNotBetween(Date value1, Date value2) {
            addCriterion("lastLoginTime not between", value1, value2, "lastlogintime");
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

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(String value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(String value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(String value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(String value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(String value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(String value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLike(String value) {
            addCriterion("price like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotLike(String value) {
            addCriterion("price not like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<String> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<String> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(String value1, String value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(String value1, String value2) {
            addCriterion("price not between", value1, value2, "price");
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