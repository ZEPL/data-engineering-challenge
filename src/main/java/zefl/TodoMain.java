package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class TodoMain {

    public static void main(String[] args) throws Exception {
        List<AbstractModule> modules = Arrays.asList(new JerseyGuiceModule("__HK2_Generated_0"), new ServletModule(), new TodoModule());
        Injector injector = Guice.createInjector(modules);
        JerseyGuiceUtils.install(injector);

        Server server = new Server(8888);

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

        server.start();
    }
}