package dhrim.zeplchallenge.todo;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class AbstractExceptionMapper {

    protected Response buildResponse(int statusCode, String message) {
        FailedMessage failedMessage = new FailedMessage();
        failedMessage.setMessage(message);

        return Response
                .status(statusCode)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(failedMessage)
                .build();
    }

}
