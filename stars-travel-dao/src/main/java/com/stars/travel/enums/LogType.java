package com.stars.travel.enums;

/**
 * Description :
 * Author : guo
 * Date : 2016/1/27 23:56
 */
public enum LogType {

    USER_LOGIN(LogTypeConstanst.USER_LOGIN_ID,"用户登录"),
    USER_REGISTER(LogTypeConstanst.USER_REGISTER_ID,"用户注册"),
    USER_DELETE(LogTypeConstanst.USER_DELETE_ID,"删除用户"),
    USER_RESTORE(LogTypeConstanst.USER_RESTORE_ID,"恢复用户");

    Integer code;
    String description;

    LogType(Integer code, String description){
        this.code = code;
        this.description = description;
    }


    /**
     * @Description : 根据类型ID获得类型描述
     * @param code
     * @return
     */
    public static String getDescription(int code){
        switch (code){
            case 1:
                return LogType.USER_LOGIN.getDescription();
            case 2:
                return LogType.USER_REGISTER.getDescription();
            default:
                return "";
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

    final static class LogTypeConstanst{
        public static final Integer USER_LOGIN_ID = 1;
        public static final Integer USER_REGISTER_ID = 2;
        public static final Integer USER_DELETE_ID = 3;
        public static final Integer USER_RESTORE_ID = 4;
    }
}
