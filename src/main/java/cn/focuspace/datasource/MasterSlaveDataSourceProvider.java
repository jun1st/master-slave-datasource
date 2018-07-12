package cn.focuspace.datasource;

import javax.sql.DataSource;
import java.util.Map;

public interface MasterSlaveDataSourceProvider {

    DataSource loadMaster();

    Map<String, DataSource> loadSlaves();

}
