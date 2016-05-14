package com.stars.travel.model.base;

public class JourneyWithBLOBs extends Journey {
    private static final long serialVersionUID = 1L;

    private String description;

    private String content;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}