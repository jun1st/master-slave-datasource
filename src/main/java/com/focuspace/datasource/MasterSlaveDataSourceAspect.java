package com.focuspace.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(-10)
public class MasterSlaveDataSourceAspect {

    @Before("@annotation(ms)")
    public void chooseDataSource(JoinPoint point, MasterSlave ms) {

        String typeValue = ms.value();

        // value must be "master" or "slave"
        // all set to "master" unless value is "slave"
        if (!typeValue.equals(MasterSlaveTypes.SLAVE)) {
            typeValue = MasterSlaveTypes.MASTER;
        }

        MasterSlaveDataSourceContextHolder.setDataSourceLookupKey(typeValue);
    }

    @After("@annotation(ms)")
    public void clearDataSource(JoinPoint point, MasterSlave ms) {
        MasterSlaveDataSourceContextHolder.clearDataSourceLookupKey();
    }


}
