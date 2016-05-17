package com.stars.travel.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MicroblogCriteria implements BaseCriteria {
    private static final long serialVersionUID = 1L;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public MicroblogCriteria() {
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

        public Criteria andPictureDescriptionIsNull() {
            addCriterion("picture_description is null");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionIsNotNull() {
            addCriterion("picture_description is not null");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionEqualTo(String value) {
            addCriterion("picture_description =", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionNotEqualTo(String value) {
            addCriterion("picture_description <>", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionGreaterThan(String value) {
            addCriterion("picture_description >", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("picture_description >=", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionLessThan(String value) {
            addCriterion("picture_description <", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionLessThanOrEqualTo(String value) {
            addCriterion("picture_description <=", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionLike(String value) {
            addCriterion("picture_description like", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionNotLike(String value) {
            addCriterion("picture_description not like", value, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionIn(List<String> values) {
            addCriterion("picture_description in", values, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionNotIn(List<String> values) {
            addCriterion("picture_description not in", values, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionBetween(String value1, String value2) {
            addCriterion("picture_description between", value1, value2, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureDescriptionNotBetween(String value1, String value2) {
            addCriterion("picture_description not between", value1, value2, "pictureDescription");
            return (Criteria) this;
        }

        public Criteria andPictureIsNull() {
            addCriterion("picture is null");
            return (Criteria) this;
        }

        public Criteria andPictureIsNotNull() {
            addCriterion("picture is not null");
            return (Criteria) this;
        }

        public Criteria andPictureEqualTo(String value) {
            addCriterion("picture =", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureNotEqualTo(String value) {
            addCriterion("picture <>", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureGreaterThan(String value) {
            addCriterion("picture >", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureGreaterThanOrEqualTo(String value) {
            addCriterion("picture >=", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureLessThan(String value) {
            addCriterion("picture <", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureLessThanOrEqualTo(String value) {
            addCriterion("picture <=", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureLike(String value) {
            addCriterion("picture like", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureNotLike(String value) {
            addCriterion("picture not like", value, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureIn(List<String> values) {
            addCriterion("picture in", values, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureNotIn(List<String> values) {
            addCriterion("picture not in", values, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureBetween(String value1, String value2) {
            addCriterion("picture between", value1, value2, "picture");
            return (Criteria) this;
        }

        public Criteria andPictureNotBetween(String value1, String value2) {
            addCriterion("picture not between", value1, value2, "picture");
            return (Criteria) this;
        }

        public Criteria andFunPictureIsNull() {
            addCriterion("fun_picture is null");
            return (Criteria) this;
        }

        public Criteria andFunPictureIsNotNull() {
            addCriterion("fun_picture is not null");
            return (Criteria) this;
        }

        public Criteria andFunPictureEqualTo(String value) {
            addCriterion("fun_picture =", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureNotEqualTo(String value) {
            addCriterion("fun_picture <>", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureGreaterThan(String value) {
            addCriterion("fun_picture >", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureGreaterThanOrEqualTo(String value) {
            addCriterion("fun_picture >=", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureLessThan(String value) {
            addCriterion("fun_picture <", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureLessThanOrEqualTo(String value) {
            addCriterion("fun_picture <=", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureLike(String value) {
            addCriterion("fun_picture like", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureNotLike(String value) {
            addCriterion("fun_picture not like", value, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureIn(List<String> values) {
            addCriterion("fun_picture in", values, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureNotIn(List<String> values) {
            addCriterion("fun_picture not in", values, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureBetween(String value1, String value2) {
            addCriterion("fun_picture between", value1, value2, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureNotBetween(String value1, String value2) {
            addCriterion("fun_picture not between", value1, value2, "funPicture");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionIsNull() {
            addCriterion("fun_picture_description is null");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionIsNotNull() {
            addCriterion("fun_picture_description is not null");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionEqualTo(String value) {
            addCriterion("fun_picture_description =", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionNotEqualTo(String value) {
            addCriterion("fun_picture_description <>", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionGreaterThan(String value) {
            addCriterion("fun_picture_description >", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("fun_picture_description >=", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionLessThan(String value) {
            addCriterion("fun_picture_description <", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionLessThanOrEqualTo(String value) {
            addCriterion("fun_picture_description <=", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionLike(String value) {
            addCriterion("fun_picture_description like", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionNotLike(String value) {
            addCriterion("fun_picture_description not like", value, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionIn(List<String> values) {
            addCriterion("fun_picture_description in", values, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionNotIn(List<String> values) {
            addCriterion("fun_picture_description not in", values, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionBetween(String value1, String value2) {
            addCriterion("fun_picture_description between", value1, value2, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFunPictureDescriptionNotBetween(String value1, String value2) {
            addCriterion("fun_picture_description not between", value1, value2, "funPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionIsNull() {
            addCriterion("food_picture_description is null");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionIsNotNull() {
            addCriterion("food_picture_description is not null");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionEqualTo(String value) {
            addCriterion("food_picture_description =", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionNotEqualTo(String value) {
            addCriterion("food_picture_description <>", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionGreaterThan(String value) {
            addCriterion("food_picture_description >", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("food_picture_description >=", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionLessThan(String value) {
            addCriterion("food_picture_description <", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionLessThanOrEqualTo(String value) {
            addCriterion("food_picture_description <=", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionLike(String value) {
            addCriterion("food_picture_description like", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionNotLike(String value) {
            addCriterion("food_picture_description not like", value, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionIn(List<String> values) {
            addCriterion("food_picture_description in", values, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionNotIn(List<String> values) {
            addCriterion("food_picture_description not in", values, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionBetween(String value1, String value2) {
            addCriterion("food_picture_description between", value1, value2, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureDescriptionNotBetween(String value1, String value2) {
            addCriterion("food_picture_description not between", value1, value2, "foodPictureDescription");
            return (Criteria) this;
        }

        public Criteria andFoodPictureIsNull() {
            addCriterion("food_picture is null");
            return (Criteria) this;
        }

        public Criteria andFoodPictureIsNotNull() {
            addCriterion("food_picture is not null");
            return (Criteria) this;
        }

        public Criteria andFoodPictureEqualTo(String value) {
            addCriterion("food_picture =", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureNotEqualTo(String value) {
            addCriterion("food_picture <>", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureGreaterThan(String value) {
            addCriterion("food_picture >", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureGreaterThanOrEqualTo(String value) {
            addCriterion("food_picture >=", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureLessThan(String value) {
            addCriterion("food_picture <", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureLessThanOrEqualTo(String value) {
            addCriterion("food_picture <=", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureLike(String value) {
            addCriterion("food_picture like", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureNotLike(String value) {
            addCriterion("food_picture not like", value, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureIn(List<String> values) {
            addCriterion("food_picture in", values, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureNotIn(List<String> values) {
            addCriterion("food_picture not in", values, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureBetween(String value1, String value2) {
            addCriterion("food_picture between", value1, value2, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andFoodPictureNotBetween(String value1, String value2) {
            addCriterion("food_picture not between", value1, value2, "foodPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureIsNull() {
            addCriterion("scenery_picture is null");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureIsNotNull() {
            addCriterion("scenery_picture is not null");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureEqualTo(String value) {
            addCriterion("scenery_picture =", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureNotEqualTo(String value) {
            addCriterion("scenery_picture <>", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureGreaterThan(String value) {
            addCriterion("scenery_picture >", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureGreaterThanOrEqualTo(String value) {
            addCriterion("scenery_picture >=", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureLessThan(String value) {
            addCriterion("scenery_picture <", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureLessThanOrEqualTo(String value) {
            addCriterion("scenery_picture <=", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureLike(String value) {
            addCriterion("scenery_picture like", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureNotLike(String value) {
            addCriterion("scenery_picture not like", value, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureIn(List<String> values) {
            addCriterion("scenery_picture in", values, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureNotIn(List<String> values) {
            addCriterion("scenery_picture not in", values, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureBetween(String value1, String value2) {
            addCriterion("scenery_picture between", value1, value2, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andSceneryPictureNotBetween(String value1, String value2) {
            addCriterion("scenery_picture not between", value1, value2, "sceneryPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionIsNull() {
            addCriterion("newness_picture_description is null");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionIsNotNull() {
            addCriterion("newness_picture_description is not null");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionEqualTo(String value) {
            addCriterion("newness_picture_description =", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionNotEqualTo(String value) {
            addCriterion("newness_picture_description <>", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionGreaterThan(String value) {
            addCriterion("newness_picture_description >", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("newness_picture_description >=", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionLessThan(String value) {
            addCriterion("newness_picture_description <", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionLessThanOrEqualTo(String value) {
            addCriterion("newness_picture_description <=", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionLike(String value) {
            addCriterion("newness_picture_description like", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionNotLike(String value) {
            addCriterion("newness_picture_description not like", value, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionIn(List<String> values) {
            addCriterion("newness_picture_description in", values, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionNotIn(List<String> values) {
            addCriterion("newness_picture_description not in", values, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionBetween(String value1, String value2) {
            addCriterion("newness_picture_description between", value1, value2, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureDescriptionNotBetween(String value1, String value2) {
            addCriterion("newness_picture_description not between", value1, value2, "newnessPictureDescription");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureIsNull() {
            addCriterion("newness_picture is null");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureIsNotNull() {
            addCriterion("newness_picture is not null");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureEqualTo(String value) {
            addCriterion("newness_picture =", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureNotEqualTo(String value) {
            addCriterion("newness_picture <>", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureGreaterThan(String value) {
            addCriterion("newness_picture >", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureGreaterThanOrEqualTo(String value) {
            addCriterion("newness_picture >=", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureLessThan(String value) {
            addCriterion("newness_picture <", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureLessThanOrEqualTo(String value) {
            addCriterion("newness_picture <=", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureLike(String value) {
            addCriterion("newness_picture like", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureNotLike(String value) {
            addCriterion("newness_picture not like", value, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureIn(List<String> values) {
            addCriterion("newness_picture in", values, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureNotIn(List<String> values) {
            addCriterion("newness_picture not in", values, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureBetween(String value1, String value2) {
            addCriterion("newness_picture between", value1, value2, "newnessPicture");
            return (Criteria) this;
        }

        public Criteria andNewnessPictureNotBetween(String value1, String value2) {
            addCriterion("newness_picture not between", value1, value2, "newnessPicture");
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

        public Criteria andTopIsNull() {
            addCriterion("top is null");
            return (Criteria) this;
        }

        public Criteria andTopIsNotNull() {
            addCriterion("top is not null");
            return (Criteria) this;
        }

        public Criteria andTopEqualTo(Integer value) {
            addCriterion("top =", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotEqualTo(Integer value) {
            addCriterion("top <>", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThan(Integer value) {
            addCriterion("top >", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThanOrEqualTo(Integer value) {
            addCriterion("top >=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThan(Integer value) {
            addCriterion("top <", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThanOrEqualTo(Integer value) {
            addCriterion("top <=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopIn(List<Integer> values) {
            addCriterion("top in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotIn(List<Integer> values) {
            addCriterion("top not in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopBetween(Integer value1, Integer value2) {
            addCriterion("top between", value1, value2, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotBetween(Integer value1, Integer value2) {
            addCriterion("top not between", value1, value2, "top");
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
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

        public Criteria andDestDescriptionIsNull() {
            addCriterion("dest_Description is null");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionIsNotNull() {
            addCriterion("dest_Description is not null");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionEqualTo(String value) {
            addCriterion("dest_Description =", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionNotEqualTo(String value) {
            addCriterion("dest_Description <>", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionGreaterThan(String value) {
            addCriterion("dest_Description >", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("dest_Description >=", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionLessThan(String value) {
            addCriterion("dest_Description <", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionLessThanOrEqualTo(String value) {
            addCriterion("dest_Description <=", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionLike(String value) {
            addCriterion("dest_Description like", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionNotLike(String value) {
            addCriterion("dest_Description not like", value, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionIn(List<String> values) {
            addCriterion("dest_Description in", values, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionNotIn(List<String> values) {
            addCriterion("dest_Description not in", values, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionBetween(String value1, String value2) {
            addCriterion("dest_Description between", value1, value2, "destDescription");
            return (Criteria) this;
        }

        public Criteria andDestDescriptionNotBetween(String value1, String value2) {
            addCriterion("dest_Description not between", value1, value2, "destDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionIsNull() {
            addCriterion("novel_Description is null");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionIsNotNull() {
            addCriterion("novel_Description is not null");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionEqualTo(String value) {
            addCriterion("novel_Description =", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionNotEqualTo(String value) {
            addCriterion("novel_Description <>", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionGreaterThan(String value) {
            addCriterion("novel_Description >", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("novel_Description >=", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionLessThan(String value) {
            addCriterion("novel_Description <", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionLessThanOrEqualTo(String value) {
            addCriterion("novel_Description <=", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionLike(String value) {
            addCriterion("novel_Description like", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionNotLike(String value) {
            addCriterion("novel_Description not like", value, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionIn(List<String> values) {
            addCriterion("novel_Description in", values, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionNotIn(List<String> values) {
            addCriterion("novel_Description not in", values, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionBetween(String value1, String value2) {
            addCriterion("novel_Description between", value1, value2, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andNovelDescriptionNotBetween(String value1, String value2) {
            addCriterion("novel_Description not between", value1, value2, "novelDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionIsNull() {
            addCriterion("end_Description is null");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionIsNotNull() {
            addCriterion("end_Description is not null");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionEqualTo(String value) {
            addCriterion("end_Description =", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionNotEqualTo(String value) {
            addCriterion("end_Description <>", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionGreaterThan(String value) {
            addCriterion("end_Description >", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("end_Description >=", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionLessThan(String value) {
            addCriterion("end_Description <", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionLessThanOrEqualTo(String value) {
            addCriterion("end_Description <=", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionLike(String value) {
            addCriterion("end_Description like", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionNotLike(String value) {
            addCriterion("end_Description not like", value, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionIn(List<String> values) {
            addCriterion("end_Description in", values, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionNotIn(List<String> values) {
            addCriterion("end_Description not in", values, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionBetween(String value1, String value2) {
            addCriterion("end_Description between", value1, value2, "endDescription");
            return (Criteria) this;
        }

        public Criteria andEndDescriptionNotBetween(String value1, String value2) {
            addCriterion("end_Description not between", value1, value2, "endDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionIsNull() {
            addCriterion("scenery_Description is null");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionIsNotNull() {
            addCriterion("scenery_Description is not null");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionEqualTo(String value) {
            addCriterion("scenery_Description =", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionNotEqualTo(String value) {
            addCriterion("scenery_Description <>", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionGreaterThan(String value) {
            addCriterion("scenery_Description >", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("scenery_Description >=", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionLessThan(String value) {
            addCriterion("scenery_Description <", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionLessThanOrEqualTo(String value) {
            addCriterion("scenery_Description <=", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionLike(String value) {
            addCriterion("scenery_Description like", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionNotLike(String value) {
            addCriterion("scenery_Description not like", value, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionIn(List<String> values) {
            addCriterion("scenery_Description in", values, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionNotIn(List<String> values) {
            addCriterion("scenery_Description not in", values, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionBetween(String value1, String value2) {
            addCriterion("scenery_Description between", value1, value2, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSceneryDescriptionNotBetween(String value1, String value2) {
            addCriterion("scenery_Description not between", value1, value2, "sceneryDescription");
            return (Criteria) this;
        }

        public Criteria andSharetimesIsNull() {
            addCriterion("sharetimes is null");
            return (Criteria) this;
        }

        public Criteria andSharetimesIsNotNull() {
            addCriterion("sharetimes is not null");
            return (Criteria) this;
        }

        public Criteria andSharetimesEqualTo(Integer value) {
            addCriterion("sharetimes =", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesNotEqualTo(Integer value) {
            addCriterion("sharetimes <>", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesGreaterThan(Integer value) {
            addCriterion("sharetimes >", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("sharetimes >=", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesLessThan(Integer value) {
            addCriterion("sharetimes <", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesLessThanOrEqualTo(Integer value) {
            addCriterion("sharetimes <=", value, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesIn(List<Integer> values) {
            addCriterion("sharetimes in", values, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesNotIn(List<Integer> values) {
            addCriterion("sharetimes not in", values, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesBetween(Integer value1, Integer value2) {
            addCriterion("sharetimes between", value1, value2, "sharetimes");
            return (Criteria) this;
        }

        public Criteria andSharetimesNotBetween(Integer value1, Integer value2) {
            addCriterion("sharetimes not between", value1, value2, "sharetimes");
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