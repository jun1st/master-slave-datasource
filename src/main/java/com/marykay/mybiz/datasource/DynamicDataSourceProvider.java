package com.marykay.mybiz.datasource;

import javax.sql.DataSource;
import java.util.Map;

public interface DynamicDataSourceProvider {

    DataSource loadMaster();

    Map<String, DataSource> loadSlaves();

}
