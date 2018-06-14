package com.focuspace.datasource.aop;

import com.focuspace.datasource.Dynamic;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

public class DynamicDataSourceAnnotationAdvisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> aClass) {
        if (method.isAnnotationPresent(Dynamic.class)) {
            return true;
        }
        return false;
    }
}
