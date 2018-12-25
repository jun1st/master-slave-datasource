package com.github.jun1st.datasource.mybatis;

import com.github.jun1st.datasource.MSDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

@Slf4j
public class MybatisMSTransactionManager extends DataSourceTransactionManager {

    public MybatisMSTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        boolean readOnly = definition.isReadOnly();
        if (readOnly) {
            logger.debug(" readonly: slave");
            MSDataSourceContextHolder.setDataSourceLookupKey("slave");
        } else {
            logger.debug("write: master");
            MSDataSourceContextHolder.setDataSourceLookupKey("master");
        }

        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);

        MSDataSourceContextHolder.clearDataSourceLookupKey();
    }
}
