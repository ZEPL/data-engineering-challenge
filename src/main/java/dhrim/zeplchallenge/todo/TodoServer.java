package dhrim.zeplchallenge.todo;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.util.Modules;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


@Slf4j
public class TodoServer {

    private void RestServer() { }

    private Server server;

    private static Module[] GUICE_DI_MODULES = {
            new GuiceDiBinding()
    };



    public void start(int port) throws Exception {

        server = new Server(port);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        // GuiceFilter is added. to inject instance when http requested
        servletContextHandler.addFilter(GuiceFilter.class, "/*", null);

        try {
            server.start();
            log.info("TodoServer started with port {}.", port);
        } catch(Throwable e) {
            log.error("Starting TodoServer failed. port={}", port, e);
        }

    }

    public void shutdown() {

        if(server==null || !server.isStarted()) {
            log.info("Server not started. Cancel shutdown.");
            return;
        }

        try {
            server.stop();
        } catch (Throwable e) {
            log.error("Something failed during shutting down TodoServer.", e);
        } finally {
            server.destroy();
        }

    }


    public boolean isStarted() {
        if(server==null) { return false; }
        return server.isStarted();
    }

    public void join() throws InterruptedException {
        if(!isStarted()) { return; }
        server.join();
    }


    public static TodoServer getInstance() {
        return getInstanceWithMockBinding();
    }

    private static Injector injector;

    @VisibleForTesting
    static TodoServer getInstanceWithMockBinding(Module... additionalModules) {
        Module module = Modules.override(GUICE_DI_MODULES).with(additionalModules);
        injector = Guice.createInjector(module);
        return injector.getInstance(TodoServer.class);
    }

}


