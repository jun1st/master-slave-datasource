package com.focuspace.datasource;

import com.focuspace.datasource.aop.MasterSlaveDataSourceAnnotationAdvisor;
import com.focuspace.datasource.aop.MasterSlaveDataSourceAnnotationInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MasterSlave datasource configuration, configure before default database
 * only enable master slave setting if master-slave is on
 * @author fengde
 */
@ConditionalOnProperty(name = "spring.datasource.master-slave", havingValue = "true")
@Configuration
@EnableConfigurationProperties(MasterSlaveDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MasterSlaveDataSourceAutoConfiguration {

    private final MasterSlaveDataSourceProperties properties;

    public MasterSlaveDataSourceAutoConfiguration(MasterSlaveDataSourceProperties properties) {
        this.properties = properties;
    }

    /**
     * MasterSlave Datasource Strategy
     *
     * @return a RoundRobin strategy by default
     */
    @Bean
    @ConditionalOnMissingBean
    public MasterSlaveDataSourceStrategy dynamicDataSourceStrategy() {
        return new RoundRobinMasterSlaveDataSourceStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public MasterSlaveDataSourceProvider dynamicDataSourceProvider() {
        return new YamlDataSourceProvider(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public MasterSlaveRoutingDataSource dynamicDataSource(
            MasterSlaveDataSourceProvider masterSlaveDataSourceProvider,
            MasterSlaveDataSourceStrategy masterSlaveDataSourceStrategy) {
        MasterSlaveRoutingDataSource masterSlaveRoutingDataSource = new MasterSlaveRoutingDataSource();
        masterSlaveRoutingDataSource.setMasterSlaveDataSourceProvider(masterSlaveDataSourceProvider);
        masterSlaveRoutingDataSource.setMasterSlaveDataSourceStrategy(masterSlaveDataSourceStrategy);
        return masterSlaveRoutingDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public MasterSlaveDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        MasterSlaveDataSourceAnnotationAdvisor advisor = new MasterSlaveDataSourceAnnotationAdvisor();
        advisor.setAdvice(new MasterSlaveDataSourceAnnotationInterceptor());
        advisor.setOrder(Integer.MIN_VALUE);
        return advisor;
    }
}
