package com.stars.travel.model.ext;

import com.stars.travel.model.base.JourneyDay;

import java.util.List;

/**
 * Description :
 * Author : guo
 * Date : 2016/3/1 23:17
 */
public class JourneyDayVo extends JourneyDay {

    /**
     * 具体行程
     */
    private List<JourneyItemVo> journeyItemVoList ;

    public List<JourneyItemVo> getJourneyItemVoList() {
        return journeyItemVoList;
    }

    public void setJourneyItemVoList(List<JourneyItemVo> journeyItemVoList) {
        this.journeyItemVoList = journeyItemVoList;
    }
}
