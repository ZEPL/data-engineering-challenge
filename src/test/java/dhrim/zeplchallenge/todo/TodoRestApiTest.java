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

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

        public Map<String, Todo> todoMap = new HashMap<String, Todo>();
        public Map<String, Map<String, Task>> tasksMap = new HashMap<String, Map<String, Task>>();

        @Override
        protected Map<String, Todo> getTodoMapInstance() {
            return todoMap;
        }

        @Override
        protected Map<String, Map<String, Task>> getTasksMapInstance() {
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
            String responseBodyString = sendAndGetResponseBody(POST, "/todos", todo, HttpStatus.OK_200);

            // THEN
            // {"id":"ad070105-64d4-4646-a06b-6c150114af32","name":"name1","created":"2016-12-30 04:27:22.090"}
            assertNotNull(responseBodyString);
            Todo actualTodo = objectMapper.readValue(responseBodyString, Todo.class);

            assertNotNull(actualTodo.getId());
            assertEquals(todo.getName(), actualTodo.getName());
            assertNotNull(actualTodo.getCreated());

            // expect 1 todo in repo
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, 1, mockTodoRepo.todoMap.size());
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

            // expect 2 todos in repo
            assertEquals("incorrect todo size. todoRepo="+mockTodoRepo, 2, mockTodoRepo.todoMap.size());
        }

    }


    @Test
    public void test_createTodo_failed_when_empty_input() throws IOException {

        // GIVEN
        Object emptyString = "";

        // WHEN
        String responseBodyString = sendAndGetResponseBody(POST, "/todos", emptyString, HttpStatus.BAD_REQUEST_400);

        // THEN
        FailedMessage failedMessage = objectMapper.readValue(responseBodyString, FailedMessage.class);
        assertNotNull(failedMessage.getMessage());

    }


    @Test
    public void test_getTodos_when_empty() throws Exception {

        // GIVEN

        // WHEN
        String responseBodyString = sendAndGetResponseBody(GET, "/todos", HttpStatus.OK_200);

        // THEN

        // just assert '[]'
        List<Todo> todoList = objectMapper.readValue(responseBodyString, new TypeReference<List<Todo>>() {});
        assertEquals(0, todoList.size());

    }

    @Test
    public void test_getTodos() throws Exception {

        // GIVEN
        Todo expectedTodo1 = new TodoBuilder().id("id1").name("name1").created(new Date()).build();
        expectedTodo1.setCreated(null);
        mockTodoRepo.todoMap.put(expectedTodo1.getId(), expectedTodo1);
        Todo expectedTodo2 = new TodoBuilder().id("id2").name("name2").created(new Date()).build();
        expectedTodo2.setCreated(null);
        mockTodoRepo.todoMap.put(expectedTodo2.getId(), expectedTodo2);

        // WHEN
        String responseBodyString = sendAndGetResponseBody(GET, "/todos", HttpStatus.OK_200);

        // THEN
        List<Todo> todoList = objectMapper.readValue(responseBodyString, new TypeReference<List<Todo>>() {});

        // THEN
        assertEquals(2, todoList.size());
        assertThat(todoList, CoreMatchers.hasItem(expectedTodo1));
        assertThat(todoList, CoreMatchers.hasItem(expectedTodo2));

    }


}
