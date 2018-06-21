package com.focuspace.datasource.aop;

import com.focuspace.datasource.MasterSlaveDataSourceContextHolder;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * User AOP to intercept @MasterSlave annotation,
 * determine the lookup key, use master or slave database
 * @author fengde
 */
public abstract class AbstractMasterSlaveDataSourceInterceptor implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String datasource = determinDataSource(method, objects, o);
        MasterSlaveDataSourceContextHolder.setDataSourceLookupKey(datasource);
    }


    protected abstract String determinDataSource(Method method, Object[] args, Object target);
}
