package com.stars.common.enums;

/**
 * Description :收藏，顶赞类型
 * Author : guo
 * Date : 2016/4/12 22:53
 */
public enum  CollectionTopType {

    COLLECTION(1,"收藏"),
    TOP(2,"顶，赞");

    Integer code;
    String description;

    CollectionTopType(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
