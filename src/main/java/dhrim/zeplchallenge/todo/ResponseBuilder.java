package dhrim.zeplchallenge.todo;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ResponseBuilder {

    private javax.ws.rs.core.Response.Status status;
    private Object body;

    // set as static so as to always use same instance to reuse parsing result.
    // ObjectMapper is thread safe by itself.
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"));
    }

    public ResponseBuilder(javax.ws.rs.core.Response.Status status) {
        this.status = status;
        this.body = null;
    }

    public ResponseBuilder(javax.ws.rs.core.Response.Status status, Object body) {
        this.status = status;
        this.body = body;
    }


    public javax.ws.rs.core.Response build() throws IOException {
        String json = objectMapper.writeValueAsString(body);
        Response.ResponseBuilder r = Response.status(status).entity(json);
        return r.build();
    }

}
