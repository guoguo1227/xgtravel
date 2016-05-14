package com.stars.travel.model.ext;

import com.stars.travel.model.base.JourneyItem;

/**
 * Description :
 * Author : guo
 * Date : 2016/3/13 22:35
 */
public class JourneyItemVo extends JourneyItem {
    /**
     * 当前天
     */
    private Integer currentDay;

    /**
     * 行程id
     */
    private Integer journeyId;

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
}
