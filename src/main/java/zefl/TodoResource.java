package zefl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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


    @GET
    @Path( "{todoId}/tasks/{taskId}" )
    public Task getTask(@PathParam("todoId") String todoId,@PathParam("taskId") String taskId) {
        Todo todo = todoService.getTodo(todoId);
        if (todo == null) {
            throw new WebApplicationException(404);
        }
        Task task = todo.getTaskMap().get(taskId);
        if (task == null) {
            throw new WebApplicationException(404);
        }
        return task;
    }

    @GET
    @Path( "{id}/tasks" )
    public List<Task> getTasks(@PathParam("id") String id) {
        Todo todo = todoService.getTodo(id);
        if (todo == null) {
            throw new WebApplicationException(404);
        }
        return new ArrayList<>(todo.getTaskMap().values());
    }

    @POST
    @Path( "{id}/tasks" )
    public Task createTasks(@PathParam("id") String todoId, Map<String, String> data) {
        Todo todo = todoService.getTodo(todoId);
        if (todo == null) {
            throw new WebApplicationException(404);
        }
        String name = data.get("name");
        String description = data.get("description");
        Task newTask = todoService.createTask(todoId, name, description);
        if (newTask == null) {
            throw new WebApplicationException(404);
        }
        return newTask;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo createTodo(Map<String, String> data) {
        String name = data.get("name");
        return todoService.createTodo(name);
    }

}
