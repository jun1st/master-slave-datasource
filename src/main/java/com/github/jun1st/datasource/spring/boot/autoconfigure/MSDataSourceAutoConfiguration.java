package com.github.jun1st.datasource.spring.boot.autoconfigure;

import com.github.jun1st.datasource.*;
import com.github.jun1st.datasource.provider.MSDataSourceProvider;
import com.github.jun1st.datasource.provider.YamlDataSourceProvider;
import com.github.jun1st.datasource.strategy.MasterSlaveDataSourceStrategy;
import com.github.jun1st.datasource.strategy.RoundRobinMasterSlaveDataSourceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MS datasource configuration, configure before default database
 * only enable master slave setting if master-slave is on
 * @author fengde
 */
@ConditionalOnProperty(name = "spring.master-slave", havingValue = "true")
@Configuration
@EnableConfigurationProperties(MSDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MSDataSourceAutoConfiguration {

    @Autowired
    private MSDataSourceProperties properties;

    /**
     * MS Datasource Strategy
     *
     * @return a RoundRobin strategy by default
     */
    @Bean
    @ConditionalOnMissingBean
    public MasterSlaveDataSourceStrategy masterSlaveDataSourceStrategy() {
        return new RoundRobinMasterSlaveDataSourceStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    public MSDataSourceCreator msDataSourceCreator() {
        MSDataSourceCreator msDataSourceCreator = new MSDataSourceCreator();
        msDataSourceCreator.setMsHikariConfig(properties.getHikari());
        return msDataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public MSDataSourceProvider masterSlaveDataSourceProvider(MSDataSourceCreator dataSourceCreator) {
        return new YamlDataSourceProvider(properties, dataSourceCreator);
    }

    @Bean
    @ConditionalOnMissingBean
    public MSRoutingDataSource dynamicDataSource(
            MSDataSourceProvider MSDataSourceProvider,
            MasterSlaveDataSourceStrategy masterSlaveDataSourceStrategy) {
        MSRoutingDataSource masterSlaveRoutingDataSource = new MSRoutingDataSource();
        masterSlaveRoutingDataSource.setMsDataSourceProvider(MSDataSourceProvider);
        masterSlaveRoutingDataSource.setMasterSlaveDataSourceStrategy(masterSlaveDataSourceStrategy);
        return masterSlaveRoutingDataSource;
    }
}
