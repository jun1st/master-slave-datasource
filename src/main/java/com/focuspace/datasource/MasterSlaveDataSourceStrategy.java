package com.focuspace.datasource;

public interface MasterSlaveDataSourceStrategy {

    /**
     *
     * @param slaveDataSourceLookupKeys
     * @return slave id
     */
    String determineSlaveDataSource(String[] slaveDataSourceLookupKeys);

}
