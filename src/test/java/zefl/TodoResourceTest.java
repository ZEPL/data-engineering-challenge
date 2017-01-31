package zefl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.assertArrayEquals;

public class TodoResourceTest extends JerseyTest{
    @Override
    protected Application configure() {
        return new ResourceConfig(TodoResource.class)
                .register(JacksonFeature.class);
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(new JacksonFeature());
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void emptyTodosTest() throws Exception {
        Todo[] responseTodos = target("todos").request().get(Todo[].class);
        assertArrayEquals(new Todo[] {}, responseTodos);
    }

}