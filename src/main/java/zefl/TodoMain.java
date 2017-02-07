package zefl;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;

import zefl.guice.TodoModule;
import zefl.guice.JettyServerWithGuiceBuilder;

public class TodoMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMain.class);

    public static void main(String[] args) throws Exception {
        JettyServerWithGuiceBuilder serverBuilder = new JettyServerWithGuiceBuilder(8080);
        List<AbstractModule> modules = Arrays.asList(
                new JerseyGuiceModule("__HK2_Generated_0"),
                new ServletModule(),
                new TodoModule());

        Server server = serverBuilder.build(modules);
        LOGGER.info("Start todo server!");
        server.start();
    }
}