package com.focuspace.datasource.aop;

import com.focuspace.datasource.Dynamic;

import java.lang.reflect.Method;

public class DynamicDataSourceAnnotationInterceptor extends AbstractDynamicDataSourceInterceptor {

    @Override
    protected String determinDataSource(Method method, Object[] args, Object target) {
        Dynamic dynamic = method.getDeclaredAnnotation(Dynamic.class);
        return dynamic.value();
    }
}
