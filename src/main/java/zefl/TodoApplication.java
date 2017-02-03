package zefl;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class TodoApplication extends ResourceConfig {
    public TodoApplication() {
        packages(TodoResource.class.getPackage().getName());
    }
}