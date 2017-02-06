package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.List;

public class JettyServerWithGuiceBuilder {
    private int port = 8080;

    public JettyServerWithGuiceBuilder() {
    }

    public JettyServerWithGuiceBuilder(int port) {
        this.port = port;
    }

    public Server build(List<AbstractModule> modules) {
        // Init Guice&Jersey2 interaction related

        Injector injector = Guice.createInjector(modules);
        JerseyGuiceUtils.install(injector);

        // Init Jetty server
        Server server = new Server(port);

        ResourceConfig config = ResourceConfig.forApplication(new TodoApplication());

        ServletContainer servletContainer = new ServletContainer(config);

        ServletHolder servletHolder = new ServletHolder(servletContainer);
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        FilterHolder filterHolder = new FilterHolder(GuiceFilter.class);
        context.addFilter(filterHolder, "/*",
                EnumSet.allOf(DispatcherType.class));

        context.addServlet(servletHolder, "/*");
        server.setHandler(context);
        return server;
    }
}