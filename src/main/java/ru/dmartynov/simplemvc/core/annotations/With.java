package ru.dmartynov.simplemvc.core.annotations;

import ru.dmartynov.simplemvc.core.mvc.Controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Дмитрий on 30.04.2014.
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface With {
    Class<? extends Controller>[] value();
}
