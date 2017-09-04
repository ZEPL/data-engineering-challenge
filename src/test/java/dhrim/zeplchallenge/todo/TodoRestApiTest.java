package dhrim.zeplchallenge.todo;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import lombok.Data;
import org.codehaus.jackson.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static org.eclipse.jetty.http.HttpStatus.OK_200;
import static org.junit.Assert.*;

public class TodoRestApiTest extends AbstractTestBase {


    private MockMemoryTodoRepo mockTodoRepo = new MockMemoryTodoRepo();

    @Before
    public void before() throws Exception {
        super.before();
    }

    @After
    public void after() {
        super.after();
        mockTodoRepo.clear_for_test();
    }

    @Override
    protected Module getMockBinding() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(TodoRepo.class).toInstance(mockTodoRepo);
            }
        };
    }

    @Data
    private static class MockMemoryTodoRepo extends AbstractMapBasedTodoRepo {

        public Map<String, Todo> todoMap = new HashMap<>();
        public Map<String, Map<String, Task>> tasksMap = new HashMap<>();

        @Override
        protected Map<String, Todo> getTodoMapInstance() {
            return todoMap;
        }

        @Override
        protected Map<String, Map<String, Task>> getTaskMapMapInstance() {
            return tasksMap;
        }

    }


    @Test
    public void test_when_invalid_path() throws IOException {

        // GIVEN
        Object emptyString = "";

        // WHEN, THEN
        sendAndGetResponseBody(GET, "/invalid_path", emptyString, HttpStatus.NOT_FOUND_404);

    }

    @Test
    public void test_createTodo() throws Exception {

        // GIVEN
        Todo todo = new TodoBuilder().name("name1").build();

        // create one
        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(POST, "/todos", todo, OK_200);

            // THEN
            // {"id":"ad070105-64d4-4646-a06b-6c150114af32","name":"name1","created":"2016-12-30 04:27:22.090"}
            assertNotNull(responseBodyString);
            Todo actualTodo = objectMapper.readValue(responseBodyString, Todo.class);

            assertNotNull(actualTodo.getId());
            assertEquals(todo.getName(), actualTodo.getName());
            assertNotNull(actualTodo.getCreated());

            final int EXPECTED_TODO_SIZE = 1;
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, EXPECTED_TODO_SIZE, mockTodoRepo.todoMap.size());
        }


        // create again
        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(POST, "/todos", todo, HttpStatus.OK_200);

            // THEN
            // {"id":"ad070105-64d4-4646-a06b-6c150114af32","name":"name1","created":"2016-12-30 04:27:22.090"}
            assertNotNull(responseBodyString);
            Todo actualTodo = objectMapper.readValue(responseBodyString, Todo.class);

            assertNotNull(actualTodo.getId());
            assertEquals(todo.getName(), actualTodo.getName());
            assertNotNull(actualTodo.getCreated());

            final int EXPECTED_TODO_SIZE = 2;
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, EXPECTED_TODO_SIZE, mockTodoRepo.todoMap.size());
        }

    }


    @Test
    public void test_createTodo_failed_when_empty_input() throws IOException {

        // GIVEN
        Object emptyString = "";

        // WHEN
        String responseBodyString = sendAndGetResponseBody(POST, "/todos", emptyString, BAD_REQUEST_400);

        // THEN
        FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
        assertNotNull(failedMessage.getMessage());

    }


    @Test
    public void test_getTodoList_when_empty() throws Exception {

        // GIVEN

        // WHEN
        String responseBodyString = sendAndGetResponseBody(GET, "/todos", OK_200);

        // THEN
        // just assert '[]'
        List<Todo> todoList = objectMapper.readValue(responseBodyString, new TypeReference<List<Todo>>() {});
        assertEquals(0, todoList.size());

    }

    @Test
    public void test_getTodoList() throws Exception {

        // GIVEN
        Todo expectedTodo1 = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(expectedTodo1.getId(), expectedTodo1);
        Todo expectedTodo2 = new TodoBuilder().id("id2").name("name2").created(new Date()).build();
        mockTodoRepo.todoMap.put(expectedTodo2.getId(), expectedTodo2);

        // WHEN
        String responseBodyString = sendAndGetResponseBody(GET, "/todos", OK_200);

        // THEN
        List<Todo> todoList = objectMapper.readValue(responseBodyString, new TypeReference<List<Todo>>() {});
        assertEquals(2, todoList.size());
        assertThat(todoList, CoreMatchers.hasItem(expectedTodo1));
        assertThat(todoList, CoreMatchers.hasItem(expectedTodo2));

    }


    @Test
    public void test_deleteTodo() throws IOException {

        // GIVEN
        Todo todo1 = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo1.getId(), todo1);
        Todo todo2 = new TodoBuilder().id("id2").name("name2").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo2.getId(), todo2);

        {
            // WHEN
            Todo targetTodo = todo1;
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/"+targetTodo.getId(), HttpStatus.OK_200);

            // THEN
            Todo todo = objectMapper.readValue(responseBodyString, Todo.class);
            assertEmpty(todo);
            assertNull(mockTodoRepo.todoMap.get(targetTodo.getId()));
        }

        {
            // WHEN
            Todo targetTodo = todo2;
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/"+targetTodo.getId(), HttpStatus.OK_200);

            // THEN
            Todo todo = objectMapper.readValue(responseBodyString, Todo.class);
            assertEmpty(todo);
            assertNull(mockTodoRepo.todoMap.get(targetTodo.getId()));
        }

    }

    @Test
    public void test_deleteTodo_when_invalid_todoId() throws IOException {

        // GIVEN
        Todo todo1 = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo1.getId(), todo1);
        Todo todo2 = new TodoBuilder().id("id2").name("name2").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo2.getId(), todo2);

        {
            // WHEN, THEN
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/INVALID_TODO_ID", BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }


    @Test
    public void test_createTask() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);


        // create one
        {
            // WHEN
            Task task = new TaskBuilder().name("taskName1").description("task description 1").build();
            String responseBodyString = sendAndGetResponseBody(POST, "/todos/"+todo.getId()+"/tasks", task, HttpStatus.OK_200);

            // THEN
            assertNotNull(responseBodyString);
            Task actualTask = objectMapper.readValue(responseBodyString, Task.class);

            assertNotNull(actualTask.getId());
            assertEquals(task.getName(), actualTask.getName());
            assertEquals(task.getDescription(), actualTask.getDescription());
            assertEquals(Task.Status.NOT_DONE, actualTask.getStatus());
            assertNotNull(actualTask.getCreated());

            final int EXPECTED_TASK_SIZE = 1;
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, EXPECTED_TASK_SIZE, mockTodoRepo.tasksMap.get(todo.getId()).size());
        }

        // create anothter
        {
            // WHEN
            Task task = new TaskBuilder().name("taskName2").description("task description 2").build();
            String responseBodyString = sendAndGetResponseBody(POST, "/todos/"+todo.getId()+"/tasks", task, HttpStatus.OK_200);

            // THEN
            assertNotNull(responseBodyString);
            Task actualTask = objectMapper.readValue(responseBodyString, Task.class);

            assertNotNull(actualTask.getId());
            assertEquals(task.getName(), actualTask.getName());
            assertEquals(task.getDescription(), actualTask.getDescription());
            assertEquals(Task.Status.NOT_DONE, actualTask.getStatus());
            assertNotNull(actualTask.getCreated());

            final int EXPECTED_TASK_SIZE = 2;
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, EXPECTED_TASK_SIZE, mockTodoRepo.tasksMap.get(todo.getId()).size());
        }

    }

    @Test
    public void test_createTask_when_invalid_todoId () throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        // WHEN
        Task task = null;
        String responseBodyString = sendAndGetResponseBody(POST, "/todos/INVALID_TODO_ID/tasks", task, BAD_REQUEST_400);

        // THEN
        FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
        assertNotNull(failedMessage.getMessage());


    }

    @Test
    public void test_createTask_when_no_input () throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        // WHEN
        Task task = null;
        String responseBodyString = sendAndGetResponseBody(POST, "/todos/"+todo.getId()+"/tasks", task, BAD_REQUEST_400);

        // THEN
        FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
        assertNotNull(failedMessage.getMessage());

    }


    @Test
    public void test_getTask() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), HttpStatus.OK_200);

            // THEN
            Task task = objectMapper.readValue(responseBodyString, Task.class);
            assertEquals(targetTask, task);
        }

        {
            Task targetTask = task2;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), HttpStatus.OK_200);

            // THEN
            Task task = objectMapper.readValue(responseBodyString, Task.class);
            assertEquals(targetTask, task);
        }

    }


    @Test
    public void test_getTask_when_invalid_todoId() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/INVALID_TODO_ID/tasks/"+targetTask.getId(), BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }

    @Test
    public void test_getTask_when_invalid_taskId() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks/INVALID_TASK_ID", HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }

    @Test
    public void test_getTaskList() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks", HttpStatus.OK_200);

            // THEN
            List<Task> taskList = objectMapper.readValue(responseBodyString, new TypeReference<List<Task>>() {});
            assertEquals(2, taskList.size());

            assertThat(taskList, CoreMatchers.hasItem(task1));
            assertThat(taskList, CoreMatchers.hasItem(task2));
        }

    }


    @Test
    public void test_getTaskList_when_invalid_todoId() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/INVALID_TODO_ID/tasks", HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());
        }

    }

    @Test
    public void test_getTaskList_with_status_done() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);
        Task task3 = new TaskBuilder().id("taskId3").name("task name3").description("description3").status(Task.Status.DONE).created(new Date()).build();
        taskMap.put(task3.getId(), task3);
        Task task4 = new TaskBuilder().id("taskId4").name("task name4").description("description4").status(Task.Status.DONE).created(new Date()).build();
        taskMap.put(task4.getId(), task4);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks/not-done", HttpStatus.OK_200);

            // THEN
            List<Task> taskList = objectMapper.readValue(responseBodyString, new TypeReference<List<Task>>() {});
            assertEquals(2, taskList.size());

            assertThat(taskList, CoreMatchers.hasItem(task1));
            assertThat(taskList, CoreMatchers.hasItem(task2));
        }

        {
            // WHEN
            String responseBodyString = sendAndGetResponseBody(GET, "/todos/"+todo.getId()+"/tasks/done", HttpStatus.OK_200);

            // THEN
            List<Task> taskList = objectMapper.readValue(responseBodyString, new TypeReference<List<Task>>() {});
            assertEquals(2, taskList.size());

            assertThat(taskList, CoreMatchers.hasItem(task3));
            assertThat(taskList, CoreMatchers.hasItem(task4));
        }

    }

    @Test
    public void test_deleteTask() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), HttpStatus.OK_200);

            // THEN
            Task task = objectMapper.readValue(responseBodyString, Task.class);
            assertEmpty(task);
            assertNull(mockTodoRepo.tasksMap.get(todo.getId()).get(targetTask.getId()));
        }

        {
            Task targetTask = task2;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), HttpStatus.OK_200);

            // THEN
            Task task = objectMapper.readValue(responseBodyString, Task.class);
            assertEmpty(task);
            assertNull(mockTodoRepo.tasksMap.get(todo.getId()).get(targetTask.getId()));
        }

    }


    @Test
    public void test_deleteTask_when_invalid_todoId() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/INVALID_TODO_ID/tasks/" + targetTask.getId(), HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());
        }

    }

    @Test
    public void test_deleteTask_when_invalid_taskId() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            // WHEN
            String responseBodyString = sendAndGetResponseBody(DELETE, "/todos/"+todo.getId()+"/tasks/INVALID_TASK_ID", HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());
        }

    }


    @Test
    public void test_updateTask() throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;
            targetTask.setName("modified task name1");
            targetTask.setDescription("modified description1");
            targetTask.setStatus(Task.Status.DONE);

            // WHEN
            String responseBodyString = sendAndGetResponseBody(PUT, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), targetTask, HttpStatus.OK_200);

            // THEN
            Task task = objectMapper.readValue(responseBodyString, Task.class);
            assertEquals(targetTask, task);
            Task actualTask = taskMap.get(targetTask.getId());
            assertEquals(targetTask, actualTask);
        }

    }


    @Test
    public void test_updateTask_when_invalid_todoId () throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;

            // WHEN
            String responseBodyString = sendAndGetResponseBody(PUT, "/todos/INVALID_TODO_ID/tasks/"+targetTask.getId(), targetTask, HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }


    @Test
    public void test_updateTask_when_invalid_taskId () throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;

            // WHEN
            String responseBodyString = sendAndGetResponseBody(PUT, "/todos/"+todo.getId()+"/tasks/INVALID_TASK_ID", targetTask, HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }

    @Test
    public void test_updateTask_when_empty_task () throws IOException {

        // GIVEN
        Todo todo = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        mockTodoRepo.todoMap.put(todo.getId(), todo);

        Map<String, Task> taskMap = new HashMap<>();
        Task task1 = new TaskBuilder().id("taskId1").name("task name1").description("description1").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task1.getId(), task1);
        Task task2 = new TaskBuilder().id("taskId2").name("task name2").description("description2").status(Task.Status.NOT_DONE).created(new Date()).build();
        taskMap.put(task2.getId(), task2);

        mockTodoRepo.tasksMap.put(todo.getId(), taskMap);

        {
            Task targetTask = task1;

            // WHEN
            String responseBodyString = sendAndGetResponseBody(PUT, "/todos/"+todo.getId()+"/tasks/"+targetTask.getId(), HttpStatus.BAD_REQUEST_400);

            // THEN
            FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
            assertNotNull(failedMessage.getMessage());

        }

    }


}
