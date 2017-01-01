package dhrim.zeplchallenge.todo;

import com.google.inject.Module;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.eclipse.jetty.http.HttpHeader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class AbstractTestBase {


    protected static final String GET = "GET";
    protected static final String POST = "POST";
    protected static final String PUT = "PUT";
    protected static final String DELETE = "DELETE";

    protected static final int PORT = 2222;
    protected static final String BASE_URL = "http://localhost:+"+PORT;

    private static TodoServer todoServer;

    protected ObjectMapper objectMapper;

    /** return mock binding configured google Guice module. **/
    protected abstract Module getMockBinding();

    protected void before() throws Exception {
        startServerIfNotStarted();
        initObjectMapperIfNotCreated();
    }

    protected void initObjectMapperIfNotCreated() {
        if(objectMapper!=null) { return; }
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    protected void after() {
        shutdownServer();
    }

    protected void startServerIfNotStarted() throws Exception {
        todoServer = TodoServer.getInstanceWithMockBinding(getMockBinding());
        todoServer.start(PORT);
    }

    protected void shutdownServer() {
        if(todoServer ==null || !todoServer.isStarted()) { return; }
        todoServer.shutdown();
        todoServer = null;
    }


    protected String sendAndGetResponseBody(String httpMethod, String path, int expectedStatusCode) throws IOException {
        return sendAndGetResponseBody(httpMethod, path, null, expectedStatusCode);
    }

    protected String sendAndGetResponseBody(String httpMethod, String path, Object requestBodyObject, int expectedStatusCode) throws IOException {

        String url = BASE_URL+path;
        HttpMethod method = null;
        switch (httpMethod) {
            case GET :
                method = new GetMethod(url);
                break;
            case POST :
                method = new PostMethod(url);
                break;
            case PUT :
                method = new PutMethod(url);
                break;
            case DELETE :
                method = new DeleteMethod(url);
                break;
            default :
                throw new RuntimeException("invalid httpMethod. '"+httpMethod+"'");
        }
        switch(httpMethod) {
            case POST:
            case PUT:
                method.setRequestHeader(HttpHeader.CONTENT_TYPE.asString(), "application/json");
                if(requestBodyObject!=null) {
                    String jsonBody = objectMapper.writeValueAsString(requestBodyObject);
                    StringRequestEntity stringRequestEntity = new StringRequestEntity(jsonBody, "application/json", "UTF-8");
                    ((EntityEnclosingMethod)method).setRequestEntity(stringRequestEntity);
                }
                break;
            default:
                break;
        }

        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(method);

        assertEquals(expectedStatusCode, method.getStatusCode());

        String responseBody = method.getResponseBodyAsString();

        System.out.println("responseBody=" + responseBody);
        return responseBody;

    }


    protected void assertEmpty(Todo todo) {
        assertNull("todo is not empty. todo="+todo, todo.getId());
        assertNull("todo is not empty. todo="+todo, todo.getName());
        assertNull("todo is not empty. todo="+todo, todo.getCreated());
    }

    protected void assertEmpty(Task task) {
        assertNull("todo is not empty. todo="+task, task.getId());
        assertNull("todo is not empty. todo="+task, task.getDescription());
        assertNull("todo is not empty. todo="+task, task.getName());
        assertNull("todo is not empty. todo="+task, task.getStatus());
        assertNull("todo is not empty. todo="+task, task.getCreated());
    }


}
