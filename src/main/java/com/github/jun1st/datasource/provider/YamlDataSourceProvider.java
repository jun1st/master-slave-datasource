package com.github.jun1st.datasource.provider;


import com.github.jun1st.datasource.MSDataSourceCreator;
import com.github.jun1st.datasource.MSDataSourceProperties;
import com.github.jun1st.datasource.DataSourceProperty;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fengde
 */
public class YamlDataSourceProvider implements MSDataSourceProvider {

    /*
     * 多数据源参数属性
     */
    private MSDataSourceProperties properties;

    /*
    * 数据库创建器
     */
    private MSDataSourceCreator msDataSourceCreator;

    public YamlDataSourceProvider(MSDataSourceProperties properties, MSDataSourceCreator dataSourceCreator) {
        this.properties = properties;
        this.msDataSourceCreator = dataSourceCreator;
    }

    @Override
    public DataSource loadMaster() {
        DataSourceProperty property = properties.getMaster();
        return msDataSourceCreator.create(property);
    }

    @Override
    public Map<String, DataSource> loadSlaves() {
        Map<String, DataSourceProperty> slaves = properties.getSlave();
        Map<String, DataSource> dataSourceMap = new HashMap<>(slaves.size());
        slaves.forEach((k, v) -> dataSourceMap.put(k, msDataSourceCreator.create(v)));
        return dataSourceMap;
    }
}
