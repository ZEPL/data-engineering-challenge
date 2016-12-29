package dhrim.zeplchallenge.todo;

import com.google.inject.Module;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.eclipse.jetty.http.HttpHeader;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public abstract class AbstractTestBase {

    protected static final int PORT = 2222;
    protected static final String BASE_URL = "http://localhost:+"+PORT;

    private static TodoServer todoServer;

    protected ObjectMapper objectMapper = new ObjectMapper();

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



    protected String sendAndGetResponseBody(String url, Object requestBodyObject, int expectedStatusCode) throws IOException {

        HttpClient httpClient = new HttpClient();
        PostMethod method = new PostMethod(url);
        StringRequestEntity stringRequestEntity = new StringRequestEntity(objectMapper.writeValueAsString(requestBodyObject), "application/json", "UTF-8");
        method.setRequestHeader(HttpHeader.CONTENT_TYPE.asString(), "application/json");
        method.setRequestEntity(stringRequestEntity);
        httpClient.executeMethod(method);

        assertEquals(expectedStatusCode, method.getStatusCode());

        return method.getResponseBodyAsString();

    }


}
