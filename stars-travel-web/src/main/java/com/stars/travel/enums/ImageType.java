package com.stars.travel.enums;

/**
 * Description : 图片类型枚举值
 * Author : guo
 * Date : 2016/1/27 23:56
 */
public enum ImageType {

    UPLOAD_USER(ImageTypeConstants.UPLOAD_USER_CODE,"/upload/user"), //用户头像，图片上传路径
    UPLAOD_USER_LOCAL(ImageTypeConstants.UPLAOD_USER_LOCAL_CODE,"/upload/local"), //当地人图片
    UPLAOD_USER_JOURNEY(ImageTypeConstants.UPLAOD_USER_JOURNEY_CODE,"/upload/journey"), //行程分享图片
    UPLAOD_USER_MICROBLOG(ImageTypeConstants.UPLAOD_USER_MICROBLOG_CODE,"/upload/microblog"); // 微游记图片

    Integer code;
    String imagePath;

    public static String getImagePath(int code){
        switch (code){
            case 1:
                return UPLOAD_USER.getImagePath();
            case 2:
                return UPLAOD_USER_LOCAL.getImagePath();
            case 3:
                return UPLAOD_USER_JOURNEY.getImagePath();
            case 4:
                return UPLAOD_USER_MICROBLOG.getImagePath();
            default:
                return "upload";
        }
    }

    ImageType(Integer code, String imagePath){
        this.code = code;
        this.imagePath = imagePath;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    final static class ImageTypeConstants{

        public static final Integer UPLOAD_USER_CODE = 1; //用户头像，图片上传路径

        public static final Integer UPLAOD_USER_LOCAL_CODE = 2; //当地人图片

        public static final Integer UPLAOD_USER_JOURNEY_CODE = 3; //行程分享图片

        public static final Integer UPLAOD_USER_MICROBLOG_CODE = 4; // 微游记图片
    }
}
