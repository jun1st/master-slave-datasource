package com.marykay.mybiz.datasource;

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
public class DynamicDataSourceProperties {

    @NestedConfigurationProperty
    private DynamicItemDataSourceProperties master = new DynamicItemDataSourceProperties();

    @NestedConfigurationProperty
    private Map<String, DynamicItemDataSourceProperties> slave = new HashMap<>();
}
