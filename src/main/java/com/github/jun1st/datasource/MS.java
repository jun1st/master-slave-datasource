package com.github.jun1st.datasource;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MS {
    String value() default  "";
}
