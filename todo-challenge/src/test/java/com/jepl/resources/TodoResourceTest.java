package com.jepl.resources;

import com.jepl.*;
import com.jepl.models.*;

import org.junit.*;

import java.util.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.eclipse.jetty.http.HttpStatus.INTERNAL_SERVER_ERROR_500;
import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TodoResourceTest {
    private final Client client = ClientBuilder.newClient();

    @BeforeClass
    public static void init() throws Exception {
        Main.main(new String[0]);
    }

    @AfterClass
    public static void detory() throws Exception {
        Main.detroy();
    }

    private WebTarget createWebTarget(String path) {
        return client.target("http://localhost:8080").path(path);
    }

    @Test
    public void testTodoResourceTodos() {
        createTodo("");
        WebTarget target = createWebTarget("/todos");
        List todos = target.request().get(new GenericType<List<Todo>>() {});
        assertEquals(true, todos.size() > 0);
    }


    @Test
    public void testTodoResourceTodoByTodoId_sendDummyIdThenExpectError() throws Exception {
        WebTarget target = createWebTarget("/todos/dummy");
        Response response = target.request().get();
        assertEquals(NOT_FOUND_404, response.getStatus());
    }


    @Test
    public void testTodoResourceGetTodoByTodoIdAndTasksByTaskId_sendDummyIdThenExpectError() throws Exception {
        WebTarget target = createWebTarget("/todos/dummy/tasks/dummy");
        Response response = target.request().get();
        assertEquals(NOT_FOUND_404, response.getStatus());
    }

    @Test
    public void testTodoResourceSetDoneToTask_sendDummtyIdThenExpectError() throws Exception {
        WebTarget target = createWebTarget("/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03/tasks/done");
        Response response = target.request().get();
        assertEquals(NOT_FOUND_404, response.getStatus());
    }


    @Test
    public void testTodoResourceSetNotDoneToTask_sendDummtyIdThenExpectError() throws Exception {
        WebTarget target = createWebTarget("/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03/tasks/not-done");
        Response response = target.request().get();
        assertEquals(NOT_FOUND_404, response.getStatus());
    }

    private Todo createTodo(String name) {
        WebTarget target = createWebTarget("/todos");
        Todo todo = new Todo(name);
        Entity<Todo> entity = Entity.entity(todo, MediaType.APPLICATION_JSON);

        return target.request(MediaType.APPLICATION_JSON).post(entity, Todo.class);
    }

    private Task createTask(String todoId, String name, String description) {
        WebTarget target = createWebTarget("/todos/" + todoId + "/tasks");
        Task task = new Task(name, description);
        Entity<Task> entity = Entity.entity(task, MediaType.APPLICATION_JSON);

        return target.request(MediaType.APPLICATION_JSON).post(entity, Task.class);
    }

    @Test
    public void testTodoResourceCreateTodo() throws Exception {
        String name = "Name of the todo";
        Todo todo = createTodo(name);
        assertEquals(name, todo.getName());
    }

    @Test
    public void testTodoResourceTodoByTodoId() throws Exception {
        Todo todo = createTodo("");
        WebTarget target = createWebTarget("/todos/" + todo.getId());
        Todo resultTodo = target.request().get(Todo.class);
        assertEquals(true, todo.equals(resultTodo));
    }

    @Test
    public void testTodoResourceCreateTask() throws Exception {
        Todo todo = createTodo("");
        Task task = createTask(todo.getId(), "", "");
        assertNotNull(task);
    }

    @Test
    public void testTodoResourceGetTodoByTodoIdAndTasksByTaskId() throws Exception {
        Todo todo = createTodo("");
        Task task = createTask(todo.getId(), "", "");
        WebTarget target = createWebTarget(String.format("/todos/%s/tasks/%s", todo.getId(), task.getId()));
        Task resultTask = target.request().get(Task.class);
        assertEquals(true, task.equals(resultTask));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Todo todo = createTodo("");
        Task task = createTask(todo.getId(), "", "");
        task.setName("update name");
        task.setDescription("update description");
        WebTarget target = createWebTarget(String.format("/todos/%s/tasks/%s", todo.getId(), task.getId()));

        Entity<Task> entity = Entity.entity(task, MediaType.APPLICATION_JSON);
        Task resultTask = target.request().put(entity, Task.class);
        assertEquals(true, task.equals(resultTask));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        Todo todo = createTodo("");
        WebTarget target = createWebTarget(String.format("/todos/%s", todo.getId()));
        String result = target.request().delete(String.class);
        assertEquals("{}", result);
    }

    @Test
    public void testDeleteTask() throws Exception {
        Todo todo = createTodo("");
        Task task = createTask(todo.getId(), "", "");
        WebTarget target = createWebTarget(String.format("/todos/%s/tasks/%s", todo.getId(), task.getId()));
        String result = target.request().delete(String.class);
        assertEquals("{}", result);
    }
}
