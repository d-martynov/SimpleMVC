package ru.dmartynov.simplemvc.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.reflections.Reflections;
import ru.dmartynov.simplemvc.core.ioc.CoreModule;
import ru.dmartynov.simplemvc.core.mvc.Controller;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.servlet.SparkApplication;

import java.lang.reflect.Method;
import java.util.Set;

import static spark.Spark.get;

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
            final String controllerName = controllerClass.getSimpleName();

            for (final Method method : controllerClass.getDeclaredMethods()) {
                if (method.getReturnType() != void.class) {
                    get(new Route(controllerName + "/" + method.getName()) {
                        @Override
                        public Object handle(Request request, Response response) {
                            Injector injector = Guice.createInjector(new CoreModule(request, response));
                            Controller controller = injector.getInstance(controllerClass);
                            try {
                                return method.invoke(controller);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }
    }
}
