package zefl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import zefl.domain.Task;
import zefl.domain.Todo;
import zefl.service.TodoService;

@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMain.class);

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    @Inject
    TodoService todoService;

    /**
     *
     * Get a list of todo objects
     *
     * @return JSON of todos
     */
    @GET
    public List<Todo> getTodos() {
        LOGGER.info("GET /todos");
        return todoService.getTodos();
    }


    @GET
    @Path( "{todoId}/tasks/{taskId}" )
    public Task getTask(@PathParam("todoId") String todoId,@PathParam("taskId") String taskId) {
        LOGGER.info("GET /todos/{}/tasks/{}", todoId, taskId);
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

    @DELETE
    @Path( "{todoId}" )
    public Map deleteTodo(@PathParam("todoId") String todoId) {
        LOGGER.info("DELETE /todos/{}", todoId);
        if (todoService.removeTodo(todoId) ) {
            return new HashMap();
        } else {
            LOGGER.error("DELETE /todos/{} failed", todoId);
            throw new WebApplicationException(404);
        }
    }

    @DELETE
    @Path( "{todoId}/tasks/{taskId}" )
    public Map deleteTask(@PathParam("todoId") String todoId, @PathParam("taskId") String taskId) {
        if (todoService.removeTask(todoId, taskId) ) {
            return new HashMap();
        } else {
            throw new WebApplicationException(404);
        }

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
