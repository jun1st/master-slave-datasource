package com.github.jun1st.datasource.strategy;

public interface MasterSlaveDataSourceStrategy {

    /**
     *
     * @param slaveDataSourceLookupKeys
     * @return slave id
     */
    String determineSlaveDataSource(String[] slaveDataSourceLookupKeys);

}
