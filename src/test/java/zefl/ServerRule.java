package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import org.eclipse.jetty.server.Server;
import org.junit.rules.ExternalResource;

import java.util.Arrays;
import java.util.List;

public class ServerRule extends ExternalResource {
    Server server;

    @Override
    protected void before() throws Throwable {
        List<AbstractModule> modules = Arrays.asList(
                new JerseyGuiceModule("__HK2_Generated_0"),
                new ServletModule(),
                new TestTodoModule());

        server = new JettyServerWithGuiceBuilder().build(modules);
        server.start();
        while (!server.isStarted()) {
            System.out.println("waiting...");
            Thread.sleep(100);
        }
    }

    @Override
    protected void after() {
        try {
            server.stop();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.destroy();
        }
    }

    public String getBaseUri() {
        return server.getURI().toString();
    }
}