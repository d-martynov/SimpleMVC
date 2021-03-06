package ru.dmartynov.simplemvc.core.annotations;

import java.lang.annotation.*;

/**
 * Created by Дмитрий on 29.04.2014.
 */

/**
 * Аннотацией обозначается метод, который будет выполняться после вызова любого метода контроллера.
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
}
