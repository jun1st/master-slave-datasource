package com.github.jun1st.datasource.strategy;


import com.github.jun1st.datasource.strategy.MasterSlaveDataSourceStrategy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pick slave datasource one after one
 * @author fengde
 */
public class RoundRobinMasterSlaveDataSourceStrategy implements MasterSlaveDataSourceStrategy {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public String determineSlaveDataSource(String[] slaveDataSourceLookupKeys) {
        int next = count.getAndAdd(1);

        return slaveDataSourceLookupKeys[next % slaveDataSourceLookupKeys.length];
    }
}
