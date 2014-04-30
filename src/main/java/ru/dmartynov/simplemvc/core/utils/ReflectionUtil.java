package ru.dmartynov.simplemvc.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class ReflectionUtil {

    /**
     * Фильтрует методы, оставляя только аннатированные заданной аннотацией.
     * @param annotationClass аннотация
     * @param methods методы
     * @return результат
     */
    public static List<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotationClass, Method[] methods) {
        List<Method> result = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                result.add(method);
            }
        }
        return result;
    }

    /**
     * Возвращает первый метод, с заданным именем.
     * @param methodName имя метода
     * @param methods методы
     * @return метод
     */
    public static Method getFirstMethodWithName(String methodName, Method[] methods) {
        for (Method method : methods)
            if (method.getName().equals(methodName))
                return method;

        return null;
    }
}
