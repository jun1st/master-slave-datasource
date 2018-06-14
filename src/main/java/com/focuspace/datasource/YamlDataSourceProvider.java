package com.focuspace.datasource;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengde
 */
public class YamlDataSourceProvider extends AbstractDynamicDataSourceProvider implements DynamicDataSourceProvider {

    private DynamicDataSourceProperties properties;

    public YamlDataSourceProvider(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public DataSource loadMaster() {
        return create(properties.getMaster());
    }

    @Override
    public Map<String, DataSource> loadSlaves() {
        Map<String, DynamicItemDataSourceProperties> slaves = properties.getSlave();
        Map<String, DataSource> dataSourceMap = new HashMap<>(slaves.size());
        slaves.forEach((k, v) -> dataSourceMap.put(k, create(v)));
        return dataSourceMap;
    }
}
