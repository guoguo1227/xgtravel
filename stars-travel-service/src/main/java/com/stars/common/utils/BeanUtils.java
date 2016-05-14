package com.stars.common.utils;


import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * Description :
 * Author : guo
 * Date : 2016/4/21 22:46
 */
public class BeanUtils extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value == null) {
            return;
        }
        super.copyProperty(bean, name, value);
    }
}
