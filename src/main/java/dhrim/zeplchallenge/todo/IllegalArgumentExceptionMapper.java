package dhrim.zeplchallenge.todo;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
@Slf4j
public class IllegalArgumentExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        log.debug("Invalid request.", e);
        return buildResponse(HttpStatus.BAD_REQUEST_400, e.getMessage());
    }

}
