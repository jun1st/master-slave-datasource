package com.github.jun1st.datasource.spring.boot.autoconfigure;

import com.github.jun1st.datasource.MSRoutingDataSource;
import com.github.jun1st.datasource.mybatis.MasterSlaveDataSourcePlugin;
import com.github.jun1st.datasource.mybatis.MybatisProperties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(
        name = {"spring.datasource.master-slave.mybatis"},
        havingValue = "true"
)
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class MyBatisMasterSlaveDataSourceAutoConfiguration {

    @Autowired
    private MybatisProperties properties;

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Autowired
    private MSRoutingDataSource masterSlaveRoutingDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        Interceptor rwplugin = new MasterSlaveDataSourcePlugin();
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        factory.setConfiguration(properties.getConfiguration());

        if (ObjectUtils.isEmpty(this.interceptors)) {
            Interceptor[] plugins = { rwplugin };
            factory.setPlugins(plugins);
        } else {
            List<Interceptor> interceptorList = Arrays.asList(interceptors);
            interceptorList.add(rwplugin);
            factory.setPlugins((Interceptor[]) interceptorList.toArray());
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            factory.setMapperLocations(this.properties.resolveMapperLocations());
        }

        factory.setDataSource(masterSlaveRoutingDataSource);

        return factory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(masterSlaveRoutingDataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        ExecutorType executorType = this.properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }
}
