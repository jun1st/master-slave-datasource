package com.focuspace.datasource.aop;

import com.focuspace.datasource.MasterSlave;

import java.lang.reflect.Method;

public class MasterSlaveDataSourceAnnotationInterceptor extends AbstractMasterSlaveDataSourceInterceptor {

    @Override
    protected String determinDataSource(Method method, Object[] args, Object target) {
        MasterSlave masterSlave = method.getDeclaredAnnotation(MasterSlave.class);
        return masterSlave.value();
    }
}
