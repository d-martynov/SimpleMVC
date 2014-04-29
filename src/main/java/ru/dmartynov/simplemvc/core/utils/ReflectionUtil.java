package ru.dmartynov.simplemvc.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class ReflectionUtil {
    public static List<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotationClass, Method[] methods) {
        List<Method> result = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                result.add(method);
            }
        }
        return result;
    }
}
