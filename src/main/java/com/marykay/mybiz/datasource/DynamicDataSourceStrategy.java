package com.marykay.mybiz.datasource;

public interface DynamicDataSourceStrategy {

    /**
     *
     * @param slaveDataSourceLookupKeys
     * @return slave id
     */
    String determineSlaveDataSource(String[] slaveDataSourceLookupKeys);

}
