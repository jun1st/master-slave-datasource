package com.focuspace.datasource;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pick slave datasource one after one
 * @author fengde
 */
public class RoundRobinDynamicDataSourceStrategy implements DynamicDataSourceStrategy {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public String determineSlaveDataSource(String[] slaveDataSourceLookupKeys) {
        int next = count.getAndAdd(1);

        return slaveDataSourceLookupKeys[next % slaveDataSourceLookupKeys.length];
    }
}
