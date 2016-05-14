package com.stars.travel.model.ext;

/**
 * Description : 返回结果对象
 * Author : guo
 * Date : 2016/1/17 22:54
 */
public class RequestResult {

    private  Boolean success; //是否成功

    private  Object data;     //返回数据

    private  String message;  //返回提示信息

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
