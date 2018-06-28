package com.focuspace.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;

@Slf4j
public abstract class AbstractMasterSlaveDataSourceProvider implements MasterSlaveDataSourceProvider {

    protected DataSource create(MasterSlaveItemDataSourceProperties properties) {
        Class<? extends DataSource> type = properties.getType();

        return properties.initializeDataSourceBuilder().build();

//        if (type == null) {
//            try {
//                Class.forName("com.zaxxer.hikari.HikariDataSource");
//                return createHikariDataSource(properties);
//            } catch (ClassNotFoundException e) {
//                log.debug("dynamic not found HikariDataSource");
//            }
//            throw new RuntimeException("please set master and slave type like spring.dynamic.datasource.master.type");
//        } else {
//            return properties.initializeDataSourceBuilder().build();
//        }
    }


//    private DataSource createHikariDataSource(DataSourceProperties properties) {
//        properties.setType(HikariDataSource.class);
//        return properties.initializeDataSourceBuilder().build();
//    }
}
