
package com.stars.travel.datasource;

import java.lang.annotation.*;

/**
 * 主库配置注解
 * @Author Joran
 * @Date 2014年8月5日 上午11:59:37 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Master {
    
}
