package dhrim.zeplchallenge.todo;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Bind jersey resource classes and other service classes for Guice DI injection.
 *
 * Jersey resource classes are auto scanned from BASE_PACKAGE recursively.
 * Other service classes are configured manually not scanned.
 */
public class GuiceDiBinding extends JerseyServletModule {

    // value is like "dhrim.zeplchallenge.todo"
    private static final String BASE_PACKAGE = Main.class.getPackage().getName();

    @Override
    protected void configureServlets() {

        configureResourceClasses();
        configureServiceClasses();

        serve("/*").with(GuiceContainer.class);

    }

    private void configureResourceClasses() {
        PackagesResourceConfig resourceConfig = new PackagesResourceConfig(BASE_PACKAGE);
        for (Class<?> resource : resourceConfig.getClasses()) {
            bind(resource);
        }
    }

    private void configureServiceClasses() {
        bind(TodoService.class);
        bind(TodoRepo.class).to(MapDbTodoRepo.class);
    }

}
