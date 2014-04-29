package interceptors;

import ru.dmartynov.simplemvc.core.annotations.After;
import ru.dmartynov.simplemvc.core.annotations.Before;
import ru.dmartynov.simplemvc.core.annotations.With;
import ru.dmartynov.simplemvc.core.mvc.Controller;

/**
 * Created by Дмитрий on 29.04.2014.
 */

@With({WithTestController.class, With2TestController.class})
public class TestController extends Controller {

    @Before
    public void before() {
        System.out.println("Before executed");
    }

    public void test() {
        System.out.println("Method executed");
    }

    @After
    public void after() {
        System.out.println("After executed");
    }
}
