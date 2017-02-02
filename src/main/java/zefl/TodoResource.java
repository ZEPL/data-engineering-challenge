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

    @GET
    @Path( "{id}/tasks/done" )
    public List<Task> getDoneTasks(@PathParam("id") String id) {
        List<Task> tasks = todoService.getDoneTasks(id);
        if (tasks == null) {
            // todo Specify more error cases?
            throw new WebApplicationException(404);
        }
        return tasks;
    }

    @GET
    @Path( "{id}/tasks/not-done" )
    public List<Task> getNotDoneTasks(@PathParam("id") String id) {
        List<Task> tasks = todoService.getNotDoneTasks(id);
        if (tasks == null) {
            // todo Specify more error cases?
            throw new WebApplicationException(404);
        }
        return tasks;
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

    @PUT
    @Path( "{todoId}/tasks/{taskId}" )
    public Task updateTask(@PathParam("todoId") String todoId,
                           @PathParam("taskId") String taskId,
                           Map<String, String> data) {
        String name = data.get("name");
        String description = data.get("description");
        String status = data.get("status");
        Task task = todoService.getTask(todoId, taskId);
        if (task == null) {
            throw new WebApplicationException(404);
        }
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        Task newTask = todoService.updateTask(todoId, task);
        if (newTask == null) {
            // This should not happen...but in concurreny situation...
            throw new WebApplicationException(404);
        }
        return task;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo createTodo(Map<String, String> data) {
        String name = data.get("name");
        return todoService.createTodo(name);
    }

}
