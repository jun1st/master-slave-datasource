package com.github.jun1st.datasource.strategy;

public interface MasterSlaveDataSourceStrategy {
    
    String determineSlaveDataSource(String[] slaveDataSourceLookupKeys);

}
