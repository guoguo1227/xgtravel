package com.stars.travel.model.base;

public class AttentionKey {
    private Integer id;

    private String relateUserKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelateUserKey() {
        return relateUserKey;
    }

    public void setRelateUserKey(String relateUserKey) {
        this.relateUserKey = relateUserKey == null ? null : relateUserKey.trim();
    }
}