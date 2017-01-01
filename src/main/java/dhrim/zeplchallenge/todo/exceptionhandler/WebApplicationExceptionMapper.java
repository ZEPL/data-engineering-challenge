package dhrim.zeplchallenge.todo.exceptionhandler;


import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
@Slf4j
public class WebApplicationExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException e) {
        log.debug("Invalid request.", e);
        return buildResponse(e.getResponse().getStatus(), e.getMessage());
    }

}
