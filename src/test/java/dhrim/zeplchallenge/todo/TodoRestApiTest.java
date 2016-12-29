package dhrim.zeplchallenge.todo;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TodoRestApiTest extends AbstractTestBase {

    @Before
    public void before() throws Exception {
        super.before();
    }

    @After
    public void after() {
        super.after();
    }

    @Override
    protected Module getMockBinding() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(TodoRepo.class).toInstance(new MockMemoryTodoRepo());
            }
        };
    }

    private static class MockMemoryTodoRepo extends AbstractMapBasedTodoRepo {

        @Override
        protected Map<String, Todo> getTodoMapInstance() {
            return new HashMap<String, Todo>();
        }

        @Override
        protected Map<String, Map<String, Task>> getTasksMapInstance() {
            return new HashMap<String, Map<String, Task>>();
        }


    }


    @Test
    public void test_createTodo() throws Exception {

        // GIVEN
        Todo todo = new TodoBuilder().name("name1").build();

        // WHEN
        String responseBodyString = sendAndGetResponseBody(BASE_URL+"/todos", todo, HttpStatus.OK_200);

        // THEN
        // {"id":"1e0840ec-6cbf-41d4-8c83-83fb6f8bf8f7","name":"name1","created":1482992669764}
        assertNotNull(responseBodyString);
        Todo actualTodo = objectMapper.readValue(responseBodyString, Todo.class);

        assertNotNull(actualTodo.getId());
        assertEquals(todo.getName(), actualTodo.getName());
        assertNotNull(actualTodo.getCreated());

    }


    @Test
    public void test_createTodo_failed_when_empty_input() throws IOException {

        // GIVEN
        Object emptyString = "";

        // WHEN
        String responseBodyString = sendAndGetResponseBody(BASE_URL+"/todos", emptyString, HttpStatus.BAD_REQUEST_400);

        // THEN
        FailedMessage failedMessage =objectMapper.readValue(responseBodyString, FailedMessage.class);
        assertNotNull(failedMessage.getMessage());

    }


    @Test
    public void test_getTodos() throws Exception {

        // GIVEN

        // WHEN
        String url = BASE_URL+"/todos";
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(url);
        httpClient.executeMethod(method);

        // THEN
        assertEquals(HttpStatus.OK_200, method.getStatusCode());


    }


}
