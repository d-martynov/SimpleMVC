package ru.dmartynov.simplemvc.core.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import ru.dmartynov.simplemvc.core.annotations.After;
import ru.dmartynov.simplemvc.core.annotations.Before;
import ru.dmartynov.simplemvc.core.annotations.With;
import ru.dmartynov.simplemvc.core.interceptors.AfterBeforeInterceptor;
import ru.dmartynov.simplemvc.core.interceptors.RedirectInterceptor;
import ru.dmartynov.simplemvc.core.interceptors.WithInterceptor;
import ru.dmartynov.simplemvc.core.mvc.Controller;
import spark.Request;
import spark.Response;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Дмитрий on 29.04.2014.
 */

/**
 * Модуль описывает реализацию ядра.
 */
public class CoreModule extends AbstractModule {
    private Request request;
    private Response response;

    public CoreModule(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    protected void configure() {
        bindInterceptor(
                Matchers.subclassesOf(Controller.class).and(Matchers.annotatedWith(With.class)),
                getMatchers(),
                new WithInterceptor()
        );

        bindInterceptor(Matchers.subclassesOf(Controller.class),
                getMatchers(),
                new AfterBeforeInterceptor());

        bind(Request.class).toInstance(request);
        bind(Response.class).toInstance(response);

        bindInterceptor(Matchers.subclassesOf(Controller.class),
                getMatchers(),
                new RedirectInterceptor(response)
        );

    }

    private Matcher<Method> getMatchers() {
        final List<Method> objectMethods = Arrays.asList(Object.class.getDeclaredMethods());
        return new AbstractMatcher<Method>() {
            @Override
            public boolean matches(Method method) {
                return !(objectMethods.contains(method)
                        || method.isAnnotationPresent(Before.class)
                        || method.isAnnotationPresent(After.class));
            }
        };
    }
}

