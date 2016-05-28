package com.stars.travel.model.base;

public class UserCollectionKey {
    private Integer id;

    private String phoneCollectionKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneCollectionKey() {
        return phoneCollectionKey;
    }

    public void setPhoneCollectionKey(String phoneCollectionKey) {
        this.phoneCollectionKey = phoneCollectionKey == null ? null : phoneCollectionKey.trim();
    }
}