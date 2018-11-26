package com.github.jun1st.datasource.aop;

import com.github.jun1st.datasource.MS;

import java.lang.reflect.Method;

public class MasterSlaveDataSourceAnnotationInterceptor extends AbstractMasterSlaveDataSourceInterceptor {

    @Override
    protected String determinDataSource(Method method, Object[] args, Object target) {
        MS MS = method.getDeclaredAnnotation(MS.class);
        return MS.value();
    }
}
