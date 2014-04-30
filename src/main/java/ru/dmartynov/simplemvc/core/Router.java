package ru.dmartynov.simplemvc.core;

import ru.dmartynov.simplemvc.core.annotations.SetRoute;
import ru.dmartynov.simplemvc.core.utils.ReflectionUtil;

import java.lang.reflect.Method;

public class Router {
    private Method method;

    public Router(Method method) {
        this.method = method;
    }

    private String getRoute(Method method) {
        String route;
        SetRoute controllerRoute = method.getDeclaringClass().getAnnotation(SetRoute.class);
        if (controllerRoute != null) {
            route = controllerRoute.value();
        } else {
            route = method.getDeclaringClass().getSimpleName().toLowerCase();
        }

        SetRoute methodRoute = method.getAnnotation(SetRoute.class);
        if (methodRoute != null) {
            route += "/" + methodRoute.value();
        } else {
            route += "/" + method.getName().toLowerCase();
        }
        return "/" + route;
    }

    public String getRoute() {
        return getRoute(method);
    }

    public String getRoute(String methodName) {
        Method result = ReflectionUtil.getFirstMethodWithName(methodName, method.getDeclaringClass().getDeclaredMethods());
        if (result == null)
            throw new IllegalArgumentException("Метод " + methodName + " в контроллере " + method.getDeclaringClass().getName()
                    + " не найден");
        return getRoute(result);
    }

}
