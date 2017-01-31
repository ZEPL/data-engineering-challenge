package zefl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    /**
     *
     * Get a list of todo objects
     *
     * @return JSON of todos
     */
    @GET
    public Todo[] getTodos() {
        return new Todo[] {};
    }

}
