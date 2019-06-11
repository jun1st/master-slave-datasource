package com.github.jun1st.datasource;

import com.github.jun1st.datasource.spring.boot.autoconfigure.hikari.MSHikariConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * @author fengde
 *
 */
@Slf4j
public class MSDataSourceCreator {

    private static final String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";


    private Method createMethod;
    private Method typeMethod;
    private Method urlMethod;
    private Method usernameMethod;
    private Method passwordMethod;
    private Method driverClassNameMethod;
    private Method buildMethod;

    private Boolean hikariExists = false;

    @Setter
    private MSHikariConfig msHikariConfig;

    public MSDataSourceCreator() {
        Class<?> builderClass = null;

        try {
            builderClass = Class.forName("org.springframework.boot.jdbc.DataSourceBuilder");
        } catch (Exception ex) {
            try {
                builderClass = Class.forName("org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            createMethod = builderClass.getDeclaredMethod("create");
            typeMethod = builderClass.getDeclaredMethod("type", Class.class);
            urlMethod = builderClass.getDeclaredMethod("url", String.class);
            usernameMethod = builderClass.getDeclaredMethod("username", String.class);
            passwordMethod = builderClass.getDeclaredMethod("password", String.class);
            driverClassNameMethod = builderClass.getDeclaredMethod("driverClassName", String.class);
            buildMethod = builderClass.getDeclaredMethod("build");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName(HIKARI_DATASOURCE);
            hikariExists = true;
        } catch (ClassNotFoundException e) {
        }
    }

    public DataSource create(DataSourceProperty dataSourceProperty) {
        if (hikariExists) {
            return createHikariDataSource(dataSourceProperty);
        }

        return createBasicDataSource(dataSourceProperty);
    }

    private DataSource createBasicDataSource(DataSourceProperty dataSourceProperty) {
        try {
            Object o1 = createMethod.invoke(null);
            Object o2 = typeMethod.invoke(o1, dataSourceProperty.getType());
            Object o3 = urlMethod.invoke(o2, dataSourceProperty.getUrl());
            Object o4 = usernameMethod.invoke(o3, dataSourceProperty.getUsername());
            Object o5 = passwordMethod.invoke(o4, dataSourceProperty.getPassword());
            Object o6 = driverClassNameMethod.invoke(o5, dataSourceProperty.getDriverClassName());
            return (DataSource) buildMethod.invoke(o6);
        } catch (Exception e) {
            throw new RuntimeException("多数据源创建数据源失败");
        }
    }

    private DataSource createHikariDataSource(DataSourceProperty dataSourceProperty ) {
        MSHikariConfig msHikariConfig = dataSourceProperty.getHikari();
        HikariConfig config = msHikariConfig.toHikariConfig(msHikariConfig);

        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());

        return new HikariDataSource(config);
    }
}
