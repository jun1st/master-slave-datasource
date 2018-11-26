package com.github.jun1st.datasource.aop;

import com.github.jun1st.datasource.MS;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author fengde
 */
public class MasterSlaveDataSourceAnnotationAdvisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        if (method.isAnnotationPresent(MS.class)) {
            return true;
        }
        return false;
    }
}
