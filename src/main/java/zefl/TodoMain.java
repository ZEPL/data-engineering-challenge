package zefl;

import org.eclipse.jetty.server.Server;

public class TodoMain {

    public static void main(String[] args) throws Exception {
        JettyServerWithGuiceBuilder serverBuilder = new JettyServerWithGuiceBuilder(8080);
        Server server = serverBuilder.build();

        server.start();
    }
}