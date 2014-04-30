package ru.dmartynov.simplemvc.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import freemarker.template.Configuration;
import org.reflections.Reflections;
import ru.dmartynov.simplemvc.core.annotations.SetMethod;
import ru.dmartynov.simplemvc.core.ioc.CoreModule;
import ru.dmartynov.simplemvc.core.mvc.Controller;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.route.HttpMethod;
import spark.servlet.SparkApplication;
import spark.template.freemarker.FreeMarkerRoute;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import static spark.Spark.*;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class EntryPoint implements SparkApplication {
    public static void main(String[] args) {
        new EntryPoint().init();
    }

    @Override
    public void init() {
        Reflections reflections = new Reflections("controllers");
        Set<Class<? extends Controller>> controllersClasses = reflections.getSubTypesOf(Controller.class);

        for (final Class<? extends Controller> controllerClass : controllersClasses) {

            for (final Method method : controllerClass.getDeclaredMethods()) {
                if (method.getReturnType() != void.class) {
                    initMethodRoute(method);
                }
            }
        }
    }

    private void initMethodRoute(Method method) {
        HttpMethod httpMethod;

        SetMethod setMethodAnnotation = method.getAnnotation(SetMethod.class);
        if (setMethodAnnotation != null) {
            httpMethod = setMethodAnnotation.value();
        } else {
            httpMethod = HttpMethod.get;
        }

        switch (httpMethod) {
            case get:
                get(getMethodRoute(method));
                break;
            case post:
                post(getMethodRoute(method));
                break;
            case put:
                put(getMethodRoute(method));
                break;
            case patch:
                patch(getMethodRoute(method));
                break;
            case delete:
                delete(getMethodRoute(method));
                break;
            case head:
                head(getMethodRoute(method));
                break;
            case trace:
                trace(getMethodRoute(method));
                break;
            case connect:
                connect(getMethodRoute(method));
                break;
            case options:
                options(getMethodRoute(method));
                break;
        }
    }

    private Route getMethodRoute(final Method method) {
        final Router router = new Router(method);
        FreeMarkerRoute route = new FreeMarkerRoute(router.getRoute()) {
            @Override
            public ModelAndView handle(Request request, Response response) {
                Injector injector = Guice.createInjector(new CoreModule(request, response));
                Controller controller = (Controller) injector.getInstance(method.getDeclaringClass());
                try {
                    Object result = invokeControllerMethod(controller, method, request);

                    int status = response.raw().getStatus();

                    if (status == 0) {
                        HashMap<String, Object> model = new HashMap<String, Object>();
                        model.put("model", result);
                        model.put("router", new Router(method));

                        return modelAndView(model, getViewPath(method));
                    } else {
                        return modelAndView(null, "views/blank");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Ошибка при выполнении метода контроллера", e);
                }
            }
        };

        Configuration configuration = new Configuration();
        try {
            File templateFolder = new File("src/main/java");
            configuration.setDirectoryForTemplateLoading(templateFolder);
           route.setConfiguration(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return route;
    }

    private Object invokeControllerMethod(Controller controller, Method method, Request request) throws Exception {
        Object result = method.invoke(controller);
        return result;
    }

    private String getViewPath(Method method) {
        String viewDir = method.getDeclaringClass().getName();
        viewDir = viewDir.replaceFirst("controllers", "views");
        viewDir = viewDir.replaceAll("\\.", "/");
        return viewDir + "/" + method.getName() + ".html";
    }

}
