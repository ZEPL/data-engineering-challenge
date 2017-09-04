package dhrim.zeplchallenge.todo.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
@Slf4j
public class ThrowableExceptionMapper extends AbstractExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        log.error("Server failed.", e);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR_500, e.getMessage());
    }

}
