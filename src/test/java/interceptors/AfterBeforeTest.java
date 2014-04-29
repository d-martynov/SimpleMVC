package interceptors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import junit.framework.TestCase;
import ru.dmartynov.simplemvc.core.ioc.CoreModule;

/**
 * Created by Дмитрий on 29.04.2014.
 */
public class AfterBeforeTest extends TestCase {
    private TestController controller;

    @Override
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new CoreModule(null, null));
        controller = injector.getInstance(TestController.class);
    }

    public void testAfterBefore() throws Exception {
        controller.test();
    }
}
