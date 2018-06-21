package com.focuspace.datasource;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MasterSlave {
    String value() default  "";
}
