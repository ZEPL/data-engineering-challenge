package zefl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        List responseTodos = target("todos").request().get(List.class);
        assertEquals(new ArrayList<Todo>(), responseTodos);
    }

    @Test
    public void insertAndRead1TodoTest() throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("name", "todo");
        Todo todoResponse = target("todos").request().post(Entity.json(data), Todo.class);
        assertNotNull(todoResponse.getId());
        assertNotNull(todoResponse.getCreated());
        assertEquals("todo", todoResponse.getName());
        System.out.println(todoResponse);
        List<Todo> todoResults = target("todos").request().get(new GenericType<List<Todo>>(){});
        assertEquals(1, todoResults.size());
        Todo resultTodo = todoResults.get(0);
        assertEquals("todo", resultTodo.getName());
    }
}