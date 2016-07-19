package com.stars.travel.enums;

/**
 * Description :关注枚举值
 * Author : guo
 * Date : 2016/7/19 23:12
 */
public enum AttentionEnum {

    USER(AttentionEnumConstanst.USER_ID,"关注当地人");

    Short code;
    String description;

    AttentionEnum(Short code, String description){
        this.code = code;
        this.description = description;
    }


    /**
     * @Description : 根据类型ID获得类型描述
     * @param code
     * @return
     */
    public static String getDescription(Short code){
        switch (code){
            case 1:
                return AttentionEnum.USER.getDescription();
            default:
                return "";
        }
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    final static class AttentionEnumConstanst{

        public static final Short USER_ID = 1;
    }
}
