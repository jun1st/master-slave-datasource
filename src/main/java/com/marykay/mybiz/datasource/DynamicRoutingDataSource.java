package com.marykay.mybiz.datasource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fengde
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private String[] slaveDataSourceLookupKeys;

    @Setter
    private DynamicDataSourceProvider dynamicDataSourceProvider;
    @Setter
    private DynamicDataSourceStrategy dynamicDataSourceStrategy;

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceLookupKey = DynamicDataSourceContextHolder.getDataSourceLookupKey();
        DynamicDataSourceContextHolder.clearDataSourceLookupKey();
        if (dataSourceLookupKey != null && dataSourceLookupKey.equals("slave")) {
            dataSourceLookupKey = dynamicDataSourceStrategy.determineSlaveDataSource(slaveDataSourceLookupKeys);
        } else {
            dataSourceLookupKey = "master";
        }

        if (!dataSourceLookupKey.equals("master")) {
            //log.info(dataSourceLookupKey);
        }


        log.debug("determine to use datasource named : {}", dataSourceLookupKey);
        return dataSourceLookupKey;
    }

    @Override
    public void afterPropertiesSet() {
        DataSource masterDataSource = dynamicDataSourceProvider.loadMaster();
        Map<String, DataSource> slaveDataSource = dynamicDataSourceProvider.loadSlaves();

        Set<String> slaveDataSourceIds = slaveDataSource.keySet();
        this.slaveDataSourceLookupKeys = slaveDataSourceIds.toArray(new String[slaveDataSource.size()]);

        Map<Object, Object> targetDataSource = new HashMap<>(slaveDataSource.size() + 1);
        targetDataSource.put("master", masterDataSource);
        targetDataSource.putAll(slaveDataSource);
        super.setDefaultTargetDataSource(masterDataSource);
        super.setTargetDataSources(targetDataSource);

        super.afterPropertiesSet();
    }
}
