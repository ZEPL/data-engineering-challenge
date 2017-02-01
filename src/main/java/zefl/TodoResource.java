package zefl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    TodoDAO todoDao = new TodoMemDAOImpl();

    /**
     *
     * Get a list of todo objects
     *
     * @return JSON of todos
     */
    @GET
    public List<Todo> getTodos() {
        return todoDao.findAll();
    }

}
