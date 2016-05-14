package com.stars.common.utils;

import java.util.UUID;

/**
 * Description : 用于生成唯一值uuid作为一个唯一的标识，实现了一个生成uuid的接口
 * Author : guo
 * Date : 2016/1/24 14:03
 */
public class UuidGenerator {

    /**
     * getUUID
     * @描述: 通过jdk1.5及以上版本提供的UUID自动生成UUID串
     * @作者: gzs
     * @创建时间: 2014-12-1下午01:51:02
     *
     * @修改描述: 无
     * @修改人: gzs
     * @修改时间: 2014-12-1下午01:51:02
     * @return
     *  生成的uuid字符串
     */

    public static String getUUID() {

        // UUID生成接口产生uuid串
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        return uuidString;
    }
}
