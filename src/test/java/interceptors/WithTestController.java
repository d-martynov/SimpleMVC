package interceptors;

import ru.dmartynov.simplemvc.core.annotations.After;
import ru.dmartynov.simplemvc.core.annotations.Before;
import ru.dmartynov.simplemvc.core.mvc.Controller;

/**
 * Created by Дмитрий on 30.04.2014.
 */
public class WithTestController extends Controller {
    @Before
    public void beforeVoid() {
        System.out.println("Before in with executed");
    }

    @After
    public void afterVoid() {
        System.out.println("After in with executed");
    }
}
