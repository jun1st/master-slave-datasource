package com.github.jun1st.datasource;

import com.github.jun1st.datasource.spring.boot.autoconfigure.hikari.MSHikariConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.activation.DataSource;

/**
 * Custom data source configuration properties
 * @author fengde
 */

@Data
@Accessors(chain = true)
public class DataSourceProperty {

    private Class<? extends DataSource> type;
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;
    /**
     * jndi数据源名称(设置即表示启用)
     */
    private String jndiName;

    /**
     * HikariCp参数配置
     */
    @NestedConfigurationProperty
    private MSHikariConfig hikari = new MSHikariConfig();

}
