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

import zefl.domain.Task;
import zefl.domain.Todo;
import zefl.service.TodoService;

public class TodoResourceTest extends JerseyTest {
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TestTodoResource.class)
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
        TestTodoResource.resetData();
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
        is404(target("todos/invalidid/tasks").request().get());
    }

    private void is404(Response response) {
        assertEquals(404, response.getStatus());
    }

    @Test
    public void test404WhenCreateTasksWithInvalidTodoid() throws Exception {
        is404(target("todos/invalidid/tasks").request().post(null));
    }

    @Test
    public void testEmptyResultWhenGetTasks() throws Exception {
        final Todo todoResponse = callCreateTodo();

        List<Task> responseTodos = target("todos/"+ todoResponse.getId() +"/tasks")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), responseTodos);
    }

    @Test
    public void test404WhenGetTaskWithInvalidTodoId() throws Exception {
        is404(target("todos/invalidTodoId/task/invalidId").request().get());
    }

    @Test
    public void test404WhenGetTaskWithInvalidTaskId() throws Exception {
        final Todo todoResponse = callCreateTodo();

        is404(target("todos/" + todoResponse.getId() + "/task/invalidId").request().get());
    }

    private Todo callCreateTodo() {
        final Map<String, String> data = new HashMap<>();
        data.put("name", "todo");
        Todo newTodo = target("todos").request().post(Entity.json(data), Todo.class);
        assertNotNull(newTodo.getId());
        return newTodo;
    }

    private Task callCreateTask(Todo todo) {
        final Map<String, String> taskData = new HashMap<>();
        taskData.put("name", "task10");
        taskData.put("description", "task-desc10");
        Task task = target("todos/" + todo.getId() + "/tasks")
                .request()
                .post(Entity.json(taskData), Task.class);
        assertNotNull(task.getId());
        return task;
    }

    private Task callUpdateTask(Todo todo, Task task) {
        final Map<String, String> taskData = new HashMap<>();
        taskData.put("name", "task1010");
        taskData.put("description", "task-desc1010");
        taskData.put("status", "DONE");
        Task newTask = target("todos/" + todo.getId() + "/tasks/" + task.getId())
                .request()
                .put(Entity.json(taskData), Task.class);
        assertNotNull(newTask.getId());
        assertEquals("DONE", newTask.getStatus());
        return newTask;
    }

    @Test
    public void testInsertAndGet1Task() throws Exception {
        final Todo newTodo = callCreateTodo();
        final String todoId = newTodo.getId();

        final Task newTask = callCreateTask(newTodo);

        // Check by getting all tasks
        List<Task> responseTasks = target("todos/"+todoId+"/tasks").request().get(new GenericType<List<Task>>(){});
        assertEquals(1, responseTasks.size());

        // Check by getting the task
        Task returnedTask = target("todos/" + todoId + "/tasks/" + newTask.getId()).request().get(Task.class);
        assertNotNull(returnedTask.getCreated());
    }

    @Test
    public void test404WhenGetDoneTasksWithInvalidTodoId() throws Exception {
        is404(target("todos/invalidid/tasks/done").request().get());
    }

    @Test
    public void testEmptyResultWhenGetDoneTasksWithEmptyTasks() throws Exception {
        final Todo todoResponse = callCreateTodo();

        final List<Task> tasks = target("todos/"+ todoResponse.getId() +"/tasks/done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), tasks);
    }

    @Test
    public void testGetDoneTasksWith1NotDoneTask() throws Exception {
        final Todo todoResponse = callCreateTodo();

        final Task newTask = callCreateTask(todoResponse);

        final List<Task> tasks = target("todos/"+ todoResponse.getId() +"/tasks/done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), tasks);
    }

    @Test
    public void testGetDoneTasksWith1DoneTask() throws Exception {
        final Todo todo = callCreateTodo();
        final Task task = callCreateTask(todo);
        final Task newTask = callUpdateTask(todo, task);

        final List<Task> tasks = target("todos/"+ todo.getId() +"/tasks/done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(1, tasks.size());
        assertEquals(TodoService.STATUS_DONE, tasks.get(0).getStatus());
    }

    @Test
    public void test404WhenGetNotDoneTasksWithInvalidTodoId() throws Exception {
        is404(target("todos/invalidid/tasks/not-done").request().get());
    }

    @Test
    public void testEmptyResultWhenGetNotDoneTasksWithEmptyTasks() throws Exception {
        final Todo todoResponse = callCreateTodo();

        final List<Task> tasks = target("todos/"+ todoResponse.getId() +"/tasks/not-done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), tasks);
    }

    @Test
    public void testGetNotDoneTasksWith1NotDoneTask() throws Exception {
        final Todo todo = callCreateTodo();
        callCreateTask(todo);

        final List<Task> tasks = target("todos/"+ todo.getId() +"/tasks/not-done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(1, tasks.size());
        assertEquals(TodoService.STATUS_NOT_DONE, tasks.get(0).getStatus());
    }

    @Test
    public void testGetNotDoneTasksWith1DoneTask() throws Exception {
        final Todo todo = callCreateTodo();
        final Task task = callCreateTask(todo);
        callUpdateTask(todo, task);

        final List<Task> tasks = target("todos/"+ todo.getId() +"/tasks/not-done")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), tasks);
    }

    @Test
    public void test404WhenDeleteTodoWithNotexistId() throws Exception {
        is404(target("todos/invalidid").request().delete());
    }

    @Test
    public void testDeleteTodo() throws Exception {
        final Todo todo = callCreateTodo();
        Map response = target("todos/" + todo.getId()).request().delete(Map.class);
        assertEquals(new HashMap(), response);
        // Check deleted or not
        emptyTodosTest();
    }

    @Test
    public void test404WhenDeleteTaskWithNotexistTodoId() throws Exception {
        is404(target("todos/invalidid/tasks/invalidtask-id").request().delete());
    }

    @Test
    public void test404WhenDeleteTaskWithNotexistTaskId() throws Exception {
        final Todo todo = callCreateTodo();
        is404(target("todos/" + todo.getId() + "/tasks/invalidtask-id").request().delete());
    }

    @Test
    public void testDeleteTask() throws Exception {
        final Todo todo = callCreateTodo();
        final Task task = callCreateTask(todo);

        Map response = target("todos/" + todo.getId() + "/tasks/" + task.getId()).request().delete(Map.class);
        assertEquals(new HashMap(), response);
        // Check deleted or not
        List<Task> responseTodos = target("todos/"+ todo.getId() +"/tasks")
                .request().get(new GenericType<List<Task>>(){});
        assertEquals(new ArrayList<Task>(), responseTodos);
    }

}