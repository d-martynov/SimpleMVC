package controllers;

import ru.dmartynov.simplemvc.core.mvc.Controller;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class TestController extends Controller {

    public String test() {
        return "hello " + request.ip();
    }
}
