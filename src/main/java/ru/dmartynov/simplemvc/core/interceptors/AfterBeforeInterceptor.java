package ru.dmartynov.simplemvc.core.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ru.dmartynov.simplemvc.core.annotations.After;
import ru.dmartynov.simplemvc.core.annotations.Before;
import ru.dmartynov.simplemvc.core.utils.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * Created by Дмитрий on 29.04.2014.
 */
public class AfterBeforeInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //Получаем контроллер
        Class clazz = methodInvocation.getMethod().getDeclaringClass();

        //Выполняем before методы
        for (Method method : ReflectionUtil.getMethodsAnnotatedWith(Before.class, clazz.getDeclaredMethods()))
            method.invoke(methodInvocation.getThis());

        //Выполняем сам метод
        Object result = methodInvocation.proceed();

        //Выполняем after методы
        for (Method method : ReflectionUtil.getMethodsAnnotatedWith(After.class, clazz.getDeclaredMethods()))
            method.invoke(methodInvocation.getThis());

        return result;
    }
}
