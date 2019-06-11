package com.github.jun1st.datasource.strategy;

/**
 * @author fengde
 */
public interface MasterSlaveDataSourceStrategy {

    /**
     *
     * @param slaveDataSourceLookupKeys
     * @return String, a slave datasource key
     */
    String determineSlaveDataSource(String[] slaveDataSourceLookupKeys);

}
