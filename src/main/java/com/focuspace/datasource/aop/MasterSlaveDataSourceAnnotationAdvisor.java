package com.focuspace.datasource.aop;

import com.focuspace.datasource.MasterSlave;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author fengde
 */
public class MasterSlaveDataSourceAnnotationAdvisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        if (method.isAnnotationPresent(MasterSlave.class)) {
            return true;
        }
        return false;
    }
}
