package com.focuspace.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengde
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class MasterSlaveDataSourceProperties {

    @NestedConfigurationProperty
    private MasterSlaveItemDataSourceProperties master = new MasterSlaveItemDataSourceProperties();

    @NestedConfigurationProperty
    private Map<String, MasterSlaveItemDataSourceProperties> slave = new HashMap<>();
}
