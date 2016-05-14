package com.stars.travel.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 主从数据源
 * @Author Joran
 * @Date 2014年8月4日 下午7:34:37 
 */
public class MasterSlaveDataSource extends AbstractRoutingDataSource{
    
    private MasterSlaveSelector masterSlaveSelector;
    
    /** 
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return masterSlaveSelector.get();
    }
    
    /**
     * Setter method for property <tt>masterSlaveSelector</tt>.
     * 
     * @param masterSlaveSelector value to be assigned to property masterSlaveSelector
     */
    public void setMasterSlaveSelector(MasterSlaveSelector masterSlaveSelector) {
        this.masterSlaveSelector = masterSlaveSelector;
    }
}
