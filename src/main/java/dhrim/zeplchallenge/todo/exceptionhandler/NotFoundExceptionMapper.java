package dhrim.zeplchallenge.todo.exceptionhandler;


import com.sun.jersey.api.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
@Slf4j
public class NotFoundExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        log.debug("Invalid request.", e);
        return buildResponse(HttpStatus.NOT_FOUND_404, e.getMessage());
    }

}
