package com.stars.travel.model.base;

public class MicroblogCollectionKey {
    private Integer id;

    private String phoneMicroblogKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneMicroblogKey() {
        return phoneMicroblogKey;
    }

    public void setPhoneMicroblogKey(String phoneMicroblogKey) {
        this.phoneMicroblogKey = phoneMicroblogKey == null ? null : phoneMicroblogKey.trim();
    }
}