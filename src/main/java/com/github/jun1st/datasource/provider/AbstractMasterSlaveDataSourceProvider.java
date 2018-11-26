package com.github.jun1st.datasource.provider;

import com.github.jun1st.datasource.MasterSlaveItemDataSourceProperties;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public abstract class AbstractMasterSlaveDataSourceProvider implements MasterSlaveDataSourceProvider {

    protected DataSource create(MasterSlaveItemDataSourceProperties properties) {
        Class<? extends DataSource> type = properties.getType();

        return properties.initializeDataSourceBuilder().build();
    }
}
