package com.marykay.mybiz.datasource.aop;

import com.marykay.mybiz.datasource.DynamicDataSourceContextHolder;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * User AOP to intercept @Dynamic annotation,
 * determine the lookup key, use master or slave database
 * @author fengde
 */
public abstract class AbstractDynamicDataSourceInterceptor implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String datasource = determinDataSource(method, objects, o);
        DynamicDataSourceContextHolder.setDataSourceLookupKey(datasource);
    }


    protected abstract String determinDataSource(Method method, Object[] args, Object target);
}
