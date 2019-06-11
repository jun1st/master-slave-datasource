package com.github.jun1st.datasource;

import com.github.jun1st.datasource.spring.boot.autoconfigure.hikari.MSHikariConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengde
 *
 * it will merge hikari configs into all database connection pools
 * spring:
 *     datasource:
 *         master-slave:
 *             master:
 *                 xxxxx:
 *                 xxxxx:
 *             slave:
 *                 one:
 *                     xxxx:
 *                     xxxx:
 *             hikari:
 *                 jdbc-url:
 */

@Data
@ConfigurationProperties(prefix = "spring.datasource.master-slave")
public class MSDataSourceProperties {

    private String primary = "master";

    @NestedConfigurationProperty
    private MSHikariConfig hikari = new MSHikariConfig();

    @NestedConfigurationProperty
    private DataSourceProperty master = new DataSourceProperty();

    @NestedConfigurationProperty
    private Map<String, DataSourceProperty> slave = new HashMap<>();

}
