
package com.stars.travel.datasource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 主从切面
 * @Author Joran
 * @Date 2014年8月4日 下午9:21:37 
 */
public class MasterSlaveAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(MasterSlaveAspect.class);

    private List<String> prefixMasters;

    @Autowired
    private MasterSlaveSelector masterSlaveSelector;

    public void before(JoinPoint point) {
        String method = point.getSignature().getName();
        if (isPrefix(method, prefixMasters)) {
            masterSlaveSelector.master();
            logger.debug("{} use write db .", method);
        } else {
            masterSlaveSelector.slave();
            logger.debug("{} use read db .", method);
        }
    }

    private boolean isPrefix(String name, List<String> prefixs) {
        boolean has = false;
        for (String prefix : prefixs) {
            if (name.startsWith(prefix)) {
                has = true;
                continue;
            }
        }
        return has;
    }

    /**
     * Setter method for property <tt>prefixMasters</tt>.
     * 
     * @param prefixMasters value to be assigned to property prefixMasters
     */
    public void setPrefixMasters(List<String> prefixMasters) {
        this.prefixMasters = prefixMasters;
    }

}
