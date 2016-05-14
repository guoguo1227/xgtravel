package com.stars.common.enums;

/**
 * Description : 评论类型枚举
 * Author : guo
 * Date : 2016/4/8 22:11
 */
public enum CommentTypeEnum {

    JOURNEYTYPE(1,"行程"),
    MICROBLOG(2,"微游记"),
    LOCALUSER(3,"当地人");

    Integer code;
    String description;

    CommentTypeEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    /**
     * @Description : 根据id获取枚举值
     * @param code
     * @return
     */
    public static String getCommentTypeByCode(Integer code){
        switch(code){
            case 1:
                return CommentTypeEnum.JOURNEYTYPE.getDescription();
            case 2:
                return CommentTypeEnum.MICROBLOG.getDescription();
            case 3:
                return CommentTypeEnum.LOCALUSER.getDescription();
            default:
                throw new IllegalArgumentException("类型:"+code+"的枚举不存在");
        }
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
