package com.stars.travel.model.base;

public class JourneyCollectionKey {
    private Integer id;

    private String phoneJourneyKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneJourneyKey() {
        return phoneJourneyKey;
    }

    public void setPhoneJourneyKey(String phoneJourneyKey) {
        this.phoneJourneyKey = phoneJourneyKey == null ? null : phoneJourneyKey.trim();
    }
}