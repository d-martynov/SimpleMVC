package ru.dmartynov.simplemvc.core.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.reflections.Reflections;
import ru.dmartynov.simplemvc.core.annotations.After;
import ru.dmartynov.simplemvc.core.annotations.Before;
import ru.dmartynov.simplemvc.core.annotations.With;
import ru.dmartynov.simplemvc.core.mvc.Controller;
import ru.dmartynov.simplemvc.core.utils.ReflectionUtil;
import spark.Request;
import spark.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class WithInterceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Class clazz = methodInvocation.getMethod().getDeclaringClass();
        With withAnnotation = (With) clazz.getAnnotation(With.class);

        for (Class<? extends Controller> withClass : withAnnotation.value()) {
            Controller withClassInstance = withClass.newInstance();
            List<Method> beforeMethods = ReflectionUtil.getMethodsAnnotatedWith(Before.class, withClass.getDeclaredMethods());

            for (Method beforeMethod : beforeMethods)
                beforeMethod.invoke(withClassInstance);

        }

        Object result = methodInvocation.proceed();

        for (Class<? extends Controller> withClass : withAnnotation.value()) {
            Controller withClassInstance = withClass.newInstance();
            List<Method> afterMethods = ReflectionUtil.getMethodsAnnotatedWith(After.class, withClass.getDeclaredMethods());

            for (Method afterMethod : afterMethods)
                afterMethod.invoke(withClassInstance);

        }
        return result;
    }
}
