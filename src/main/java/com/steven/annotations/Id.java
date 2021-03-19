package com.steven.annotations;


import org.omg.SendingContext.RunTime;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

    String name() default "";
}
