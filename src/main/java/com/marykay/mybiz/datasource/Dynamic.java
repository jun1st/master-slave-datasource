package com.marykay.mybiz.datasource;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dynamic {
    String value() default  "";
}
