package controllers;

import ru.dmartynov.simplemvc.core.annotations.SetRoute;
import ru.dmartynov.simplemvc.core.mvc.Controller;

/**
 * Created by Дмитрий on 30.04.2014.
 */

@SetRoute("cont")
public class TestController extends Controller {

    @SetRoute("act")
    public String test() {
        return gets2();
    }

    public String gets2() {
        return "Gets2";
    }
}
