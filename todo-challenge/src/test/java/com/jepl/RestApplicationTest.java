package com.jepl;

import com.*;
import com.fasterxml.jackson.databind.*;

import com.jepl.enums.*;
import com.jepl.models.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Launcher.class)
@WebAppConfiguration
@IntegrationTest
@DirtiesContext
public class RestApplicationTest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeClass
	public static void init() {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

    @Value("${management.address}")
    String managementAddress;

    @Value("${management.port}")
    int managementPort;

    @Value("${server.address}")
    String serverAddress;

    @Value("${server.port}")
    int serverPort;

    String serverBaseUrl() {
        return "http://" + serverAddress + ":" + serverPort;
    }

    String managementBaseUrl() {
        return "http://" + managementAddress + ":" + managementPort;
    }

    @Test
    public void testTodoResourceTodo() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(serverBaseUrl() + "/todos", String.class);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        assertEquals(entity.getBody(), "[]");
    }

    @Test
    public void testTodoResourceTodoByTodoId() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(serverBaseUrl() + "/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(null, entity.getBody());
    }

    @Test
    public void getTodoByTodoIdAndTasksByTaskId() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(serverBaseUrl() + "/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03/tasks/84569ae0-bc41-11e6-a4a6-cec0c932ce03", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(null, entity.getBody());

    }

    @Test
    public void setDoneToTask() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(serverBaseUrl() + "/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03/tasks/done", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("[]", entity.getBody());
    }

    @Test
    public void setNotDoneToTask() throws Exception {
        ResponseEntity<String> entity = restTemplate.getForEntity(serverBaseUrl() + "/todos/84569ae0-bc41-11e6-a4a6-cec0c932ce03/tasks/not-done", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("[]", entity.getBody());
    }

    @Test
    public void createTodo() throws Exception {
        String name = "Name of the todo";
        Todo todo = new Todo(name);
        ResponseEntity<String> entity = restTemplate.postForEntity(serverBaseUrl() + "/todos",  todo, String.class);
        Todo resultTodo = createTodoForTest(name);
        assertEquals(name, resultTodo.getName());
    }

    public Todo createTodoForTest(String name) throws Exception {
        Todo todo = new Todo(name);
        ResponseEntity<String> entity = restTemplate.postForEntity(serverBaseUrl() + "/todos",  todo, String.class);
        Todo resultTodo = mapper.readValue(entity.getBody(), Todo.class);
        return resultTodo;
    }

    @Test
    public void createTask() throws Exception {
        Todo todo = createTodoForTest("Name of the todo");

        String name = "Name of the task";
        String description = "description of the task";
        Task task = new Task(name, description);
        String todo_id = todo.getId();
        ResponseEntity<String> entity = restTemplate.postForEntity(serverBaseUrl() + "/todos/" + todo_id + "/tasks",  task, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        Task resultTask = mapper.readValue(entity.getBody(), Task.class);
        assertEquals(name, resultTask.getName());
        assertEquals(description, resultTask.getDescription());
    }

    public Task createTaskForTest(String todoId, String name, String description) throws IOException {
        Task task = new Task(name, description);
        ResponseEntity<String> entity = restTemplate.postForEntity(serverBaseUrl() + "/todos/" + todoId + "/tasks",  task, String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        return mapper.readValue(entity.getBody(), Task.class);
    }

    @Test
    public void updateTask() throws Exception {
        // init
        Todo todo = createTodoForTest("todo");
        Task task = createTaskForTest(todo.getId(), "task", "task");

        // update
        String name = "updated: Name of the task";
        String description = "updated: description of the task";
        task.setName(name);
        task.setDescription(description);
        task.setStatus(TaskStatus.DONE);

        String todoId = todo.getId();
        String taskId = task.getId();

        String requestJson = mapper.writeValueAsString(task);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson,headers);


        ResponseEntity<String> entity2 =
                restTemplate.exchange(serverBaseUrl() + "/todos/" + todoId + "/tasks/" + taskId, HttpMethod.PUT, httpEntity, String.class, task);

        assertEquals(HttpStatus.OK, entity2.getStatusCode());
        Task resultTask = mapper.readValue(entity2.getBody(), Task.class);

        assertEquals(name, resultTask.getName());
        assertEquals(description, resultTask.getDescription());
        assertEquals(TaskStatus.DONE, resultTask.getStatus());
    }

    @Test
    public void deleteTodo() throws Exception {
        // init
        Todo todo = createTodoForTest("todo");
        String todoId = todo.getId();

        ResponseEntity<String> entity =
                restTemplate.exchange(serverBaseUrl() + "/todos/" + todoId, HttpMethod.DELETE, null, String.class);
        assertEquals("{}", entity.getBody());
    }

    @Test
    public void deleteNullTodoThenExpectException() throws Exception {
        // init
        String todoId = "unkown";
        ResponseEntity<String> entity =
                restTemplate.exchange(serverBaseUrl() + "/todos/" + todoId, HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        System.out.println(entity);
    }

    @Test
    public void deleteTask() throws Exception {
        // init
        Todo todo = createTodoForTest("todo");
        String todoId = todo.getId();
        Task task = createTaskForTest(todoId, "task", "description");
        String taskId = task.getId();

        ResponseEntity<String> entity =
                restTemplate.exchange(serverBaseUrl() + "/todos/" + todoId + "/tasks/" + taskId, HttpMethod.DELETE, null, String.class);
        assertEquals("{}", entity.getBody());
    }
}
