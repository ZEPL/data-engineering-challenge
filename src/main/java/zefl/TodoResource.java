package zefl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    TodoService todoService = new TodoServiceImpl();

    /**
     *
     * Get a list of todo objects
     *
     * @return JSON of todos
     */
    @GET
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo createTodo(Map<String, String> data) {
        String name = data.get("name");
        return todoService.createTodo(name);
    }

}
