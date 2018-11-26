package com.github.jun1st.datasource.provider;


import com.github.jun1st.datasource.MasterSlaveDataSourceProperties;
import com.github.jun1st.datasource.MasterSlaveItemDataSourceProperties;
import com.github.jun1st.datasource.provider.AbstractMasterSlaveDataSourceProvider;
import com.github.jun1st.datasource.provider.MasterSlaveDataSourceProvider;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengde
 */
public class YamlDataSourceProvider extends AbstractMasterSlaveDataSourceProvider implements MasterSlaveDataSourceProvider {

    private MasterSlaveDataSourceProperties properties;

    public YamlDataSourceProvider(MasterSlaveDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource loadMaster() {
        return create(properties.getMaster());
    }

    @Override
    public Map<String, DataSource> loadSlaves() {
        Map<String, MasterSlaveItemDataSourceProperties> slaves = properties.getSlave();
        Map<String, DataSource> dataSourceMap = new HashMap<>(slaves.size());
        slaves.forEach((k, v) -> dataSourceMap.put(k, create(v)));
        return dataSourceMap;
    }
}
