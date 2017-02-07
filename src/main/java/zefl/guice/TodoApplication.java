package zefl.guice;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

import zefl.TodoResource;

@ApplicationPath("/")
public class TodoApplication extends ResourceConfig {
    public TodoApplication() {
        packages(TodoResource.class.getPackage().getName());
    }
}