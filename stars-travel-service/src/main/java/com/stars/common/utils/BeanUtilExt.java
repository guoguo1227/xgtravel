package com.stars.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : 属性拷贝工具类
 * Author : guo
 * Date : 2016/3/30 22:25
 */
public class BeanUtilExt extends BeanUtils {

    private static Map cache = new HashMap();
    private static Log logger = LogFactory.getFactory().getInstance(BeanUtilExt.class);

    private BeanUtilExt() {
    }

    static {
    // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null), java.sql.Timestamp.class);
    // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.StringConverter(),java.lang.String.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.IntegerConverter(0),java.lang.Integer.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.BooleanConverter(false),java.lang.Boolean.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.LongConverter(0),java.lang.Long.class);
    }

    public static void copyProperties(Object target, Object source)
            throws InvocationTargetException, IllegalAccessException {
    // 支持对日期copy
        BeanUtils.copyProperties(target, source);

    }

}