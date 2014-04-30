package ru.dmartynov.simplemvc.core.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ru.dmartynov.simplemvc.core.Router;
import ru.dmartynov.simplemvc.core.mvc.Controller;
import spark.Response;

import java.lang.reflect.Method;


public class RedirectInterceptor implements MethodInterceptor {
    private Response response;

    public RedirectInterceptor(Response response) {
        this.response = response;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
       if (!isFromController()) {
           return methodInvocation.proceed();
       } else {
           Method calledMethod = methodInvocation.getMethod();
           Router router = new Router(calledMethod);
           response.redirect(router.getRoute());
           return null;
       }
    }


    private boolean isFromController() throws Exception {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stack) {
            Class<?> clazz = Class.forName(stackTraceElement.getClassName());
            if (Controller.class.isAssignableFrom(clazz) && !stackTraceElement.getFileName().equals("<generated>"))
                return true;
        }
        return false;
    }
}
