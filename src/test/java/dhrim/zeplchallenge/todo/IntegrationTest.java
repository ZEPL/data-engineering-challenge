package dhrim.zeplchallenge.todo;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test only all integrated are working.
 *
 *      RestApi - Service - Repo
 *
 * Detail functions are tested in other testcase.
 *
 */
public class IntegrationTest extends AbstractTestBase {


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
            protected void configure() { }
        };
    }

    @Test
    public void test_createTodo() throws Exception {

        // GIVEN

        // WHEN
        Todo todo = new TodoBuilder().name("name1").build();
        String responseBodyString = sendAndGetResponseBody(POST, "/todos", todo, HttpStatus.OK_200);

        // THEN
        assertNotNull(responseBodyString);
        Todo actualTodo = objectMapper.readValue(responseBodyString, Todo.class);

        assertNotNull(actualTodo.getId());
        assertEquals(todo.getName(), actualTodo.getName());
        assertNotNull(actualTodo.getCreated());

    }

    @Test
    public void test_getTodoList() throws Exception {

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
