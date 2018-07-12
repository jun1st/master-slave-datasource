package cn.focuspace.datasource;

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
public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {

    private String[] slaveDataSourceLookupKeys;

    @Setter
    private MasterSlaveDataSourceProvider masterSlaveDataSourceProvider;
    @Setter
    private MasterSlaveDataSourceStrategy masterSlaveDataSourceStrategy;

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceLookupKey = MasterSlaveDataSourceContextHolder.getDataSourceLookupKey();
        if (dataSourceLookupKey != null && dataSourceLookupKey.equals(MasterSlaveTypes.SLAVE)) {
            dataSourceLookupKey = masterSlaveDataSourceStrategy.determineSlaveDataSource(slaveDataSourceLookupKeys);
        } else {
            dataSourceLookupKey = MasterSlaveTypes.MASTER;
        }


        log.debug("determine to use datasource named : {}", dataSourceLookupKey);
        return dataSourceLookupKey;
    }

    @Override
    public void afterPropertiesSet() {
        DataSource masterDataSource = masterSlaveDataSourceProvider.loadMaster();
        Map<String, DataSource> slaveDataSource = masterSlaveDataSourceProvider.loadSlaves();

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
