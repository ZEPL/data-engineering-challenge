package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import org.eclipse.jetty.server.Server;

import java.util.Arrays;
import java.util.List;

public class TodoMain {

    public static void main(String[] args) throws Exception {
        JettyServerWithGuiceBuilder serverBuilder = new JettyServerWithGuiceBuilder(8080);
        List<AbstractModule> modules = Arrays.asList(
                new JerseyGuiceModule("__HK2_Generated_0"),
                new ServletModule(),
                new TodoModule());

        Server server = serverBuilder.build(modules);

        server.start();
    }
}