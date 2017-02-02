package zefl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TodoResourceTest extends JerseyTest{
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TodoResource.class)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO")
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
        TodoServiceImpl.resetDao();
    }

    @Test
    public void emptyTodosTest() throws Exception {
        List<Todo> responseTodos = target("todos").request().get(new GenericType<List<Todo>>(){});
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
        List<Todo> todoResults = target("todos").request().get(new GenericType<List<Todo>>(){});
        assertEquals(1, todoResults.size());
        Todo resultTodo = todoResults.get(0);
        assertEquals("todo", resultTodo.getName());
    }

    @Test
    public void test404WhenGetTasksWithInvalidTodoid() throws Exception {
        Response output = target("todos/invalidid/tasks").request().get();
        assertEquals(404, output.getStatus());
    }

    @Test
    public void test404WhenCreateTasksWithInvalidTodoid() throws Exception {
        Response output = target("todos/invalidid/tasks").request().post(null);
        assertEquals(404, output.getStatus());
    }

    @Test
    public void testGetEmptyTasks() throws Exception {
        final Todo todoResponse = callCreateTodo();

        List<Task> responseTodos = target("todos/"+ todoResponse.getId() +"/tasks")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), responseTodos);
    }

    @Test
    public void test404WhenGetTaskWithInvalidTodoId() throws Exception {
        Response output = target("todos/invalidTodoId/task/invalidId").request().get();
        assertEquals(404, output.getStatus());
    }

    @Test
    public void test404WhenGetTaskWithInvalidTaskId() throws Exception {
        final Todo todoResponse = callCreateTodo();

        Response output = target("todos/" + todoResponse.getId() + "/task/invalidId").request().get();
        assertEquals(404, output.getStatus());
    }

    private Todo callCreateTodo() {
        final Map<String, String> data = new HashMap<>();
        data.put("name", "todo");
        Todo newTodo = target("todos").request().post(Entity.json(data), Todo.class);
        assertNotNull(newTodo.getId());
        return newTodo;
    }

    @Test
    public void testInsertAndGet1Task() throws Exception {
        final Todo newTodo = callCreateTodo();
        final String todoId = newTodo.getId();

        final Map<String, String> taskData = new HashMap<>();
        taskData.put("name", "task10");
        taskData.put("description", "task-desc10");
        final Task newTasks = target("todos/"+todoId+"/tasks")
                .request()
                .post(Entity.json(taskData), Task.class);
        final String taskId = newTasks.getId();
        assertNotNull(taskId);

        // Check by getting all tasks
        List<Task> responseTasks = target("todos/"+todoId+"/tasks").request().get(new GenericType<List<Task>>(){});
        assertEquals(1, responseTasks.size());

        // Check by getting the task
        Task returnedTask = target("todos/" + todoId + "/tasks/"+ taskId).request().get(Task.class);
        assertNotNull(returnedTask.getCreated());
    }
}