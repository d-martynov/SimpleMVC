package ru.dmartynov.simplemvc.core.annotations;

import spark.route.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Дмитрий on 30.04.2014.
 */

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    HttpMethod value() default HttpMethod.get;
}



