package com.github.jun1st.datasource.mybatis;


import com.github.jun1st.datasource.MSDataSourceContextHolder;
import com.github.jun1st.datasource.MSTypes;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {
                        MappedStatement.class,
                        Object.class
                }
        ),
        @Signature(
                type = Executor.class,
                method = "query",
                args = {
                        MappedStatement.class,
                        Object.class,
                        RowBounds.class,
                        ResultHandler.class
                }
        )})
public class MSDataSourcePlugin implements Interceptor {

    // insert delete update结尾的操作作为写操作，其余，都当作读操作
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        if(!synchronizationActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];

            String dynamicDataSourceGlobal = null;

            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    dynamicDataSourceGlobal = MSTypes.MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        dynamicDataSourceGlobal = MSTypes.MASTER;
                    } else {
                        dynamicDataSourceGlobal = MSTypes.SLAVE;
                    }
                }
            } else {
                dynamicDataSourceGlobal = MSTypes.MASTER;
            }

            MSDataSourceContextHolder.setDataSourceLookupKey(dynamicDataSourceGlobal);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }
}
