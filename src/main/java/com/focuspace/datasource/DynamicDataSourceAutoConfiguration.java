package com.focuspace.datasource;

import com.focuspace.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.focuspace.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Dynamic datasource configuration, configure before default database
 * only enable master slave setting if master-slave is on
 * @author fengde
 */
@ConditionalOnProperty(name = "spring.datasource.master-slave", havingValue = "true")
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

    private final DynamicDataSourceProperties properties;

    public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    /**
     * Dynamic Datasource Strategy
     *
     * @return a RoundRobin strategy by default
     */
    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceStrategy dynamicDataSourceStrategy() {
        return new RoundRobinDynamicDataSourceStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new YamlDataSourceProvider(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicDataSource(
            DynamicDataSourceProvider dynamicDataSourceProvider,
            DynamicDataSourceStrategy dynamicDataSourceStrategy) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setDynamicDataSourceProvider(dynamicDataSourceProvider);
        dynamicRoutingDataSource.setDynamicDataSourceStrategy(dynamicDataSourceStrategy);
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor();
        advisor.setAdvice(new DynamicDataSourceAnnotationInterceptor());
        advisor.setOrder(Integer.MIN_VALUE);
        return advisor;
    }
}
