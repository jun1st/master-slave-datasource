package com.focuspace.datasource;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomly pick a slave as datasource
 * @author fengde
 */
public class RandomDataSourceStrategy implements MasterSlaveDataSourceStrategy {

    @Override
    public String determineSlaveDataSource(String[] slaveDataSourceLookupKeys) {
        int i = ThreadLocalRandom.current().nextInt(slaveDataSourceLookupKeys.length);

        return slaveDataSourceLookupKeys[i];
    }
}
